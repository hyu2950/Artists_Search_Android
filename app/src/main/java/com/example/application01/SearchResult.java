package com.example.application01;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.application01.detail.ArtistDetail;
import com.example.application01.utility.ArtistModel;
import com.example.application01.utility.RecyclerViewInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchResult extends AppCompatActivity implements RecyclerViewInterface {


    ArrayList<ArtistModel> artistModels;
    String name;
    private LinearLayout spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name = getIntent().getStringExtra("name");
        getSupportActionBar().setTitle(name);

        String url = "https://hw40405.wm.r.appspot.com/search/"+name;
        Log.d("SearchResult","onCreate Function run");
        spinner = findViewById(R.id.spinner04);
        spinner.setVisibility(View.VISIBLE);

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add( new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                spinner.setVisibility(View.GONE);
                Log.d("SearchResult", "onCreate:" + response);
                artistModels = new ArrayList<>();
                try {
                    setUpArtistModels(response);
                } catch (Exception e) {
                    Log.d("Search", "error in JSON:" + e);
                }
                int size = artistModels.size();
                Log.d("Search", ""+size);
                if (size > 0 ) {

                    RecyclerView recyclerView = findViewById(R.id.mRecycleView);
                    Artist_RecyclerViewAdapter adapter = new Artist_RecyclerViewAdapter(SearchResult.this,
                            artistModels, SearchResult.this);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(SearchResult.this));
                } else {
                    findViewById(R.id.no_result_found).setVisibility(View.VISIBLE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("SearchResult", "Fail to get Info! "+error);
            }
        }));








    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportActionBar().setTitle(name);
        return true;
    }

    private void setUpArtistModels(JSONArray response) throws JSONException {
        Log.d("SearchResult",""+response.length());

        int len = response.length();
        for (int i = 0;i<len;i++){
            JSONObject object = response.getJSONObject(i);
            String type = object.getString("type");

            if ( "artist".equals(type)) {
                String name = object.getString("title");
                String img_url = object.getJSONObject("_links").getJSONObject("thumbnail").getString("href");
                String id_url = object.getJSONObject("_links").getJSONObject("self").getString("href");
                String id = id_url.substring(id_url.lastIndexOf("/") + 1);
                ArtistModel artist = new ArtistModel(name, img_url, id);
                artistModels.add(artist);
            }

        }

    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(SearchResult.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onItemClick(int postion) {
        Intent intent = new Intent(SearchResult.this, ArtistDetail.class);

        intent.putExtra("id",artistModels.get(postion).getId());
        intent.putExtra("name",artistModels.get(postion).getName());

        startActivity(intent);
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            Intent returnIntent = new Intent();
            setResult(RESULT_CANCELED, returnIntent);
            finish();
            return true;
        }
        return true;
    }
}