package com.example.application01.detail;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.application01.fragment.ArtistInfo;
import com.example.application01.fragment.Artwork;

import org.json.JSONObject;

public class VMAdapter extends FragmentStateAdapter {




    Bundle artistInfoResponse;
    Bundle artworkResponse;

    //2 variables to hold artistinfo and artworks
    private String[] titles =new String[]{"DETAILS","ARTWORK"};
    Context context;
    public VMAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);

    }


//    void setArtistInfoData(data)
//    {
//        assign data to your variables
//    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment artistInfo = new ArtistInfo();
        artistInfo.setArguments(artistInfoResponse);

        switch (position)
        {
            case 0:

                return artistInfo;
            case 1:
                Fragment artwork = new Artwork();
                artwork.setArguments(artworkResponse);
                return artwork;
        }

        return artistInfo;
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }

    public void setArtistInfoResponse(Bundle artistInfoResponse) {
        this.artistInfoResponse = artistInfoResponse;
    }

    public void setArtworkResponse(Bundle artworkResponse) {
        this.artworkResponse = artworkResponse;
    }
}
