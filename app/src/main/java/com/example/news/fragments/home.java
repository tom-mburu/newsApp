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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.news.R;
import com.example.news.detailedNewsArticle;
import com.example.news.interfaces.onItemClickListener;
import com.example.news.models.topheadlinesmodel;
import com.example.news.recycleradapters.moreheadlinesAdapter;
import com.example.news.recycleradapters.topheadlinesrecyclerviewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class home extends Fragment implements onItemClickListener {
    ArrayList<topheadlinesmodel> headlines=new ArrayList<>();
    String url= "https://newsapi.org/v2/top-headlines?country=us&category=general&apiKey=063dfaa67ee640d8aaeec97a1f00c993";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    JSONArray[] availableheadlines=new JSONArray[1];
    private RecyclerView topheadlinesView;
    private SwipeRefreshLayout refleshLayout;
    private RecyclerView moretopheadlinesView;
    private ArrayList<topheadlinesmodel> moreheadlinesList=new ArrayList<>();
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment home.
     */
    // TODO: Rename and change types and number of parameters
    public static home newInstance(String param1, String param2) {
        home fragment = new home();
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
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        topheadlinesView=view.findViewById(R.id.topitems);
        moretopheadlinesView=view.findViewById(R.id.moreitems);
        refleshLayout=view.findViewById(R.id.reflesh);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ProgressDialog dialog=new ProgressDialog(getActivity());
        dialog.setCancelable(false);
        dialog.setTitle("Contacting API");
        dialog.setMessage("Fetching news for you...wait");
        RequestQueue queue=Volley.newRequestQueue(getActivity());
        StringRequest homeTopHeadlines=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.hide();
                refleshLayout.setRefreshing(false);
//Toast.makeText(getActivity(),response,Toast.LENGTH_LONG).show();
                try {
                    JSONObject resp=new JSONObject(response);
                    JSONArray articles=resp.getJSONArray("articles");
                    for(int x=0;x<articles.length();x++){
                        JSONObject article=articles.getJSONObject(x);
                      //  Toast.makeText(getActivity(),article.getString("title"),Toast.LENGTH_LONG).show();
                        String urlToBgImage;
                        String desc;
                        String author;
                        String publishdate;
                        String urltofullarticle;
                        urlToBgImage=article.getString("urlToImage");
                        desc=article.getString("description");
                        author=article.getString("author");
                        publishdate=article.getString("publishedAt");
                        urltofullarticle=article.getString("url");
                        //setting the model object
                        topheadlinesmodel articlemodel=new topheadlinesmodel();
                        articlemodel.setUrltoimage(urlToBgImage);
                        articlemodel.setDescription(desc);
                        articlemodel.setPublishdate(publishdate);
                        articlemodel.setAuthor(author);
                        articlemodel.setUrltonewsArticle(urltofullarticle);
                        headlines.add(articlemodel);
                        if(x==10){
                            break;
                        }

                    }
                    for(int y=10;y>=10&&y<articles.length();y++){
                        JSONObject moreArtical=articles.getJSONObject(y);
                        String urlToBgImageMore;
                        String descMore;
                        String authorMore;
                        String publishdateMore;
                        String urltofullarticleMore;
                        urlToBgImageMore=moreArtical.getString("urlToImage");
                        descMore=moreArtical.getString("description");
                        authorMore=moreArtical.getString("author");
                        publishdateMore=moreArtical.getString("publishedAt");
                        urltofullarticleMore=moreArtical.getString("url");
                        topheadlinesmodel articleMoremodel=new topheadlinesmodel();
                        articleMoremodel.setUrltoimage(urlToBgImageMore);
                        articleMoremodel.setDescription(descMore);
                        articleMoremodel.setAuthor(authorMore);
                        articleMoremodel.setPublishdate(publishdateMore);
                        articleMoremodel.setUrltonewsArticle(urltofullarticleMore);
                        moreheadlinesList.add(articleMoremodel);



                    }
                    } catch (JSONException ex) {
                    throw new RuntimeException(ex);
                }
               if(headlines.size()>0){
                    // we have model objects inside the
                    topheadlinesrecyclerviewAdapter adapter=new topheadlinesrecyclerviewAdapter(getActivity(),home.this,headlines);
                    topheadlinesView.setAdapter(adapter);
                    LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false);
                    topheadlinesView.setLayoutManager(layoutManager);

                }
               if(moreheadlinesList.size()>0){
                  moreheadlinesAdapter moreAdapter=new moreheadlinesAdapter(moreheadlinesList,getActivity(),home.this);
                   moretopheadlinesView.setAdapter(moreAdapter);
                   LinearLayoutManager moremanager=new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
                   moretopheadlinesView.setLayoutManager(moremanager);
               }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.hide();
                refleshLayout.setRefreshing(false);
                Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_LONG).show();



            }
        }){
            @Override
            public Map<String,String> getHeaders() throws AuthFailureError{
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
        dialog.show();
        queue.add(homeTopHeadlines);
        refleshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refleshLayout.setRefreshing(true);
                queue.add(homeTopHeadlines);
            }
        });

    }

    @Override
    public void onItemClick(View v, int position,boolean moreheadlines) {
        Intent intent=new Intent(getActivity(), detailedNewsArticle.class);
        if(moreheadlines){
            //get url from moreheadlines
            String url=moreheadlinesList.get(position).getUrltonewsArticle();
            intent.putExtra("url",url);
            startActivity(intent);
        } else  {
           //get url from topheadlines

            String url=headlines.get(position).getUrltonewsArticle();
            intent.putExtra("url",url);
            startActivity(intent);
        }

    }
}