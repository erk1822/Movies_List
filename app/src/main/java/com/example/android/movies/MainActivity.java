package com.example.android.movies;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private static final String[] movies = {"JAWS", "Airplane!", "Raiders of the Lost Ark", "Ghostbusters", "Groundhog Day", "Dumb and Dumber"};
    private static final String[] urls = {"tt0073195", "tt0080339", "tt0082971", "tt0087332", "tt0107048", "tt0109686"};
    private ListView moviesList;
    private ArrayList<String> movieTitles;
    private ArrayList<String> urlList;
    private AlertDialog dialog;
    private int itemdelete;
    private SharedPreferences p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        moviesList=findViewById(R.id.movieList);

        movieTitles = new ArrayList<String>();
        urlList = new ArrayList<String>();
        p = getPreferences(Context.MODE_PRIVATE);

        moviesList.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.imdb.com/title/" + urlList.get(i)));
                        startActivity(in);
                    }
                }
        );

        for (int i=0; i<p.getInt("NEWSIZE",-1); i++) {
            movieTitles.add(p.getString("TITLE"+i,"error"));
            urlList.add(p.getString("CODE"+i, "error"));
        }

        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this, R.layout.list_item_view, movieTitles);
        moviesList.setAdapter(adapter);


        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("REMOVIN' A MOVIE!");
        alert.setMessage("Are you sure you want to remove this movie?");
        alert.setPositiveButton("YES!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                movieTitles.remove(itemdelete);
                urlList.remove(itemdelete);

                ArrayAdapter<String> adapter;
                adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_item_view, movieTitles);
                moviesList.setAdapter(adapter);
            }
        });
        alert.setNegativeButton("NAH", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog=alert.create();

        moviesList.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                        itemdelete=i;
                        dialog.show();
                        return true;
                    }
                }
        );


        /* for (int i=0; i<movies.length; i++) {
            movieTitles.add(movies[i]);
            urlList.add(urls[i]);
        }*/

    }

    @Override
    public void onStop() {
        super.onStop();
        SharedPreferences.Editor e = p.edit();
        for (int i=0; i < movieTitles.size(); i++) {
            e.putString("TITLE"+i, movieTitles.get(i));
            e.putString("CODE"+i, urlList.get(i));
            e.putInt("NEWSIZE",movieTitles.size());
        }
        e.apply();
    }

    public void addButtonPressed (View v) {
        Intent i = new Intent(this, AddActivity.class);
        startActivityForResult(i,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        movieTitles.add(data.getStringExtra("TITLE"));
        urlList.add(data.getStringExtra("CODE"));

        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this, R.layout.list_item_view, movieTitles);
        moviesList.setAdapter(adapter);

    }

}
