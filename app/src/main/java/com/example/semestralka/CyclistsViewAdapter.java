package com.example.semestralka;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.semestralka.model.Cyclist;

import java.util.List;


/**
 * Recycler View Adapter + ViewHolder
 * Vytvori recycler zoznam a nastavi data jednotlivych poloziek
 */
public class CyclistsViewAdapter extends RecyclerView.Adapter<CyclistsViewAdapter.ViewHolder> {

    private List<Cyclist> data;
    private LayoutInflater inflater;
    private CyclistsViewAdapter.ItemClickListener clickListener;
    private Context context;

    // data is passed into the constructor
    CyclistsViewAdapter(Context context, List<Cyclist> data) {
        this.inflater = LayoutInflater.from(context);
        this.data = data;
    }

    // inflates the row layout from xml when needed
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = inflater.inflate(R.layout.recyclerview_row_cyclist, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(@NonNull CyclistsViewAdapter.ViewHolder holder, int position) {
        Cyclist cyclist = data.get(position);
        holder.tvCyclistFullName.setText(cyclist.getFullName());
        holder.tvCyclistNationality.setText(cyclist.getNationality());
        holder.tvCyclistTeam.setText(cyclist.getTeam().getName());
        holder.ivCyclistThumbnail.setImageBitmap(data.get(position).getBitmapFromAsset(context));
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return data.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final TextView tvCyclistFullName;
        final TextView tvCyclistNationality;
        final TextView tvCyclistTeam;
        final ImageView ivCyclistThumbnail;

        ViewHolder(View itemView) {
            super(itemView);
            tvCyclistFullName = itemView.findViewById(R.id.tvCyclistFullName);
            tvCyclistNationality = itemView.findViewById(R.id.tvCyclistNationality);
            tvCyclistTeam = itemView.findViewById(R.id.tvCyclistTeam);
            ivCyclistThumbnail = itemView.findViewById(R.id.ivCyclistThumbnail);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    Cyclist getItem(int id) {
        return data.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(CyclistsViewAdapter.ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
