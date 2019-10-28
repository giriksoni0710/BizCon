package com.crazy4web.myapplication.ui.cardDetail;

import android.os.Bundle;
import android.text.Html;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class SwipeAdapter extends FragmentStatePagerAdapter {

    public SwipeAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        //now take specific fragment from any page and return it
        Fragment pageFragment = new FragmentPage();
        Bundle bundle = new Bundle();
        bundle.putInt("pageNumber", position+1);
        pageFragment.setArguments(bundle);

        return pageFragment;
    }

    @Override
    public int getCount() {
        return 3; //HOW MANY PAGES WE WANT
    }

}

