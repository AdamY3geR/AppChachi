package com.example.appchachi.Announcements;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appchachi.R;

import java.util.List;

/**
 * Adapter class for displaying announcements in a RecyclerView.
 */
public class AnnouncementAdapter extends RecyclerView.Adapter<AnnouncementAdapter.AnnouncementViewHolder> {

    private final List<Announcement> announcements;

    public AnnouncementAdapter(List<Announcement> announcements) {
        this.announcements = announcements;
    }

    @NonNull
    @Override
    public AnnouncementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_announcement, parent, false);
        return new AnnouncementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnnouncementViewHolder holder, int position) {
        Announcement announcement = announcements.get(position);
        holder.bind(announcement);
    }

    @Override
    public int getItemCount() {
        return announcements.size();
    }

    public static class AnnouncementViewHolder extends RecyclerView.ViewHolder {

        private final TextView fromTextView;
        private final TextView messageTextView;

        public AnnouncementViewHolder(@NonNull View itemView) {
            super(itemView);
            fromTextView = itemView.findViewById(R.id.text_from);
            messageTextView = itemView.findViewById(R.id.text_message);
        }

        public void bind(Announcement announcement) {
            fromTextView.setText(announcement.getFrom());
            messageTextView.setText(announcement.getMessage());
        }
    }
}
