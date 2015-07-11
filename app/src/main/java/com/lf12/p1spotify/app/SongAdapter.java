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
 * Created by lcf on 18/06/2015.
 */
public class SongAdapter extends ArrayAdapter<Song> {

    private ArrayList<Song> songArrayList;

    public SongAdapter(Activity context, int textViewResourceId, ArrayList<Song> songArrayList){
        super(context, textViewResourceId, songArrayList);
        this.songArrayList = songArrayList;

    }

    @Override
    public View getView(int position, View view, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_topsongs, null, true);

        // Song Name
        TextView txtArtists = (TextView) rowView.findViewById(R.id.song_name);
        txtArtists.setText(songArrayList.get(position).name);

        // Album Name
        TextView albumName = (TextView) rowView.findViewById(R.id.album_name);
        albumName.setText(System.getProperty("line.separator") + songArrayList.get(position).album);


        // myArtist Image
        ImageView thumbImg = (ImageView) rowView.findViewById(R.id.album_thumb);
        if ("".equals(songArrayList.get(position).album_thumb)){
            Picasso.with(getContext()).load("https://d13yacurqjgara.cloudfront.net/users/181459/avatars/mini/spotify_icon.png").resize(100, 100).into(thumbImg);
        } else {
            Picasso.with(getContext()).load(songArrayList.get(position).album_thumb).resize(100, 100).into(thumbImg);
        }

        return rowView;
    }


}
