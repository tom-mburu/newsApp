package com.example.news.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.news.R;
import com.example.news.detailedNewsArticle;
import com.example.news.interfaces.onItemClickListener;
import com.example.news.models.topheadlinesmodel;
import com.example.news.recycleradapters.healthAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link health#newInstance} factory method to
 * create an instance of this fragment.
 */
public class health extends Fragment implements onItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private RecyclerView healthHeadlinesView;
    private SwipeRefreshLayout refreshLayout;
    private ArrayList<topheadlinesmodel> healthheadlines=new ArrayList<>();
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public health() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment health.
     */
    // TODO: Rename and change types and number of parameters
    public static health newInstance(String param1, String param2) {
        health fragment = new health();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_health, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        healthHeadlinesView=view.findViewById(R.id.health);
refreshLayout=view.findViewById(R.id.reflesh);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String url="https://newsapi.org/v2/top-headlines?country=us&category=health&apiKey=063dfaa67ee640d8aaeec97a1f00c993&category=health";
        ProgressDialog dialog=new ProgressDialog(getActivity());
        dialog.setTitle("Contacting api");
        dialog.setMessage("getting health articles please wait...");
        RequestQueue queue= Volley.newRequestQueue(getActivity());
        StringRequest request=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                refreshLayout.setRefreshing(false);
                try {
                    dialog.hide();
                    JSONObject resp=new JSONObject(response);
                    JSONArray healthArticles=resp.getJSONArray("articles");
                    for(int x=0;x<healthArticles.length();x++){
                        JSONObject article=healthArticles.getJSONObject(x);
                        String imageUrl;
                        String description;
                        String publishdate;
                        String author;
                        String urlToFullArticle;
                        imageUrl=article.getString("urlToImage");
                        description=article.getString("description");
                        publishdate=article.getString("publishedAt");
                        author=article.getString("author");
                        urlToFullArticle=article.getString("url");
                        topheadlinesmodel healthModel=new topheadlinesmodel();
                        healthModel.setUrltoimage(imageUrl);
                        healthModel.setDescription(description);
                        healthModel.setPublishdate(publishdate);
                        healthModel.setAuthor(author);
                        healthModel.setUrltonewsArticle(urlToFullArticle);
                        healthheadlines.add(healthModel);
                    }
                    if(healthheadlines.size()>0){
                       healthAdapter adapter=new healthAdapter(getActivity(),healthheadlines,health.this);
                        LinearLayoutManager manager=new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
                        healthHeadlinesView.setAdapter(adapter);
                        healthHeadlinesView.setLayoutManager(manager);


                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                refreshLayout.setRefreshing(false);
                Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            public Map<String,String> getHeaders() throws AuthFailureError {
                HashMap<String,String> params=new HashMap<>();
                //params.put("key","7ab462270865ae3cf6ab77db719f610d");
                //String creds=String.format("%s:%s","tom_mburu","38946086");
                // params.put("accept","application/json");
                params.put("User-Agent","Mozilla/5.0");

                // String auth="Bearer "+ "063dfaa67ee640d8aaeec97a1f00c993";
                //params.put("Authorization",auth);
                return params;
            }
        };
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(true);
                queue.add(request);
            }
        });
        queue.add(request);
        dialog.show();
    }

    @Override
    public void onItemClick(View v, int position, boolean moreheadlines) {
        Intent intent=new Intent(getActivity(), detailedNewsArticle.class);
        if(moreheadlines){
            intent.putExtra("url",healthheadlines.get(position).getUrltonewsArticle());
            startActivity(intent);
        }
    }
}