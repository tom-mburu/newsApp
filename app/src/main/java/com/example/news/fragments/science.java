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
import com.example.news.interfaces.onItemClickFromListListener;
import com.example.news.models.topheadlinesmodel;
import com.example.news.recycleradapters.scienceAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link science#newInstance} factory method to
 * create an instance of this fragment.
 */
public class science extends Fragment implements onItemClickFromListListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private RecyclerView scienceheadlinesView;
    private SwipeRefreshLayout refreshLayout;
    private ArrayList<topheadlinesmodel> scienceheadlines=new ArrayList<>();
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public science() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment science.
     */
    // TODO: Rename and change types and number of parameters
    public static science newInstance(String param1, String param2) {
        science fragment = new science();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_science, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        scienceheadlinesView =view.findViewById(R.id.scienceheadlines);
        refreshLayout=view.findViewById(R.id.reflesh);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ProgressDialog dialog=new ProgressDialog(getActivity());
        dialog.setTitle("contacting API");
        dialog.setMessage("Getting science articles ,please wait.....");
        RequestQueue queue= Volley.newRequestQueue(getActivity());
        String url="https://newsapi.org/v2/top-headlines?country=us&category=science&apiKey=063dfaa67ee640d8aaeec97a1f00c993&category=health";
        StringRequest request=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.hide();
                refreshLayout.setRefreshing(false);
                try {
                    JSONObject resp=new JSONObject(response);
                    JSONArray scienceArticles=resp.getJSONArray("articles");
                    for (int x=0;x<scienceArticles.length();x++){
                        JSONObject article=scienceArticles.getJSONObject(x);
                        String bgImage;
                        String description;
                        String author;
                        String publishdate;
                        String urlToFullArticle;
                        bgImage=article.getString("urlToImage");
                        description=article.getString("description");
                        author=article.getString("author");
                        publishdate=article.getString("publishedAt");
                        urlToFullArticle=article.getString("url");
                        topheadlinesmodel scienceModel=new topheadlinesmodel();
                        scienceModel.setUrltoimage(bgImage);
                        scienceModel.setDescription(description);
                        scienceModel.setAuthor(author);
                        scienceModel.setPublishdate(publishdate);
                        scienceModel.setUrltonewsArticle(urlToFullArticle);
                        scienceheadlines.add(scienceModel);
                    }
                    if(scienceheadlines.size()>0){
                        scienceAdapter adapter=new scienceAdapter(getActivity(),scienceheadlines,science.this);
                        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
                        scienceheadlinesView.setLayoutManager(layoutManager);
                        scienceheadlinesView.setAdapter(adapter);
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.hide();
                refreshLayout.setRefreshing(false);
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();

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
        queue.add(request);
        dialog.show();
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                queue.add(request
                );
                refreshLayout.setRefreshing(true);
            }
        });
    }

    @Override
    public void onItemClick(View v, int position) {
        Intent intent=new Intent(getActivity(), detailedNewsArticle.class);
        intent.putExtra("url",scienceheadlines.get(position).getUrltonewsArticle());
        startActivity(intent);

    }
}