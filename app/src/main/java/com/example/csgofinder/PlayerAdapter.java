package com.example.csgofinder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.ViewHolder> {

    private ArrayList<Player> listOfPlayers;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView playerName;
        public TextView role;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            playerName = itemView.findViewById(R.id.textView_name);
            role = itemView.findViewById(R.id.textView_role);
        }
    }

    public PlayerAdapter(ArrayList<Player> listOfPlayers){
        this.listOfPlayers = listOfPlayers;
    }

    @NonNull
    @Override
    public PlayerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_row, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerAdapter.ViewHolder holder, int position) {
        holder.playerName.setText(listOfPlayers.get(position).getName());
        holder.role.setText(listOfPlayers.get(position).getRole());
    }

    @Override
    public int getItemCount() {
        return listOfPlayers.size();
    }
}
