package com.example.appchachi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

/**
 * Adapter class for displaying announcements in a RecyclerView.
 */
public class AnnouncementAdapter extends RecyclerView.Adapter<AnnouncementAdapter.AnnouncementViewHolder> {

    private List<String> announcements;

    /**
     * Constructor for the AnnouncementAdapter.
     *
     * @param announcements The list of announcements to be displayed.
     */
    public AnnouncementAdapter(List<String> announcements) {
        this.announcements = announcements;
    }

    /**
     * Called when RecyclerView needs a new ViewHolder of the given type to represent an item.
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return A new AnnouncementViewHolder that holds a View of the given view type.
     */
    @NonNull
    @Override
    public AnnouncementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_announcement, parent, false);
        return new AnnouncementViewHolder(view);
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the item at the given position.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull AnnouncementViewHolder holder, int position) {
        String announcement = announcements.get(position);
        holder.bind(announcement);
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return announcements.size();
    }

    /**
     * ViewHolder for holding the view for each announcement item.
     */
    public static class AnnouncementViewHolder extends RecyclerView.ViewHolder {

        private TextView announcementText;

        /**
         * Constructor for the AnnouncementViewHolder.
         *
         * @param itemView The view for each announcement item.
         */
        public AnnouncementViewHolder(@NonNull View itemView) {
            super(itemView);
            announcementText = itemView.findViewById(R.id.text_announcement);
        }

        /**
         * Binds the announcement text to the TextView.
         *
         * @param announcement The announcement text to be displayed.
         */
        public void bind(String announcement) {
            announcementText.setText(announcement);
        }
    }
}
