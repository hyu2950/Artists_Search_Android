package com.example.application01;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.application01.utility.ArtistModel;
import com.example.application01.utility.RecyclerViewInterface;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Artist_RecyclerViewAdapter  extends RecyclerView.Adapter<Artist_RecyclerViewAdapter.MyViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;

    Context context;
    ArrayList<ArtistModel> artistModels;

    public Artist_RecyclerViewAdapter(Context context, ArrayList<ArtistModel> artistModels,
                                      RecyclerViewInterface recyclerViewInterface){
        this.context = context;
        this.artistModels = artistModels;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public Artist_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycle_view_row,parent,false);
        return new Artist_RecyclerViewAdapter.MyViewHolder(view,recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull Artist_RecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.textView.setText(artistModels.get(position).getName());
        Picasso.get()
                .load(artistModels.get(position).getImg_url())
                .error(R.drawable.ic_artsy_seeklogo_com)
                .into( holder.imageView);
      //  holder.imageView.setImageResource(R.drawable.ic_artsy_seeklogo_com);
    }

    @Override
    public int getItemCount() {
        return artistModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView;

        public MyViewHolder(@NonNull View itemView,RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            imageView = itemView.findViewById(R.id.artist_img);
            textView = itemView.findViewById(R.id.artist_name);

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
