package com.crazy4web.myapplication.ui.cardDetail2;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PageAdapter extends FragmentStatePagerAdapter {

int behavior;
    public PageAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        this.behavior = behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position) {

            case 0:
                ServicesTab servicesTab = new ServicesTab();
                return servicesTab;

            case 1:
                AboutTab aboutTab = new AboutTab();
                return aboutTab;

            case 2:
                ReviewsTab reviewsTab= new ReviewsTab();
                return reviewsTab;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return behavior;
    }
}
