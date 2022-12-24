package com.example.application01;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.splashscreen.SplashScreen;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.application01.detail.ArtistDetail;
import com.example.application01.favorite.FavoriteModel;
import com.example.application01.favorite.Favorite_RecycleViewAdapter;
import com.example.application01.utility.RecyclerViewInterface;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements RecyclerViewInterface {

    ArrayList<FavoriteModel> favoriteModels;
    TextView hyperlink;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);




        Intent intent = new Intent(MainActivity.this, MainActivity2.class);
        startActivity(intent);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


/*
        AppCompatActivity mainActivity = (AppCompatActivity)this;
        mainActivity.setContentView(R.layout.activity_main);
        ProgressBar spinner = (ProgressBar) mainActivity.findViewById(R.id.progressBar3);

 */



        // set Time format
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("d MMMM yyyy");
        String currentDate = format.format(calendar.getTime());
        TextView textViewDate = findViewById(R.id.text_view_date);
        textViewDate.setText(currentDate);

        hyperlink = findViewById(R.id.hyperlink);
        hyperlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openURL = new Intent(Intent.ACTION_VIEW);
                openURL.setData(Uri.parse("https://www.artsy.net"));
                startActivity(openURL);

            }
        });


/*
        SharedPreferences sharedPref = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        Map<String, ?> map = sharedPref.getAll();

       if (map != null) {
           //recycle view test
           setFavoriteModels(map);
           RecyclerView recyclerView = findViewById(R.id.favoriate_recycle_layout);
           Favorite_RecycleViewAdapter adapter = new Favorite_RecycleViewAdapter(this, this, favoriteModels);
           recyclerView.setAdapter(adapter);
           LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
           recyclerView.setLayoutManager(linearLayoutManager);
           recyclerView.addItemDecoration(
                   new DividerItemDecoration(
                           recyclerView.getContext(),
                           linearLayoutManager.getOrientation()
                   )
           );
       }
       */


        Log.d("MainActivity","onCreate run");


    }

    public void onResume() {

        super.onResume();

        Log.d("MainActivity","onResume Called");
        SharedPreferences sharedPref = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        Map<String, ?> map = sharedPref.getAll();

        if (map != null) {
            //recycle view test
            setFavoriteModels(map);
            RecyclerView recyclerView = findViewById(R.id.favoriate_recycle_layout);
            Favorite_RecycleViewAdapter adapter = new Favorite_RecycleViewAdapter(this, this, favoriteModels);
            recyclerView.setAdapter(adapter);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(linearLayoutManager);
          /*  DividerItemDecoration dividerItemDecoration =
            new DividerItemDecoration(
                    recyclerView.getContext(),
                    //linearLayoutManager.getOrientation()
                    DividerItemDecoration.VERTICAL
            );
            dividerItemDecoration.setDrawable(getDrawable(R.drawable.recycle_view_border));
            recyclerView.addItemDecoration(
                  dividerItemDecoration
            );

           */



        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search…");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.setQuery(query,false);
                Intent intent = new Intent(MainActivity.this,SearchResult.class);
                intent.putExtra("name",query);
                startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void setFavoriteModels(Map<String, ?> map){
        favoriteModels = new ArrayList<>();
        Gson gson =new Gson();
        for (Map.Entry<String, ?> entry : map.entrySet()) {
           String v = (String)entry.getValue();
           Type type = new TypeToken<ArrayList<String>>() {}.getType();
           ArrayList<String> arr = gson.fromJson(v,type);
           FavoriteModel favoriteModel = new FavoriteModel(arr.get(0), arr.get(1),arr.get(2),arr.get(3));
            favoriteModels.add(favoriteModel);
        }


    }




    @Override
    public void onItemClick(int postion) {

        Intent intent = new Intent(MainActivity.this, ArtistDetail.class);

        intent.putExtra("id",favoriteModels.get(postion).getId());
        intent.putExtra("name",favoriteModels.get(postion).getName());

        startActivity(intent);





    }
}