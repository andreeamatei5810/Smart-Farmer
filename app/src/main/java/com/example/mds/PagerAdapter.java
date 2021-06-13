package com.example.mds;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;

public class PagerAdapter extends FragmentPagerAdapter {

    private int noOfTabs;
    private String emailFarmer;
    private String emailUser;

    public PagerAdapter(FragmentManager fm, int noOfTabs, String emailFarmer,String emailUser){
        super(fm);
        this.noOfTabs = noOfTabs;
        this.emailFarmer = emailFarmer;
        this.emailUser = emailUser;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                Fragment fragment = new ProductsFragment();
                ((ProductsFragment) fragment).setEmailFarmer(emailFarmer);
                return fragment;
            case 1:
                Fragment fragment2 = new RatingsFragment();
                ((RatingsFragment) fragment2).setEmailFarmer(emailFarmer);
                ((RatingsFragment) fragment2).setEmailUser(emailUser);
                return fragment2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return noOfTabs;
    }
}
