package com.crazy4web.myapplication.ui.cardDetail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.crazy4web.myapplication.R;

import static com.facebook.FacebookSdk.getApplicationContext;

public class ReviewsTab extends Fragment {

    RecyclerView recyclerViewReviews;
    RecyclerAdapterReviews recyclerAdapterReviews;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.reviews_tab, container, false);
        return root;
    }
}
