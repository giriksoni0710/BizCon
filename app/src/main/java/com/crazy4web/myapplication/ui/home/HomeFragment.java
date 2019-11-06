package com.crazy4web.myapplication.ui.home;

import android.content.Intent;
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
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.crazy4web.myapplication.R;
import com.crazy4web.myapplication.ui.cardDetail.DetailActivityFragment;
import com.crazy4web.myapplication.ui.categoryview.category_page;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class HomeFragment extends Fragment {

    ImageView categoryImage;
    private HomeViewModel homeViewModel;

    CardView cardView;
    Intent i;
    Intent intent;

    private int[] images = new int[]{
            R.drawable.categorypage1,R.drawable.categorypage2, R.drawable.categorypage1, R.drawable.categorypage2};


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        cardView = root.findViewById(R.id.business_card);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            i = new Intent(getContext(), DetailActivityFragment.class);

            startActivity(i);

            }
        });
        categoryImage = root.findViewById(R.id.categoryImage);
        categoryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                intent = new Intent(getContext(), category_page.class);

                startActivity(intent);

            }
        });

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