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

public class techAdapter extends RecyclerView.Adapter<techAdapter.ViewHolder> {
    Context context;
    private ArrayList<topheadlinesmodel> techheadlines;
    onItemClickFromListListener mylistener;

    public techAdapter(Context context, ArrayList<topheadlinesmodel> techheadlines, onItemClickFromListListener mylistener) {
        this.context = context;
        this.techheadlines = techheadlines;
        this.mylistener = mylistener;
    }

    @NonNull
    @Override
    public techAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View itemview=inflater.inflate(R.layout.scienceitem,parent,false);

        return new techAdapter.ViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull techAdapter.ViewHolder holder, int position) {
        if(techheadlines.get(position).getUrltoimage()!="null"){
            Picasso.get().load(techheadlines.get(position).getUrltoimage()).into(holder.bgimage);
        }
        if(techheadlines.get(position).getAuthor()!="null"){
            holder.author.setText("Author : "+techheadlines.get(position).getAuthor());
        }
        if(techheadlines.get(position).getDescription()!="null"){
            holder.description.setText(techheadlines.get(position).getDescription());
        }
        if(techheadlines.get(position).getPublishdate()!="null"){
            holder.publishdate.setText("publish date : "+techheadlines.get(position).getPublishdate().split("T")[0]);
        }
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return techheadlines.size();
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
