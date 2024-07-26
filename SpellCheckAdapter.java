package com.example.androidapp_1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class SpellCheckAdapter extends RecyclerView.Adapter<SpellCheckAdapter.ViewHolder> {

    private List<String> suggestions;

    public SpellCheckAdapter(List<String> suggestions) {
        this.suggestions = suggestions;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String suggestion = suggestions.get(position);
        holder.suggestionTextView.setText(suggestion);
    }

    @Override
    public int getItemCount() {
        return suggestions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView suggestionTextView;

        public ViewHolder(View view) {
            super(view);
            suggestionTextView = view.findViewById(android.R.id.text1);
        }
    }
}
