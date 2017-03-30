package com.mahmoud.mohammed.capstone_nd.adapter;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mahmoud.mohammed.capstone_nd.R;
import com.mahmoud.mohammed.capstone_nd.data.BookContract;
import com.mahmoud.mohammed.capstone_nd.data.BookLoader;
import com.mahmoud.mohammed.capstone_nd.model.Book;
import com.mahmoud.mohammed.capstone_nd.ui.DetailActivity;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.Myholder> {
    private Cursor mCursor;
    Context ctx;
    RecyclerViewClickListener itemListener;

    public MyAdapter() {
    }

    public MyAdapter(Cursor cursor, Context ctx,RecyclerViewClickListener itemListener) {
        mCursor = cursor;
        this.ctx = ctx;
        this.itemListener=itemListener;
    }

    @Override
    public long getItemId(int position) {
        mCursor.moveToPosition(position);
        return mCursor.getLong(BookLoader.Query._ID);
    }


    @Override
    public Myholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_row, parent, false);
        final Myholder holder = new Myholder(row);

        return holder;
    }

    @Override
    public void onBindViewHolder(Myholder holder, int position) {

        mCursor.moveToPosition(position);
        String title = mCursor.getString(BookLoader.Query.TITLE);
        String desc = mCursor.getString(BookLoader.Query.DESCRIPTION);
        holder.booktitle.setText(title);
        holder.bookdesc.setText(desc);
        Glide.with(ctx).load(mCursor.getString(BookLoader.Query.PHOTO_URL)).diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.poster);

    }

    @Override
    public int getItemCount() {

        return mCursor.getCount();
    }

    class Myholder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView booktitle, bookrate, bookdesc;
        ImageView poster;
        LinearLayout linearLayout;

        public Myholder(View itemView) {
            super(itemView);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.book_row_layout);
            booktitle = (TextView) itemView.findViewById(R.id.booktitleTV);
            bookdesc = (TextView) itemView.findViewById(R.id.bookdescTV);
            poster = (ImageView) itemView.findViewById(R.id.bookposteIMG);
            linearLayout.setContentDescription(booktitle.getText().toString());

              itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemListener.recyclerViewListClicked(view, this.getLayoutPosition());

        }

    }

    public interface RecyclerViewClickListener {

        void recyclerViewListClicked(View v, int position);
    }


}



