package com.mahmoud.mohammed.capstone_nd.data;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by siko on 3/19/2017.
 */

public class BookProvider extends ContentProvider {
    private SQLiteOpenHelper mOpenHelper;
    private static final int BOOKS = 0;
    private static final int BOOKS__ID = 1;
    private static final int BOOKS_SERVER_ID = 2;


    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = BookContract.CONTENT_AUTHORITY;
        matcher.addURI(authority, "items", BOOKS);
        matcher.addURI(authority, "items/#", BOOKS__ID);
        matcher.addURI(authority, "items/SERVER_ID", BOOKS_SERVER_ID);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new BooksDatabase(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        // COMPLETED (1) Get access to underlying database (read-only for query)
        // COMPLETED (2) Write URI match code and set a variable to return a Cursor
        // COMPLETED (3) Query for the tasks directory and write a default case
        // COMPLETED (4) Set a notification URI on the Cursor and return that Cursor
        // Return the desired Cursor
        final SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        final SelectionBuilder builder = buildSelection(uri);
        Cursor cursor = builder.where(selection, selectionArgs).query(db, projection, sortOrder);
        if (cursor != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return cursor;
    }

    private SelectionBuilder buildSelection(Uri uri) {
        final SelectionBuilder builder = new SelectionBuilder();
        final int match = sUriMatcher.match(uri);
        return buildSelection(uri, match, builder);
    }

    private SelectionBuilder buildSelection(Uri uri, int match, SelectionBuilder builder) {
        final List<String> paths = uri.getPathSegments();
        switch (match) {
            case BOOKS: {
                return builder.table(BooksDatabase.ITEMS);
            }
            case BOOKS__ID: {
                final String _id = paths.get(1);
                return builder.table(BooksDatabase.ITEMS).where(BookContract.BookItems._ID + "=?", _id);
            }
            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
    }

    @NonNull
    @Override
    public ContentProviderResult[] applyBatch(ArrayList<ContentProviderOperation> operations)
            throws OperationApplicationException {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            final int numOperations = operations.size();
            final ContentProviderResult[] results = new ContentProviderResult[numOperations];
            for (int i = 0; i < numOperations; i++) {
                results[i] = operations.get(i).apply(this, results, i);
            }
            db.setTransactionSuccessful();
            return results;
        } finally {
            db.endTransaction();
        }
    }

    /*
    * It also helps a ContentProvider that handles several different types of data
    * to keep things organized, e.g. a RailwayContentProvider might handle trains,
     * stations and tickets and can use the MIME type
    * to tell each one apart.

    Why MIME types?

    The use of MIME types is a natural consequence when you think about how a ContentProvider is accessed through URIs,
     i.e. something like an URL on the Internet.
     Just like on the Internet there are MIME types like text/html for web pages and image/jpeg for .jpg images,
     Android wants you to define a custom MIME type for any data type your ContentProvider handles.


    * */
    @Nullable
    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case BOOKS:
                return BookContract.BookItems.CONTENT_TYPE;
            case BOOKS__ID:
                return BookContract.BookItems.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }


    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case BOOKS: {
                final long _id = db.insertOrThrow(BooksDatabase.ITEMS, null, values);
                getContext().getContentResolver().notifyChange(uri, null);
                return BookContract.BookItems.buildItemUri(_id);
            }
            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final SelectionBuilder builder = buildSelection(uri);
        getContext().getContentResolver().notifyChange(uri, null);
        return builder.where(selection, selectionArgs).delete(db);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final SelectionBuilder builder = buildSelection(uri);
        getContext().getContentResolver().notifyChange(uri, null);
        return builder.where(selection, selectionArgs).update(db, values);
    }
}
