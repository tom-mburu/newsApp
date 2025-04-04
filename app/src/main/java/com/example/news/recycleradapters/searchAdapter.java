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
import com.example.news.interfaces.onItemClickFromListListener;
import com.example.news.models.topheadlinesmodel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class searchAdapter extends RecyclerView.Adapter<searchAdapter.ViewHolder> {
    Context context;
    ArrayList<topheadlinesmodel> searchheadlines;
    onItemClickFromListListener mylistener;

    public searchAdapter(Context context, ArrayList<topheadlinesmodel> searchheadlines, onItemClickFromListListener mylistener) {
        this.context = context;
        this.searchheadlines = searchheadlines;
        this.mylistener = mylistener;
    }

    @NonNull
    @Override
    public searchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View itemview=inflater.inflate(R.layout.searchitem,parent,false);

        return new searchAdapter.ViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull searchAdapter.ViewHolder holder, int position) {
        if(searchheadlines.get(position).getUrltoimage()!="null"){
            Picasso.get().load(searchheadlines.get(position).getUrltoimage()).into(holder.bgimage);
        }
        if(searchheadlines.get(position).getAuthor()!="null"){
            holder.author.setText("Author : "+searchheadlines.get(position).getAuthor());
        }
        if(searchheadlines.get(position).getDescription()!="null"){
            holder.description.setText(searchheadlines.get(position).getDescription());
        }
        if(searchheadlines.get(position).getPublishdate()!="null"){
            holder.publishdate.setText("publish date : "+searchheadlines.get(position).getPublishdate().split("T")[0]);
        }
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return searchheadlines.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView bgimage;
        TextView description;
        TextView author;
        TextView publishdate;
        View itemView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView=itemView;
            bgimage=itemView.findViewById(R.id.bgImage);
            description=itemView.findViewById(R.id.description);
            author=itemView.findViewById(R.id.author);
            publishdate=itemView.findViewById(R.id.publishdate);
        }
        public void bind(int position){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mylistener.onItemClick(v,position);
                }
            });

        }
    }
}
