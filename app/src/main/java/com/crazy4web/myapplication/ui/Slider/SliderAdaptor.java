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

            R.drawable.intro_image,
            R.drawable.slider2,
            R.drawable.slider1
    };

    public String[] slideHeadings = {

            "Never miss an opportunity", "Get your work done easily", "Let us help you"

    };

    public String[] slideDesc = {

            "Start a business easily with your skillset",
            "Find services nearby and get work done easily",
            "Connect with businesses using our chat feature"


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
