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

public class SlideAdapterMul extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;

    public SlideAdapterMul(Context context) {
        this.context = context;
    }

    public int[] slideImage = {
        R.drawable.mul1_1,
        R.drawable.mul1_2,
        R.drawable.mul2_1,
        R.drawable.mul2_2,
        R.drawable.mul2_3,
        R.drawable.mul2_4,
        R.drawable.mul2_5,
        R.drawable.mul2_6,
        R.drawable.mul2_7
    };

    public String[] slideDescription = {
            "There are 3 candies in a pack. John bought 2 packs. He got total 6 candies. ",
            "There are 3 candies in a pack. John bought 2 packs. He got total 6 candies. ",
            "Example 2",
            "To solve this equation, we can first solve the right first number of second row's number",
            "And then perform an addition with the result of second number of second row's number",
            "Firstly, anything multiply by 0 will always be 0",
            "Then, 11 multiply by 1 we will get back 11",
            "Now we perform addition on both numbers",
            "At the end we will get 110 as the answer",
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
