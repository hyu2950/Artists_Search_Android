package com.example.application01.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.application01.R;

public class ArtistInfo extends Fragment {
    View view;
    //data
//    public ArtistInfo(String data)
//    {
//        this.data = data;
//    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_artist_info, container, false);
        //update the view with the data. view.findViewById().setText()
        Log.d("ArtistInfo","onCreate Function run");


        TextView text;
        String str = getArguments().getString("name");
        Log.d("artistInfo","name:"+str);

        if ("".equals(str)){
            view.findViewById(R.id.name_row).setVisibility(View.GONE);
        } else {
            text = view.findViewById(R.id.name_value);
            text.setText(str);
        }

        str = getArguments().getString("nationality");
        Log.d("artistInfo","nationality:"+str);
        if ("".equals(str)){
            view.findViewById(R.id.nationality_row).setVisibility(View.GONE);
        } else {
            text = view.findViewById(R.id.nationality_value);
            text.setText(str);
        }

        str = getArguments().getString("biography");
        if ("".equals(str)){
            view.findViewById(R.id.biography_row).setVisibility(View.GONE);
        } else {
            text = view.findViewById(R.id.biography_value);
            text.setText(str);
        }

        str = getArguments().getString("birthday");
        if ("".equals(str)){
            view.findViewById(R.id.birthday_row).setVisibility(View.GONE);
        } else {
            text = view.findViewById(R.id.birthday_value);
            text.setText(str);
        }

        str = getArguments().getString("deathday");
        if ("".equals(str)){
            view.findViewById(R.id.deathday_row).setVisibility(View.GONE);
        } else {
            text = view.findViewById(R.id.deathday_value);
            text.setText(str);
        }



        return view;
    }
}