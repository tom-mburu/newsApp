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

public class sportsAdapter extends RecyclerView.Adapter<sportsAdapter.ViewHolder> {
    Context context;
    ArrayList<topheadlinesmodel> sportsHeadlines;
    onItemClickListener listeneter;

    public sportsAdapter(Context context, ArrayList<topheadlinesmodel> sportsHeadlines, onItemClickListener listeneter) {
        this.context = context;
        this.sportsHeadlines = sportsHeadlines;
        this.listeneter = listeneter;
    }

    @NonNull
    @Override
    public sportsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View itemview= inflater.inflate(R.layout.healthitem,parent,false);

        return new sportsAdapter.ViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull sportsAdapter.ViewHolder holder, int position) {
        if(sportsHeadlines.get(position).getUrltoimage()!="null"){
            Picasso.get().load(sportsHeadlines.get(position).getUrltoimage()).into(holder.backgroundImg);
        }
        if(sportsHeadlines.get(position).getDescription()!="null"){
            holder.description.setText(sportsHeadlines.get(position).getDescription());
        }
        if(sportsHeadlines.get(position).getPublishdate()!="null"){
            holder.publishDate.setText("publish date : "+sportsHeadlines.get(position).getPublishdate().split("T")[0]);
        }
        if(sportsHeadlines.get(position).getAuthor()!="null"){
            holder.author.setText("Author : "+sportsHeadlines.get(position).getAuthor());
        }
        holder.bind(position);

    }

    @Override
    public int getItemCount() {
        return sportsHeadlines.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        View itemView;
        ImageView backgroundImg;
        TextView description ;
        TextView publishDate;
        TextView author;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView=itemView;
            backgroundImg=itemView.findViewById(R.id.backgroundImg);
            description=itemView.findViewById(R.id.description);
            publishDate =itemView.findViewById(R.id.publishdate);
            author=itemView.findViewById(R.id.author);
        }
        public void bind(int position){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    listeneter.onItemClick(v,position,true);
                }
            });
        }
    }
}
