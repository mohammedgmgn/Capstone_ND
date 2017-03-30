package com.mahmoud.mohammed.capstone_nd.ui;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.mahmoud.mohammed.capstone_nd.R;
import com.mahmoud.mohammed.capstone_nd.data.BookContract;
import com.mahmoud.mohammed.capstone_nd.data.BookLoader;
import com.mahmoud.mohammed.capstone_nd.model.Book;

import static com.mahmoud.mohammed.capstone_nd.ui.MainActivity.UNIQUE_ID;

public class DetailActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor>{
    final static int ID_DEFAULT_VALUE=0;
    private long mFirstID;
    private long mSelectedItemId;
    private Cursor mCursor;
    private static String TAG="tag";
    private  String URI;

    //  private long mStartId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
         mFirstID=getIntent().getIntExtra("postion",ID_DEFAULT_VALUE);
         //URI=getIntent().getStringExtra("uri");
       //  Toast.makeText(this,mFirstID+"",Toast.LENGTH_SHORT).show();
       // mSelectedItemId = BookContract.BookItems.getItemId(Uri.parse(URI));
        getLoaderManager().initLoader(1, null, this);


    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return BookLoader.newInstanceForItemId(this, mFirstID);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCursor=data;
        Toast.makeText(this,mCursor.getCount()+"",Toast.LENGTH_LONG).show();
       // getString(BookLoader.Query.TITLE

    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
