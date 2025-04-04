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
import android.widget.TextView;
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
import com.example.news.interfaces.onItemClickListener;
import com.example.news.models.topheadlinesmodel;
import com.example.news.recycleradapters.scienceAdapter;
import com.example.news.recycleradapters.searchAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link searchResults#newInstance} factory method to
 * create an instance of this fragment.
 */
public class searchResults extends Fragment implements onItemClickFromListListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private RecyclerView searchresultsView;
    private TextView errormsg;
    private ArrayList<topheadlinesmodel> searchresult=new ArrayList<>();
    private static String query;
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public searchResults() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment searchResults.
     */
    // TODO: Rename and change types and number of parameters
    public static searchResults newInstance(String param1, String param2) {
        searchResults fragment = new searchResults();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        query=param1;
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchresultsView=view.findViewById(R.id.searchResults);
        errormsg=view.findViewById(R.id.errormsg);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
       String mydate;
        Date date=new Date();
        SimpleDateFormat format=new SimpleDateFormat("yyyy-mm-dd");
       String formattedDate= format.format(date);
       String [] form=formattedDate.split("-");
      String lastpart=form[form.length-1];
       int intLastPart=Integer.parseInt(lastpart);
       if(intLastPart==1){
         mydate=formattedDate;
       }else{
           mydate=form[0]+"-"+form[1]+"-"+"01";
       }
      // String url="https://newsapi.org/v2/everything?q=Apple&from=2024-12-18&sortBy=popularity&apiKey=063dfaa67ee640d8aaeec97a1f00c993";
       String url= "https://newsapi.org/v2/everything?q="+query+"&from="+mydate+"&sortBy=popularity&apiKey=063dfaa67ee640d8aaeec97a1f00c993";
        ProgressDialog dialog=new ProgressDialog(getActivity());
        dialog.setTitle("contacting API");
        dialog.setMessage("Getting science articles ,please wait.....");
        RequestQueue queue= Volley.newRequestQueue(getActivity());
        StringRequest request=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.hide();

                try {
                    JSONObject resp=new JSONObject(response);
                    JSONArray scienceArticles=resp.getJSONArray("articles");
                    if(scienceArticles.length()==0){
                       errormsg.setVisibility(View.VISIBLE);
                    }else{
                        errormsg.setVisibility(View.INVISIBLE);
                    }
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
                        topheadlinesmodel searchModel=new topheadlinesmodel();
                        searchModel.setUrltoimage(bgImage);
                        searchModel.setDescription(description);
                        searchModel.setAuthor(author);
                        searchModel.setPublishdate(publishdate);
                        searchModel.setUrltonewsArticle(urlToFullArticle);
                        searchresult.add(searchModel);
                    }
                    if(searchresult.size()>0){
                        searchAdapter adapter=new searchAdapter(getActivity(),searchresult,searchResults.this);
                        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
                       searchresultsView.setLayoutManager(layoutManager);
                        searchresultsView.setAdapter(adapter);
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.hide();

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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_results, container, false);
    }

    @Override
    public void onItemClick(View v, int position) {
        Intent intent=new Intent(getActivity(), detailedNewsArticle.class);
        intent.putExtra("url",searchresult.get(position).getUrltonewsArticle());
        startActivity(intent);

    }
}