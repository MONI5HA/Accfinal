package com.example.androidapp_1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GymLocationAdapter extends RecyclerView.Adapter<GymLocationAdapter.GymLocationViewHolder> {

    private Context context;
    private List<Gym> gymList;

    public GymLocationAdapter(Context context, List<Gym> gymList) {
        this.context = context;
        this.gymList = gymList;
    }

    @NonNull
    @Override
    public GymLocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_gym_location, parent, false);
        return new GymLocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GymLocationViewHolder holder, int position) {
        Gym gym = gymList.get(position);
        holder.gymNameTextView.setText(gym.getName());
        holder.gymLocationTextView.setText(gym.getLocation());
    }

    @Override
    public int getItemCount() {
        return gymList.size();
    }

    public static class GymLocationViewHolder extends RecyclerView.ViewHolder {
        TextView gymNameTextView;
        TextView gymLocationTextView;

        public GymLocationViewHolder(@NonNull View itemView) {
            super(itemView);
            gymNameTextView = itemView.findViewById(R.id.gym_name_text_view);
            gymLocationTextView = itemView.findViewById(R.id.gym_location_text_view);
        }
    }
}
