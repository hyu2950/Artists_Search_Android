package com.example.application01.fragment;

import static com.example.application01.R.drawable.background;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.application01.utility.ArtistModel;
import com.example.application01.R;
import com.example.application01.utility.RecyclerViewInterface;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Artwork extends Fragment implements RecyclerViewInterface{

    Dialog dialog;

    View view;
    ArrayList<ArtistModel> artworkModels = new ArrayList<>();
    int num;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_artwork, container, false);

        Log.d("Artwork","onCreate Function run");

        TextView textView = view.findViewById(R.id.no_artwork);
        textView.setVisibility(View.GONE);

        num = getArguments().getInt("num");
        if (num > 0) {
            RecyclerView recyclerView = view.findViewById(R.id.artwork_view);
            setUpArtworkModels();

            Artwork_RecyclerViewAdapter adapter = new Artwork_RecyclerViewAdapter(this, getContext(), artworkModels);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        } else {
            textView.setVisibility(View.VISIBLE);
        }


        return view;
    }

    private void setUpArtworkModels() {

        Log.d("artwork fragment","num:"+num);
        for (int i = 0;i<num;i++){
            String[] strings = getArguments().getStringArray("artwork"+i);
            artworkModels.add(new ArtistModel(strings[0],strings[1],strings[2]));

        }

    }


    @Override
    public void onItemClick(int postion) {
      //  Toast.makeText(getContext(),"Item Selected",Toast.LENGTH_LONG).show();

        String url = "https://hw40405.wm.r.appspot.com/api_genes/"+artworkModels.get(postion).getId();
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add( new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray arr = response.getJSONObject("_embedded").getJSONArray("genes");
                    dialog = new Dialog(getContext());
                    if (arr.length()>0){
                        JSONObject info = arr.getJSONObject(0);
                        String name = info.getString("name");
                        String description = info.getString("description");
                        String img_url = info.getJSONObject("_links").getJSONObject("thumbnail").getString("href");


                        dialog.setContentView(R.layout.dialog_scorllale);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        TextView dialog_name = dialog.findViewById(R.id.category_name_tag);
                        dialog_name.setText(name);
                        TextView dialog_description = dialog.findViewById(R.id.description_value);
                        dialog_description.setText(description);
                        ImageView imageView = dialog.findViewById(R.id.category_img);
                        Picasso.get()
                                .load(img_url)
                                .error(R.drawable.ic_artsy_seeklogo_com)
                                .into(imageView);



                    } else {
                        dialog.setContentView(R.layout.no_result);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    }
                    dialog.show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }));





    }
}