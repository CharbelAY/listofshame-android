package com.charbelay.listofshame;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.charbelay.listofshame.View.ListFragmentView;
import com.charbelay.listofshame.View.MapFragmentView;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new MapFragmentView()).commit();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment  = null;

                    switch (menuItem.getItemId()){
                        case R.id.nav_home:
                            selectedFragment = new MapFragmentView();
                            break;
                        case R.id.nav_profile:
                            selectedFragment = new ProfileFragment();
                            break;
                        case R.id.nav_list:
                            selectedFragment = new ListFragmentView();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace
                            (R.id.fragment_container,selectedFragment).commit();
                    return true;
                }
            };

    @Override
    public void onBackPressed() {
        if (true) {
            moveTaskToBack(true);
        } else {
            super.onBackPressed();
        }
    }


}
