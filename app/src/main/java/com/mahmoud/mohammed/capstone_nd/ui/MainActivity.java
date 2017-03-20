package com.mahmoud.mohammed.capstone_nd.ui;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.mahmoud.mohammed.capstone_nd.R;
import com.mahmoud.mohammed.capstone_nd.adapter.MyAdapter;
import com.mahmoud.mohammed.capstone_nd.model.Book;
import com.mahmoud.mohammed.capstone_nd.remote.config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {
    private RecyclerView mRecyclerView;
    RecyclerView recyclerView;
   // @BindView(R.id.toolbar)
    Toolbar toolbar;
   // @BindView(R.id.swipe_refresh_layout)
     SwipeRefreshLayout mSwipeRefreshLayout;
    RequestQueue requestQueue;
    private List<Book> Books=new ArrayList<>();
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
       // setSupportActionBar(toolbar);
        requestQueue = Volley.newRequestQueue(this);
        recyclerView=new RecyclerView(this);


         recyclerView = (RecyclerView) findViewById(R.id.my_recycler_viewt);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

            sendJsonRequest(getResources().getString(R.string.url)+"&"+(getResources().getString(R.string.print_type_magazines))+"&"+ config.API_KEY);
    }

    public void sendJsonRequest(String url) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, (String) null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    //    Toast.makeText(MainActivity.this,response+"",Toast.LENGTH_SHORT).show();

                Books=new ArrayList<>();

                try {

                    JSONArray mResultArray = response.getJSONArray("items");
                    for (int i = 0; i < mResultArray.length(); i++) {
                        JSONObject mResultObject = mResultArray.getJSONObject(i);
                        Book book = new Book();
                        book.setId(mResultObject.getString("id"));
                        JSONObject volumeInfo=mResultObject.getJSONObject("volumeInfo");
                        book.setTitle(volumeInfo.getString("title"));
                        book.setPublishedDate(volumeInfo.getString("publishedDate"));
                        book.setDescription(volumeInfo.getString("description"));
                        JSONObject imageinfo=volumeInfo.getJSONObject("imageLinks");
                        String img=imageinfo.getString("smallThumbnail");
                        book.setImageUrl(img);
                        Books.add(book);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                adapter = new MyAdapter(Books, MainActivity.this, new MyAdapter.RecyclerViewClickListener() {
                    @Override
                    public void recyclerViewListClicked(View v, int position) {
                        startActivity(new Intent(MainActivity.this,DetailActivity.class));
                    }
                });
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        requestQueue.add(request);


    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
