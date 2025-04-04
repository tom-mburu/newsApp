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

public class scienceAdapter extends RecyclerView.Adapter<scienceAdapter.ViewHolder> {
    Context context;
    ArrayList<topheadlinesmodel> scienceheadlines;
    onItemClickFromListListener mylistener;

    public scienceAdapter(Context context, ArrayList<topheadlinesmodel> scienceheadlines, onItemClickFromListListener mylistener) {
        this.context = context;
        this.scienceheadlines = scienceheadlines;
        this.mylistener = mylistener;
    }

    @NonNull
    @Override
    public scienceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View itemview=inflater.inflate(R.layout.scienceitem,parent,false);

        return new ViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull scienceAdapter.ViewHolder holder, int position) {
       if(scienceheadlines.get(position).getUrltoimage()!="null"){
        Picasso.get().load(scienceheadlines.get(position).getUrltoimage()).into(holder.bgimage);
       }
       if(scienceheadlines.get(position).getAuthor()!="null"){
           holder.author.setText("Author : "+scienceheadlines.get(position).getAuthor());
       }
       if(scienceheadlines.get(position).getDescription()!="null"){
           holder.description.setText(scienceheadlines.get(position).getDescription());
       }
       if(scienceheadlines.get(position).getPublishdate()!="null"){
           holder.publishdate.setText("publish date : "+scienceheadlines.get(position).getPublishdate().split("T")[0]);
       }
       holder.bind(position);

    }

    @Override
    public int getItemCount() {
        return scienceheadlines.size();
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
