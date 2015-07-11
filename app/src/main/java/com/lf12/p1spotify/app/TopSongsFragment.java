package com.lf12.p1spotify.app;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Tracks;


/**
 * A placeholder fragment containing a simple view.
 */
public class TopSongsFragment extends Fragment {

    private ArrayAdapter<Song> songAdapter;
    private String queryString;

    public TopSongsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_top_songs, container, false);

        // TopSongsFragment was called via Intent, Inspect the Intent for Artist ID
        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)){
            queryString = intent.getStringExtra(Intent.EXTRA_TEXT);
        }


        songAdapter = new SongAdapter (
                getActivity(),
                R.layout.list_topsongs,
                new ArrayList<Song>()
        );

        ListView listView = (ListView) rootView.findViewById(R.id.listView_songs);
        listView.setAdapter(songAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*String intentText = "intenttext";*/
                Context context = getActivity().getBaseContext();
                Toast foreToast = Toast.makeText(context, queryString, Toast.LENGTH_SHORT);
                foreToast.show();

                Intent previewIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(songAdapter.getItem(position).preview_url));
                startActivity(previewIntent);


            }
        });



        return rootView;
    }

    protected void updateSongs(){
        FetchSongsTask fetchSongsTask= new FetchSongsTask();
        fetchSongsTask.execute();
    }

    @Override
    public void onStart(){
        super.onStart();
        updateSongs();
    }

    //TODO: Adaptar para receber Musicas
    public class FetchSongsTask extends AsyncTask<String, Void, ArrayList<Song>> {

        private final String LOG_TAG = FetchSongsTask.class.getSimpleName();

        @Override
        protected ArrayList<Song> doInBackground(String... params) {

            ArrayList<Song> arrayListSongs = new ArrayList<Song>();

            SpotifyApi api = new SpotifyApi();
            SpotifyService spotifyService = api.getService();
            HashMap<String, Object> queryTopTracks = new HashMap<>();
            queryTopTracks.put(SpotifyService.COUNTRY, Locale.getDefault().getCountry());

            Tracks results = spotifyService.getArtistTopTrack(queryString, queryTopTracks);


            // Print number of items
            // Log.d(LOG_TAG, "Number of items" + (results.artists.items.size()));
            // Name of The first myArtist
            //   Log.d(LOG_TAG, "Number of items" + (results.artists.items.size()));

            String thumbImg = "";
            int thumbCount = 0;

            for (int i = 0; i < results.tracks.size(); i++) {
                thumbCount = results.tracks.get(i).album.images.size();

                if (thumbCount == 0){
                    thumbImg = "";
                } else if (thumbCount == 1) {
                    thumbImg = results.tracks.get(i).album.images.get(0).url;
                } else {
                    thumbImg = results.tracks.get(i).album.images.get(thumbCount-2).url;
                }


                arrayListSongs.add(new Song(
                        results.tracks.get(i).name,
                        results.tracks.get(i).id,
                        results.tracks.get(i).album.name,
                        thumbImg,
                        results.tracks.get(i).preview_url
                ));
                Log.d(LOG_TAG, "Artists, item " + i + " " + (results.tracks.get(i)).name);
                Log.d(LOG_TAG, "Album Name " + i + " " + (results.tracks.get(i).album.name));
                Log.d(LOG_TAG, "SpotifyID, item " + i + " " + (results.tracks.get(i)).id);
                }

            return arrayListSongs;
        }

        @Override
        protected void onPostExecute(ArrayList<Song> result) {
            if (result != null){
                songAdapter.clear();
                for (Song song : result) {
                    songAdapter.add(song);
                }
            }
        }

    }


}