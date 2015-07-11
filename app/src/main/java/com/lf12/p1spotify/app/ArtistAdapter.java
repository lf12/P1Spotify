package com.lf12.p1spotify.app;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by lcf on 15/06/2015.
 */
public class ArtistAdapter extends ArrayAdapter<myArtist> {

    private ArrayList<myArtist> artistArrayList;

    public ArtistAdapter(Activity context, int textViewResourceId, ArrayList<myArtist> artistArrayList){
        super(context, textViewResourceId, artistArrayList);
        this.artistArrayList = artistArrayList;

    }

    @Override
    public View getView(int position, View view, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_artists, null, true);

        // myArtist Name
        TextView txtArtists = (TextView) rowView.findViewById(R.id.artist_name);
        txtArtists.setText(artistArrayList.get(position).name);


        // myArtist Image
        ImageView thumbImg = (ImageView) rowView.findViewById(R.id.artist_thumb);
        if ("".equals(artistArrayList.get(position).thumb)){
            Picasso.with(getContext()).load("https://d13yacurqjgara.cloudfront.net/users/181459/avatars/mini/spotify_icon.png").resize(100, 100).into(thumbImg);
        } else {
            Picasso.with(getContext()).load(artistArrayList.get(position).thumb).resize(100, 100).into(thumbImg);
        }

        return rowView;
    }


}
