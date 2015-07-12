package com.lf12.p1spotify.app;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.ArtistsPager;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    SearchView search;

    // ou ArrayAdapter<myArtist>??
    private ArtistAdapter artistsAdapter;

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRetainInstance(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        search = (SearchView) rootView.findViewById(R.id.searchViewArtists);
            search.setQueryHint("Search Artists");

        artistsAdapter = new ArtistAdapter(
                getActivity(),
                R.layout.list_artists,
                new ArrayList<myArtist>()
        );

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() > 0) {
                    (new FetchArtistsTask()).execute(newText);
                }

                return false;
            }
        });

        ListView listView = (ListView) rootView.findViewById(R.id.listView_artists);
        listView.setAdapter(artistsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String artistID = artistsAdapter.getItem(position).id;

                Intent detailIntent = new Intent(getActivity(), TopSongs.class).putExtra(Intent.EXTRA_TEXT, artistID);
                startActivity(detailIntent);
            }
        });



        return rootView;
    }

    // TODO: Implement Search Bar


    @Override
    public void onStart(){
        super.onStart();
    }

    // TODO: Make Toast if Search not found
    public class FetchArtistsTask extends AsyncTask<String, Void, ArrayList<myArtist>> {

        private final String LOG_TAG = FetchArtistsTask.class.getSimpleName();

        @Override
        protected ArrayList<myArtist> doInBackground(String... params) {

            StringBuilder stringBuilder = new StringBuilder();
            for (String s: params){
                stringBuilder.append(s);
            }


                    ArrayList<String> arrayArtists = new ArrayList<String>();
            ArrayList<myArtist> arrayListArtist = new ArrayList<myArtist>();

            try {

                SpotifyApi api = new SpotifyApi();
                SpotifyService spotifyService = api.getService();
                ArtistsPager results = spotifyService.searchArtists(stringBuilder.toString());


            // Print number of items
            // Log.d(LOG_TAG, "Number of items" + (results.artists.items.size()));
            // Name of The first myArtist
            //   Log.d(LOG_TAG, "Number of items" + (results.artists.items.size()));

            String thumbImg = "";
            int thumbCount = 0;

            for (int i = 0; i < results.artists.items.size(); i++) {
                arrayArtists.add((((kaaes.spotify.webapi.android.models.Artist) ((ArrayList) results.artists.items).get(i)).name));
                thumbCount = results.artists.items.get(i).images.size();

                if (thumbCount == 0) {
                    thumbImg = "";
                } else if (thumbCount == 1) {
                    thumbImg = results.artists.items.get(i).images.get(0).url;
                } else {
                    thumbImg = results.artists.items.get(i).images.get(thumbCount - 2).url;
                }


                arrayListArtist.add(new myArtist(
                        results.artists.items.get(i).name,
                        results.artists.items.get(i).id,
                        thumbImg));
                Log.d(LOG_TAG, "Artists, item " + i + " " + (results.artists.items.get(i)).name);
                Log.d(LOG_TAG, "Followers, item " + i + " " + (results.artists.items.get(i)).followers.total);
                Log.d(LOG_TAG, "SpotifyID, item " + i + " " + (results.artists.items.get(i)).id);
            }

            } catch (Exception e) {
                Log.e(LOG_TAG, "Error fetching Artists ", e);
            }

            return arrayListArtist;
        }

        @Override
        protected void onPostExecute(ArrayList<myArtist> result) {
            if (result == null || result.isEmpty()){
                artistsAdapter.clear();
                Toast toaster = Toast.makeText(getActivity().getBaseContext(), "No Artists found. Please refine search.", Toast.LENGTH_SHORT);
                toaster.show();
            } else {
                artistsAdapter.clear();
                for (myArtist song : result) {
                    artistsAdapter.add(song);
                }
            }
        }

    }
}


