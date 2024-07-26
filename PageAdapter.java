package com.example.androidapp_1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PageAdapter extends RecyclerView.Adapter<PageAdapter.PageViewHolder> {

    private List<Page> pages;

    public PageAdapter(List<Page> pages) {
        this.pages = pages;
    }

    @NonNull
    @Override
    public PageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.page_ranking_item, parent, false);
        return new PageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PageViewHolder holder, int position) {
        Page page = pages.get(position);
        holder.urlTextView.setText(page.url);
        holder.rankTextView.setText("Number of occurrence: " + page.rank);
    }

    @Override
    public int getItemCount() {
        return pages.size();
    }

    public void updatePages(List<Page> pages) {
        this.pages = pages;
        notifyDataSetChanged();
    }

    static class PageViewHolder extends RecyclerView.ViewHolder {
        TextView urlTextView;
        TextView rankTextView;

        PageViewHolder(@NonNull View itemView) {
            super(itemView);
            urlTextView = itemView.findViewById(R.id.url_text_view);
            rankTextView = itemView.findViewById(R.id.rank_text_view);
        }
    }
}
