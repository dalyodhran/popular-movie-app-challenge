package com.example.android.popularmoviesapppart1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class DetailActivity extends AppCompatActivity {

    private String PREF;
    public static final String PREFERENCES = "catagorie";

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        sharedPreferences = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.detail_fragment, new DetailFragment()).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        PREF = sharedPreferences.getString("prefKey", "popular?");

        if(PREF != null){
            if(PREF.contains("popular?")){
                menu.findItem(R.id.action_popular).setChecked(true);
                menu.findItem(R.id.action_top_rated).setChecked(false);
            }else {
                menu.findItem(R.id.action_popular).setChecked(false);
                menu.findItem(R.id.action_top_rated).setChecked(true);
            }
        }


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItemThatWasSelected = item.getItemId();
        if(menuItemThatWasSelected == R.id.action_popular){
            PREF = "popular?";
            editor.putString("prefKey", PREF);
            editor.commit();

            startActivity(new Intent(this, MainActivity.class));
            finish();
        }else {
            PREF = "top_rated?";
            editor.putString("prefKey", PREF);
            editor.commit();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
        return true;
    }
}
