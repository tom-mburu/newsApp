package com.example.news.recycleradapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.news.R;
import com.example.news.interfaces.onItemClickListener;
import com.example.news.models.topheadlinesmodel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class topheadlinesrecyclerviewAdapter extends RecyclerView.Adapter<topheadlinesrecyclerviewAdapter.ViewHolder> {
    Context context;
    ArrayList<topheadlinesmodel> topheadlines;
    onItemClickListener mylistener;

    public topheadlinesrecyclerviewAdapter(Context context,onItemClickListener listener, ArrayList<topheadlinesmodel> topheadlines) {
        this.context = context;
        this.topheadlines = topheadlines;
        this.mylistener=listener;
    }

    @NonNull
    @Override
    public topheadlinesrecyclerviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
            View ItemView=inflater.inflate(R.layout.topitem,parent,false);
        return new ViewHolder(ItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull topheadlinesrecyclerviewAdapter.ViewHolder holder, int position) {
        if(topheadlines.get(position).getUrltoimage()!="null"){
        Picasso.get().load(topheadlines.get(position).getUrltoimage()).into(holder.bgImage);
        }
        if(topheadlines.get(position).getDescription()!="null"){
        holder.desc.setText(topheadlines.get(position).getDescription());
        }
        if(topheadlines.get(position).getAuthor()!="null"){
        holder.author.setText("Author : "+topheadlines.get(position).getAuthor());
        }
        if(topheadlines.get(position).getPublishdate()!="null"){
        holder.publishdate.setText("publish date : "+topheadlines.get(position).getPublishdate().split("T")[0]);
        }
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return topheadlines.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        View itemView;
        ImageView bgImage;
        TextView desc;
        TextView author;
        TextView publishdate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView=itemView;
            bgImage=itemView.findViewById(R.id.bgImage);
            desc=itemView.findViewById(R.id.desc);
            author=itemView.findViewById(R.id.author);
            publishdate=itemView.findViewById(R.id.publishdate);
        }
        public void  bind(int position){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //call a method from fragment to start activity with news details
                    mylistener.onItemClick(v,position,false);
                }
            });
        }

    }
}
