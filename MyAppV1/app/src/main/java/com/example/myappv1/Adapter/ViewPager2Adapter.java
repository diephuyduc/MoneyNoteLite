package com.example.myappv1.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.myappv1.fragment.HomeFragment;
import com.example.myappv1.fragment.LoanFragment;

public class ViewPager2Adapter extends FragmentStateAdapter {

Fragment[] fragmentList = {new LoanFragment(), new HomeFragment()};

    public ViewPager2Adapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }


    public Fragment getCurrentFragment(int position){
        return fragmentList[position];
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0 :
                return fragmentList[0];

            case 1:
                return  fragmentList[1];

            default:
                return fragmentList[0];
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
