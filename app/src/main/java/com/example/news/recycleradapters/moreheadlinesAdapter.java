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

public class moreheadlinesAdapter extends RecyclerView.Adapter<moreheadlinesAdapter.ViewHolder> {
    ArrayList<topheadlinesmodel> moreheadlines;
    Context context;
    onItemClickListener listener;

    public moreheadlinesAdapter(ArrayList<topheadlinesmodel> moreheadlines, Context context, onItemClickListener listener) {
        this.moreheadlines = moreheadlines;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public moreheadlinesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(context);
        View itemview=inflater.inflate(R.layout.moreitem,parent,false);

        return new ViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull moreheadlinesAdapter.ViewHolder holder, int position) {
        if(moreheadlines.get(position).getUrltoimage()!="null"){
        Picasso.get().load(moreheadlines.get(position).getUrltoimage()).into(holder.bgImage);
        }
        if(moreheadlines.get(position).getDescription()!="null"){
        holder.description.setText(moreheadlines.get(position).getDescription());
        }
        if(moreheadlines.get(position).getAuthor()!="null"){
            holder.author.setText("Author : "+moreheadlines.get(position).getAuthor());
        }
        if(moreheadlines.get(position).getPublishdate()!="null"){
            holder.publishAt.setText("published date : "+moreheadlines.get(position).getPublishdate().split("T")[0]);
        }
        holder.bind(position);
    }


    @Override
    public int getItemCount() {
        return moreheadlines.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
View itemView;
ImageView bgImage;
TextView description;
TextView author;
TextView publishAt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView=itemView;
            bgImage=itemView.findViewById(R.id.backgroundImg);
            description=itemView.findViewById(R.id.description);
            author=itemView.findViewById(R.id.author);
            publishAt=itemView.findViewById(R.id.publishAt);

        }
        public void bind(int position){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //what to do if this news article is clicked
                    listener.onItemClick(v,position,true);
                }
            });
        }
    }
}
