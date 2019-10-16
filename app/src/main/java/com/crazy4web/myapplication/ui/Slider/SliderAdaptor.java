package com.crazy4web.myapplication.ui.Slider;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.crazy4web.myapplication.R;

public class SliderAdaptor extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;


    public SliderAdaptor (Context context)
    {
    this.context = context;
    }

    public int[] slideImages = {

            R.drawable.ic_home_black_24dp,
            R.drawable.ic_dashboard_black_24dp,
            R.drawable.ic_notifications_black_24dp
    };

    public String[] slideHeadings = {

            "Slide1", "slide2", "slide3"

    };

    public String[] slideDesc = {

            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book",
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book",
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book"


    };

    @Override
    public int getCount() {
        return slideHeadings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (LinearLayout) object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slidelayout, container, false);

        ImageView imageView = (ImageView) view.findViewById(R.id.imageView2);
        TextView slideHeading = (TextView) view.findViewById(R.id.textView);

        TextView slideDescription = (TextView) view.findViewById(R.id.textView2);

        imageView.setImageResource(slideImages[position]);
        slideHeading.setText(slideHeadings[position]);

        slideDescription.setText(slideDesc[position]);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((LinearLayout)object);
    }
}
