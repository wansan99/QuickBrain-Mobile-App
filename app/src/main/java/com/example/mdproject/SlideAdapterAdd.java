package com.example.mdproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class SlideAdapterAdd extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;

    public SlideAdapterAdd(Context context) {
        this.context = context;
    }

    public int[] slideImage = {
        R.drawable.add1_1,
        R.drawable.add1_2,
        R.drawable.add2_1,
        R.drawable.add2_2,
        R.drawable.add2_3,
        R.drawable.add2_4,
        R.drawable.add2_5,
        R.drawable.add2_6
    };

    public String[] slideDescription = {
            "Bob has 2 apples, he buys another 2 apples. Now he has total 4 apples.",
            "Bob has 2 apples, he buys another 2 apples. Now he has total 4 apples.",
            "Example 2",
            "To solve this equation, we can add each individual column from right to left.",
            "The first right column: is 9 + 2",
            "Which gives us 11, but instead we take the right number of 11, which is 1",
            "The additional 1, we carried on top of 2nd column",
            "2nd column we had 1 plus the carried number 1, which gives us 2"
    };

    @Override
    public int getCount() {
        return slideDescription.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_content, container, false);

        ImageView slideImageView = view.findViewById(R.id.slideImage);
        TextView slideTextViewDescription = view.findViewById(R.id.slideDescription);

        slideImageView.setImageResource(slideImage[position]);
        slideTextViewDescription.setText(slideDescription[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}
