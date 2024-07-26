package com.example.androidapp_1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CompletionAdapter extends RecyclerView.Adapter<CompletionAdapter.ViewHolder> {

    private final List<String> completions;
    private final OnCompletionClickListener listener;

    public interface OnCompletionClickListener {
        void onCompletionClick(String word);
    }

    public CompletionAdapter(List<String> completions, OnCompletionClickListener listener) {
        this.completions = completions;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String word = completions.get(position);
        holder.textView.setText(word);
        holder.itemView.setOnClickListener(v -> listener.onCompletionClick(word));
    }

    @Override
    public int getItemCount() {
        return completions.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1);
        }
    }
}
