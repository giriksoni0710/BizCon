package com.crazy4web.myapplication.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.crazy4web.myapplication.R;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    private int[] images = new int[]{
            R.drawable.ic_notifications_black_24dp,R.drawable.ic_dashboard_black_24dp, R.drawable.ic_launcher_background, R.drawable.ic_launcher_foreground};



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);


        // search



        CarouselView carouselView = root.findViewById(R.id.carouselview);

        carouselView.setPageCount(images.length);

        carouselView.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {


                imageView.setImageResource(images[position]);


            }
        });
        carouselView.setImageClickListener(new ImageClickListener() {
            @Override
            public void onClick(int position) {

                Toast.makeText(getContext(), images[position],Toast.LENGTH_LONG).show();

            }
        });

        return root;










    }


}