package com.example.myappv1.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.myappv1.Adapter.ViewPager2Adapter;
import com.example.myappv1.DepthPageTransformer;
import com.example.myappv1.MainActivity;
import com.example.myappv1.R;
import com.example.myappv1.fragment.LoanFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    ViewPager2 viewPager2;
    ViewPager2Adapter viewPager2Adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
        bottomNavigationView.setItemIconTintList(null);
        viewPager2Adapter = new ViewPager2Adapter(this);
        viewPager2.setAdapter(viewPager2Adapter);
        viewPager2.setPageTransformer(new DepthPageTransformer());
        slideViewPager2();
        bottomAction();

    }

    private void bottomAction() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.act_loan:
                        viewPager2.setCurrentItem(0);
                        LoanFragment loanFragment = (LoanFragment) viewPager2Adapter.getCurrentFragment(0);

                        break;

                    case R.id.act_home:
                        viewPager2.setCurrentItem(1);
                        break;
                }
                return true;
            }
        });
    }



    public void init() {
        if(MainActivity.isLogin!=true){
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(intent);
            finish();

        }
        bottomNavigationView = findViewById(R.id.bottom_nav);
        viewPager2 = findViewById(R.id.view_pager_2);
    }


    public void slideViewPager2() {
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.act_loan).setChecked(true);
                        LoanFragment loanFragment = (LoanFragment) viewPager2Adapter.getCurrentFragment(0);
                       // loanFragment.loadData();
                        break;

                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.act_home).setChecked(true);
                        break;
                }
            }
        });
    }
}