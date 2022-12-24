package com.example.application01.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.application01.utility.ArtistModel;
import com.example.application01.R;
import com.example.application01.utility.RecyclerViewInterface;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Artwork_RecyclerViewAdapter extends RecyclerView.Adapter<Artwork_RecyclerViewAdapter.MyViewHolder> {

    private final RecyclerViewInterface recyclerViewInterface;
    Context context;
    ArrayList<ArtistModel> artworkModels;

    public Artwork_RecyclerViewAdapter(RecyclerViewInterface recyclerViewInterface,
                                       Context context, ArrayList<ArtistModel> artistModels){
        this.recyclerViewInterface = recyclerViewInterface;
        this.context = context;
        this.artworkModels = artistModels;

    }
    @NonNull
    @Override
    public Artwork_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.artwork_view_row,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view,recyclerViewInterface);
        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull Artwork_RecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.textView.setText(artworkModels.get(position).getName());
        Picasso.get()
                .load(artworkModels.get(position).getImg_url())
                .error(R.drawable.ic_artsy_seeklogo_com)
                .into( holder.imageView);

    }

    @Override
    public int getItemCount() {
        return artworkModels.size();
    }

    public static class MyViewHolder extends  RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView;

        public MyViewHolder(@NonNull View itemView,RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            imageView = itemView.findViewById(R.id.artwork_img);
            textView = itemView.findViewById(R.id.artwork_name);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recyclerViewInterface != null){
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });

        }
    }
}
