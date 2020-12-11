package com.example.csgofinder;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {

    private ArrayList<String> spinnerArray = new ArrayList<>();
    private Spinner spinner;
    private EditText etProfileName;
    private Button updateButton;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private CollectionReference playerRef;
    private String currentUserId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        spinnerArray.add("Entry Fragger");
        spinnerArray.add("Support");
        spinnerArray.add("IGL");
        spinnerArray.add("AWPer");
        spinnerArray.add("Lurker");

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        playerRef = db.collection("players");
        currentUserId = mAuth.getCurrentUser().getUid();


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        spinner = v.findViewById(R.id.spinnerRoles);
        etProfileName = v.findViewById(R.id.etProfileName);
        updateButton = v.findViewById(R.id.btnSave);
        updateButton.setOnClickListener(updateProfileListener);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getActivity().getApplicationContext(),
                android.R.layout.simple_list_item_1, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        return v;
    }

    private View.OnClickListener updateProfileListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            updateProfile();
        }
    };

    private void updateProfile(){

        String name = etProfileName.getText().toString().trim();
        String role = spinner.getSelectedItem().toString();

        db.collection("players").document(currentUserId)
                .update("name", name, "role", role)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getActivity(), "Profile updated.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Something went wrong.", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
