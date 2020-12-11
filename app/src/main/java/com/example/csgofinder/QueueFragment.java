package com.example.csgofinder;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class QueueFragment extends Fragment {

    private static final String TAG = "MACO";
    private static final int MAX_PLAYER = 5;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private CollectionReference playerRef;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private Button buttonPlay, buttonLogout, buttonEmptyQueue;

    private ArrayList<Player> listOfPlayers = new ArrayList<Player>();
    private Player player;
    private String currentUserId;

    private QueueViewModel model;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        playerRef = db.collection("players");
        currentUserId = mAuth.getCurrentUser().getUid();
        model = new ViewModelProvider(requireActivity()).get(QueueViewModel.class);

        Log.d(TAG, "onCreate: " + model.isPlay());

    }

    @Override
    public void onStart() {
        super.onStart();

        Log.d(TAG, "onStart: " + model.isPlay());

        db.collection("players")
                .whereEqualTo("play", true)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        listOfPlayers.clear();

                        for (DocumentSnapshot doc : value.getDocuments()){
                            Player player = doc.toObject(Player.class);
                            listOfPlayers.add(player);
                        }

                        if (listOfPlayers.size() >= MAX_PLAYER && model.isPlay()){
                            buttonPlay.setText("Leave queue");
                            buttonPlay.setEnabled(true);
                        } else if (listOfPlayers.size() >= MAX_PLAYER){
                            buttonPlay.setText("Queue full");
                            buttonPlay.setEnabled(false);
                        } else {
                            buttonPlay.setText("Look for players");
                            buttonPlay.setEnabled(true);
                        }

                        mAdapter.notifyDataSetChanged();
                    }
                });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_queue, container, false);
        buttonPlay = v.findViewById(R.id.button_play);
        buttonPlay.setOnClickListener(playClickListener);
        recyclerView = v.findViewById(R.id.rv_players);
        initRecyclerView();
        return v;
    }

    private void initRecyclerView() {
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new PlayerAdapter(listOfPlayers);
        recyclerView.setAdapter(mAdapter);
    }

    View.OnClickListener playClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            model.playToggle();
            Log.d(TAG, "onPlay: " + model.isPlay());


            db.collection("players")
                    .document(currentUserId)
                    .update("play", model.isPlay())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            //Toast.makeText(MainActivity.this, "looking to play", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    };
}
