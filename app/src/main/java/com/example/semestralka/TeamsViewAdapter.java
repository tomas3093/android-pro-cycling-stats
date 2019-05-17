package com.example.semestralka;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.semestralka.model.Team;

import java.util.List;


/**
 * Recycler View Adapter + ViewHolder
 * Vytvori recycler zoznam a nastavi data jednotlivych poloziek
 */
public class TeamsViewAdapter extends RecyclerView.Adapter<TeamsViewAdapter.ViewHolder> {

    private List<Team> data;
    private LayoutInflater inflater;
    private ItemClickListener clickListener;
    private Context context;

    // data is passed into the constructor
    TeamsViewAdapter(Context context, List<Team> data) {
        this.inflater = LayoutInflater.from(context);
        this.data = data;
    }

    // inflates the row layout from xml when needed
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = inflater.inflate(R.layout.recyclerview_row_team, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Team team = data.get(position);
        holder.txtViewTeamName.setText(team.getName());
        holder.txtViewTeamCountry.setText(team.getCountry());
        holder.imgViewTeamThumbnail.setImageBitmap(data.get(position).getBitmapFromAsset(context));
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return data.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final TextView txtViewTeamName;
        final TextView txtViewTeamCountry;
        final ImageView imgViewTeamThumbnail;

        ViewHolder(View itemView) {
            super(itemView);
            txtViewTeamName = itemView.findViewById(R.id.tvTeamName);
            txtViewTeamCountry = itemView.findViewById(R.id.tvTeamCountry);
            imgViewTeamThumbnail = itemView.findViewById(R.id.ivTeamThumbnail);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    Team getItem(int id) {
        return data.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}
