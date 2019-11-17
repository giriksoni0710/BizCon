package com.crazy4web.myapplication.ui.cardDetail;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class SwipeAdapter extends FragmentStatePagerAdapter {

    int behavior;

    public SwipeAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        this.behavior = behavior;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                ServicesTab servicesTab = new ServicesTab();
                Log.d("from swipe adapter: Position: ", ""+position);
                return servicesTab;

            case 1:
                AboutTab aboutTab = new AboutTab();
                Log.d("from swipe adapter: Position: ", ""+position);
                return aboutTab;

            case 2:
                ReviewsTab reviewsTab = new ReviewsTab();
                Log.d("from swipe adapter: Position: ", ""+position);
                return reviewsTab;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return behavior;
//        return 3; //HOW MANY PAGES WE WANT
    }

}




//now take specific fragment from any page and return it
//        Fragment pageFragment = new FragmentPage();
//        Bundle bundle = new Bundle();
//        bundle.putInt("pageNumber", position+1);
//        pageFragment.setArguments(bundle);
//
//        return pageFragment;
