package com.example.application01.favorite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.application01.R;
import com.example.application01.utility.RecyclerViewInterface;

import java.util.ArrayList;

public class Favorite_RecycleViewAdapter extends RecyclerView.Adapter<Favorite_RecycleViewAdapter.MyViewHolder> {

    private final RecyclerViewInterface recyclerViewInterface;

    Context context;
    ArrayList<FavoriteModel> favoriteModels;

    public Favorite_RecycleViewAdapter(RecyclerViewInterface recyclerViewInterface,
                                       Context context, ArrayList<FavoriteModel> favoriteModels) {
        this.recyclerViewInterface = recyclerViewInterface;
        this.context = context;
        this.favoriteModels = favoriteModels;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.favorites_view_row,parent,false);
        return new Favorite_RecycleViewAdapter.MyViewHolder(view,recyclerViewInterface);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.nameView.setText(favoriteModels.get(position).getName());
        holder.birthdayView.setText(favoriteModels.get(position).getBirthday());
        holder.nationalityView.setText(favoriteModels.get(position).getNationality());

    }

    @Override
    public int getItemCount() {
        return favoriteModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nameView;
        TextView birthdayView;
        TextView nationalityView;

        public MyViewHolder(@NonNull View itemView,RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            nameView = itemView.findViewById(R.id.favorite_name_value);
            birthdayView = itemView.findViewById(R.id.favorite_birthday_value);
            nationalityView = itemView.findViewById(R.id.favorite_nationality_value);

            ImageView imageView = itemView.findViewById(R.id.chevron_right_icon);
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
