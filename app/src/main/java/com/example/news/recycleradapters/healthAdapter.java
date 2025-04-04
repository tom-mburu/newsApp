package com.example.news.recycleradapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.TextureView;
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

public class healthAdapter extends RecyclerView.Adapter<healthAdapter.ViewHolder> {
    Context context;
    ArrayList<topheadlinesmodel> healthHeadlines;
    onItemClickListener listeneter;

    public healthAdapter(Context context, ArrayList<topheadlinesmodel> healthHeadlines, onItemClickListener listeneter) {
        this.context = context;
        this.healthHeadlines = healthHeadlines;
        this.listeneter = listeneter;
    }

    @NonNull
    @Override
    public healthAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
       View itemview= inflater.inflate(R.layout.healthitem,parent,false);

        return new ViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull healthAdapter.ViewHolder holder, int position) {
        if(healthHeadlines.get(position).getUrltoimage()!="null"){
        Picasso.get().load(healthHeadlines.get(position).getUrltoimage()).into(holder.backgroundImg);
        }
        if(healthHeadlines.get(position).getDescription()!="null"){
            holder.description.setText(healthHeadlines.get(position).getDescription());
        }
        if(healthHeadlines.get(position).getPublishdate()!="null"){
            holder.publishDate.setText("publish date : "+healthHeadlines.get(position).getPublishdate().split("T")[0]);
        }
        if(healthHeadlines.get(position).getAuthor()!="null"){
            holder.author.setText("Author : "+healthHeadlines.get(position).getAuthor());
        }
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return healthHeadlines.size();
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
