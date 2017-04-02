package com.mahmoud.mohammed.capstone_nd.ui;

import android.database.Cursor;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.mahmoud.mohammed.capstone_nd.R;
import com.mahmoud.mohammed.capstone_nd.data.BookLoader;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

public class Search extends AppCompatActivity {
    Toolbar mToolbar;
    ArrayList<String> search_filter;
    final static int UNIQUE_ID0 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_search);
        setSupportActionBar(mToolbar);
        search_filter = new ArrayList<>();
        //getLoaderManager().initLoader(UNIQUE_ID0, null, this);

    }

}

