package com.example.android.popularmovie2.UI;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android.popularmovie2.R;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("More Details");


        FragmentDetailActivity image = new FragmentDetailActivity();

        FragmentManager fragmentManager = getSupportFragmentManager();

        if(savedInstanceState == null) {
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_detail_container, image)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemChecked = item.getItemId();

//        switch (itemChecked){
//            case R.id.action_favourite:
//                Toast.makeText(this, "Clicked favorites", Toast.LENGTH_LONG).show();
//                break;
//            case R.id.action_popular:
//                Toast.makeText(this, "Clicked Popular", Toast.LENGTH_LONG).show();
//                break;
//            case R.id.action_top_rated:
//                Toast.makeText(this, "Clicked top rated", Toast.LENGTH_LONG).show();
//                break;
//            case android.R.id.home:
//                this.finish();
//        }
        return true;
    }
}
