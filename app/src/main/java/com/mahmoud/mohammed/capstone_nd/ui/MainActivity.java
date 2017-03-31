package com.mahmoud.mohammed.capstone_nd.ui;

import android.app.LoaderManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.Loader;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.iid.FirebaseInstanceId;
import com.mahmoud.mohammed.capstone_nd.Helper;
import com.mahmoud.mohammed.capstone_nd.R;
import com.mahmoud.mohammed.capstone_nd.adapter.MyAdapter;
import com.mahmoud.mohammed.capstone_nd.data.BookContract;
import com.mahmoud.mohammed.capstone_nd.data.BookLoader;
import com.mahmoud.mohammed.capstone_nd.data.BooksDatabase;
import com.mahmoud.mohammed.capstone_nd.data.UpdaterService;
import com.mahmoud.mohammed.capstone_nd.model.Book;
import com.mahmoud.mohammed.capstone_nd.remote.config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {
    RecyclerView recyclerView;
    //@BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    RequestQueue requestQueue;
    MyAdapter adapter;
    private boolean mIsRefreshing = false;
    TextView mNoData;
    final static int UNIQUE_ID=0;
    private final String[] BOOK_COLUMNS = {
            BookContract.BookItems._ID,
            BookContract.BookItems.SERVER_ID,
            BookContract.BookItems.TITLE,
            BookContract.BookItems.DESCRIPTION,
            BookContract.BookItems.PHOTO_URL,

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        requestQueue = Volley.newRequestQueue(this);
        String tkn = FirebaseInstanceId.getInstance().getToken();
        mNoData = (TextView) findViewById(R.id.error);
        recyclerView = new RecyclerView(this);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_viewt);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-3940256099942544~3347511713");
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        getLoaderManager().initLoader(UNIQUE_ID, null, this);
        if (savedInstanceState == null) {
            refresh();
        }
    }


    private void refresh() {
        startService(new Intent(this, UpdaterService.class));
        Cursor data = getContentResolver().query(BookContract.BookItems.buildDirUri(), BOOK_COLUMNS, null, null, null);

        if (!Helper.isNetworkConnected(this) && data.getCount() == 0) {
            mNoData.setText(getString(R.string.error_no_network));
            mNoData.setVisibility(View.VISIBLE);
        } else if (!Helper.isNetworkConnected(this)) {
            Toast.makeText(this, R.string.toast_no_connectivity, Toast.LENGTH_LONG).show();

        } else {
            mNoData.setVisibility(View.GONE);

        }

    }

    private BroadcastReceiver mRefreshingReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (UpdaterService.BROADCAST_ACTION_STATE_CHANGE.equals(intent.getAction())) {
                mIsRefreshing = intent.getBooleanExtra(UpdaterService.EXTRA_REFRESHING, false);
                updateRefreshingUI();

            }
        }
    };

    private void updateRefreshingUI() {
        mSwipeRefreshLayout.setRefreshing(mIsRefreshing);

    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(mRefreshingReceiver, new IntentFilter(UpdaterService.BROADCAST_ACTION_STATE_CHANGE));
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(mRefreshingReceiver);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return BookLoader.newAllArticlesInstance(this);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter = new MyAdapter(data, this);
        /*
         told my adapter that items will not change for given position
         and adapter no need to call onBindViewHolder for this position again
        * */
        adapter.setHasStableIds(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        recyclerView.setAdapter(null);

    }
}
