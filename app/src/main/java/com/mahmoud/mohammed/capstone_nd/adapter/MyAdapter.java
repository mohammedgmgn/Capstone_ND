package com.mahmoud.mohammed.capstone_nd.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by siko on 3/19/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.myholder> {
    List<Book> books = new ArrayList<>();
    Context ctx;
    RecyclerViewClickListener itemListener;

    public MyAdapter(List<Book> mylist, Context ctx, RecyclerViewClickListener listener) {
        this.books = mylist;
        this.ctx=ctx;
        this.itemListener=listener;
    }
    public MyAdapter() {
    }

    @Override
    public myholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row= LayoutInflater.from(parent.getContext()).inflate(R.layout.book_row,parent,false);
        myholder holder=new myholder(row);
        return holder;
    }

    @Override
    public void onBindViewHolder(myholder holder, int position) {

        String title= books.get(position).getTitle();
         String imgUrL=books.get(position).getImageUrl();
        holder.title.setText(title);
        Glide.with(ctx).load(imgUrL).diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }


    class myholder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView title;
        ImageView image;
        public myholder(View itemView) {
            super(itemView);
            title=(TextView)itemView.findViewById(R.id.txv_row);
            image=(ImageView) itemView.findViewById(R.id.bookposteIMG);

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
