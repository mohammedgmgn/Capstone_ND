package com.mahmoud.mohammed.capstone_nd.ui;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.mahmoud.mohammed.capstone_nd.R;
import com.mahmoud.mohammed.capstone_nd.data.BookContract;
import com.mahmoud.mohammed.capstone_nd.data.BookLoader;

import static com.mahmoud.mohammed.capstone_nd.ui.MainActivity.UNIQUE_ID;

public class DetailActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor>{
final static int ID_DEFAULT_VALUE=0;
    private long mFirstID;
    private long mSelectedItemId;
    private Cursor mCursor;

    //  private long mStartId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
         mFirstID=getIntent().getIntExtra("postion",ID_DEFAULT_VALUE);
        Toast.makeText(this,mFirstID+"",Toast.LENGTH_SHORT).show();

        getLoaderManager().initLoader(0, null, this);
        if(savedInstanceState==null)
        {
            mSelectedItemId=BookContract.BookItems.getItemId(BookContract.BookItems.buildItemUri(mFirstID));
        }

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return BookLoader.newAllArticlesInstance(this);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
