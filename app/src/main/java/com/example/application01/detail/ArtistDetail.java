package com.example.application01.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.application01.Artist_RecyclerViewAdapter;
import com.example.application01.MainActivity;
import com.example.application01.R;
import com.example.application01.SearchResult;
import com.example.application01.fragment.ArtistInfo;
import com.example.application01.fragment.Artwork;
import com.example.application01.utility.ArtistModel;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ArtistDetail extends AppCompatActivity {

    VMAdapter vmAdapter;
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    String name;
    String id;
    Boolean isAdded = false;
    private String[] titles =new String[]{"DETAILS","ARTWORK"};
    private int[] icon = new int[]{R.drawable.ic_action_info_new,R.drawable.ic_action_name};

    private SharedPreferences sharedPref;
    private LinearLayout spinner;


    Bundle artInfo;
    Bundle artwork;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedPref = getSharedPreferences("MySharedPref",MODE_PRIVATE);


        name = getIntent().getStringExtra("name");
        id = getIntent().getStringExtra("id");
        getSupportActionBar().setTitle(name);

        spinner = findViewById(R.id.spinner02);
        spinner.setVisibility(View.VISIBLE);

        //Show tabLayout
        viewPager2 = findViewById(R.id.viewpage);
        tabLayout = findViewById(R.id.tab_layout);

        tabLayout.setTabIconTint(getResources().getColorStateList(R.color.icon_color));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Drawable drawable = tab.getIcon();
                int color01 = getResources().getColor(R.color.orange);
                drawable.setColorFilter(color01, PorterDuff.Mode.SRC_IN);
                tab.setIcon(drawable);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Drawable drawable = tab.getIcon();
                int color01 = getResources().getColor(R.color.orange);
                drawable.setColorFilter(color01, PorterDuff.Mode.SRC_IN);
                tab.setIcon(drawable);

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Drawable drawable = tab.getIcon();
                int color01 = getResources().getColor(R.color.orange);
                drawable.setColorFilter(color01, PorterDuff.Mode.SRC_IN);
                tab.setIcon(drawable);

            }
        });



        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout,viewPager2,((tab, position) -> {
            tab.setText(titles[position]);
            Drawable drawable = getDrawable(icon[position]);
            int color = getResources().getColor(R.color.orange);
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);
            tab.setIcon(drawable);
        }));

        String info_url =  "https://hw40405.wm.r.appspot.com/api_artists/"+id;
        String artwork_url = "https://hw40405.wm.r.appspot.com/api_artworks/"+id;
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add( new JsonObjectRequest(Request.Method.GET, info_url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //2 api calls to get the data


                try {
                    setUpArtistInfo(response);


                } catch (Exception e) {
                    Log.d("Search", "error in info JSON:" + e);
                }

                //vmAdapter.setArtworks(artworksdata)
                RequestQueue queue = Volley.newRequestQueue(ArtistDetail.this);
                queue.add( new JsonObjectRequest(Request.Method.GET, artwork_url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Search", " artwork response:" + response);
                        try {
                            spinner.setVisibility(View.GONE);
                            setUpArtwork(response);
                            vmAdapter = new VMAdapter(ArtistDetail.this);
                            vmAdapter.setArtistInfoResponse(artInfo);
                            vmAdapter.setArtworkResponse(artwork);
                            viewPager2.setAdapter(vmAdapter);
                            tabLayoutMediator.attach();
                        } catch (Exception e) {
                            Log.d("Search", "error in artwork JSON:" + e);
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("SearchResult", "Fail to get Artwork! "+error);
                    }
                }));



               //
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("SearchResult", "Fail to get Info! "+error);
            }
        }));



                //.attach();

    }

    private void setUpArtwork(JSONObject response) throws JSONException {
        artwork = new Bundle();

        JSONArray artworks = response.getJSONObject("_embedded").getJSONArray("artworks");
        artwork.putInt("num",artworks.length());
        Log.d("detail","bundle:"+artwork.getInt("num"));
        for (int i = 0;i<artworks.length();i++){
            JSONObject object = artworks.getJSONObject(i);
            String name = object.getString("title");
            String img_url = object.getJSONObject("_links").getJSONObject("thumbnail").getString("href");
            String id = object.getString("id");
            artwork.putStringArray("artwork"+i,new String[]{name,img_url,id});
        }

    }

    private void setUpArtistInfo(JSONObject response) throws JSONException {
        artInfo = new Bundle();

        artInfo.putString("name",response.getString("name"));
        artInfo.putString("birthday",response.getString("birthday"));
        artInfo.putString("deathday",response.getString("deathday"));
        artInfo.putString("nationality",response.getString("nationality"));
        artInfo.putString("biography",response.getString("biography"));

    }

    // initialize start icon
    public boolean onPrepareOptionsMenu (Menu menu){
        MenuItem menuItem = menu.findItem(R.id.favorite_icon);
        String res =sharedPref.getString(name,"");
        Log.d("Detial","start init:"+res);
        if ("".equals(res)){
            isAdded = false;
            menuItem.setIcon(R.drawable.ic_star_outline);
        } else {
            isAdded = true;
            menuItem.setIcon(R.drawable.ic_star);

        }
        return true;
    }

    // change start icon
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu,menu);
        MenuItem menuItem = menu.findItem(R.id.favorite_icon);

        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (isAdded){
                    menuItem.setIcon(R.drawable.ic_star_outline);
                    Context context = getApplicationContext();
                    CharSequence text = name + " is Removed from favorites";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.remove(name);
                    editor.apply();

                } else {
                    if (artInfo != null) {
                        menuItem.setIcon(R.drawable.ic_star);
                        Context context = getApplicationContext();
                        CharSequence text = name + " is added to favorites";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();

                        SharedPreferences.Editor editor = sharedPref.edit();
                        ArrayList<String> arr = new ArrayList<>();

                        arr.add(name);
                        arr.add(artInfo.getString("birthday"));
                        arr.add(artInfo.getString("nationality"));
                        arr.add(id);

                        Gson gson =new Gson();
                        String json = gson.toJson(arr);

                        editor.putString(name,json);


                        editor.apply();
                    }

                }
                isAdded = !isAdded;
                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }

    // back to the former activity
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