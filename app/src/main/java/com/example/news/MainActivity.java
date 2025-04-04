package com.example.news;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.example.news.fragments.health;
import com.example.news.fragments.home;
import com.example.news.fragments.science;
import com.example.news.fragments.searchResults;
import com.example.news.fragments.sports;
import com.example.news.fragments.technology;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    TextView category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar =findViewById(R.id.toolbar);
        EditText searchbar=findViewById(R.id.searchfield);
        ImageButton searchbtn=findViewById(R.id.searchbtn);
        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query=searchbar.getText().toString();
                if(query.isEmpty()!=true){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontainer, searchResults.newInstance(query,"xyz"))
                            .commit();
                }
            }
        });


       setSupportActionBar(toolbar);

       toolbar.setTitle("");
      // toolbar.setTitleTextColor(Color.parseColor("#FF3D00"));
        category=findViewById(R.id.category);
        //FrameLayout container=findViewById(R.id.container);
        BottomNavigationView navView=findViewById(R.id.nav_view);
        //navigate through the fragments
        //default tab
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontainer,home.newInstance("xyz","xyz")).commit();
        category.setText("home");
        navView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemid=item.getItemId();
                Fragment fragment=null;
                if(itemid==R.id.home){
                    //home fragment
                    fragment= home.newInstance("xyz","xyz");
                    if (fragment instanceof home){
                        category.setText("home");
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontainer,fragment).commit();
                        //fragment=null;
                        //transaction.replace(R.id.container,fragment).commit();
                    }
                    return true;
                } else if (itemid==R.id.health) {
                    fragment=health.newInstance("xyz","xyz");
                    if (fragment instanceof health){
                        category.setText("health");
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontainer,fragment).commit();
                        //fragment=null;
                       // transaction.replace(R.id.container,fragment).commit();
                    }
                    //health fragment


                } else if (itemid==R.id.science) {
                    //science fragment
                    fragment= science.newInstance("xyz","xyz");
                    if(fragment instanceof science){
                        category.setText("science");
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontainer,fragment).commit();
                        //fragment=null;
                        //transaction.replace(R.id.container,fragment).commit();
                    }

                } else if (itemid==R.id.sports) {
                    //sports fragment
                    fragment= sports.newInstance("xyz","xyz");
                    if(fragment instanceof sports){
                        category.setText("sports");
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontainer,fragment).commit();
                        //fragment=null;
                   // transaction.replace(R.id.container,fragment).commit();
                    }

                } else if (itemid==R.id.technology) {
                    //technology
                    fragment= technology.newInstance("xyz","xyz");
                    if(fragment instanceof technology){
                        category.setText("technology");
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontainer,fragment).commit();
                        //fragment=null;
                    //transaction.replace(R.id.container,fragment).commit();
                    }
                }
                if(fragment!=null){

                    //Toast.makeText(MainActivity.this,fragment.getClass().getName(),Toast.LENGTH_LONG).show();
                }else {

                }
                return true;
            }
        });

    }
}