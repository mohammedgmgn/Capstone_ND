package com.mahmoud.mohammed.capstone_nd.ui;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.provider.Settings;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mahmoud.mohammed.capstone_nd.R;
import com.mahmoud.mohammed.capstone_nd.data.BookContract;
import com.mahmoud.mohammed.capstone_nd.data.BookLoader;
import com.mahmoud.mohammed.capstone_nd.model.Book;

import static com.mahmoud.mohammed.capstone_nd.ui.MainActivity.UNIQUE_ID;

public class DetailActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {
    final static int ID_DEFAULT_VALUE = 0;
    private int mFirstID;
    private Cursor mCursor;
    private static String TAG = "tag";
    Toolbar mToolbar;
    ImageView mImageView;
    CollapsingToolbarLayout mCollapsingToolbar;
    TextView mDescription;


    //  private long mStartId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mFirstID = getIntent().getIntExtra("postion", ID_DEFAULT_VALUE);
        //URI=getIntent().getStringExtra("uri");
        //  Toast.makeText(this,mFirstID+"",Toast.LENGTH_SHORT).show();
        // mSelectedItemId = BookContract.BookItems.getItemId(Uri.parse(URI));
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        mImageView = (ImageView) findViewById(R.id.im_view);
        mDescription=(TextView)findViewById(R.id.tv_description);
        mCollapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbartest);
        getLoaderManager().initLoader(1, null, this);
        setSupportActionBar(mToolbar);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        //   return BookLoader.newInstanceForItemId(this, mFirstID);
        return BookLoader.newAllArticlesInstance(this);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCursor = data;
        mCursor.moveToPosition(mFirstID);
        bindView(mCursor);
    }

    private void bindView(Cursor mCursor) {
        String title = mCursor.getString(BookLoader.Query.TITLE);
        String img_url = mCursor.getString(BookLoader.Query.PHOTO_URL);
        String desc = mCursor.getString(BookLoader.Query.DESCRIPTION);

        mCursor.getString(BookLoader.Query.DESCRIPTION);
        mCollapsingToolbar.setTitle(title);
        mDescription.setText(desc);
        Glide.with(this).load(img_url).diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mImageView);
        Toast.makeText(this, mCursor.getString(BookLoader.Query.TITLE), Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursor = null;

    }
}
