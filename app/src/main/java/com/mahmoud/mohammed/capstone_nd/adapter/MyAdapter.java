package com.mahmoud.mohammed.capstone_nd.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mahmoud.mohammed.capstone_nd.R;
import com.mahmoud.mohammed.capstone_nd.model.Book;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.Myholder> {

    List<Book> moviesList ;
    Context ctx;
    RecyclerViewClickListener itemListener;

    public MyAdapter(List<Book> moviesList, Context context, RecyclerViewClickListener listener) {
        this.moviesList = moviesList;
        this.ctx=context;
        this.itemListener=listener;
    }

    @Override
    public Myholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_row,parent,false);
        Myholder holder = new Myholder(row);
        return holder;
    }

    @Override
    public void onBindViewHolder(Myholder holder, int position) {

        Book book = moviesList.get(position);
        holder.booktitle.setText(book.getTitle());
        holder.bookdesc.setText(book.getDescription());

        Glide.with(ctx).load(book.getImageUrl()).diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.poster);

    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    class Myholder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView booktitle,bookrate,bookdesc ;
        ImageView poster;

        public Myholder(View itemView) {
            super(itemView);
            booktitle = (TextView) itemView.findViewById(R.id.booktitleTV);
            bookdesc = (TextView) itemView.findViewById(R.id.bookdescTV);
            poster = (ImageView) itemView.findViewById(R.id.bookposteIMG);
            itemView.setOnClickListener(this);

        }
        @Override
        public void onClick(View view) {
            itemListener.recyclerViewListClicked(view,this.getLayoutPosition());

        }

    }
    public interface RecyclerViewClickListener
    {

        void recyclerViewListClicked(View v, int position);
    }



}



