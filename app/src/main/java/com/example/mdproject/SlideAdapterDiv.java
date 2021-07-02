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

public class SlideAdapterDiv extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;

    public SlideAdapterDiv(Context context) {
        this.context = context;
    }

    public int[] slideImage = {
        R.drawable.div1_1,
        R.drawable.div1_2,
        R.drawable.div2_1,
        R.drawable.div2_2,
        R.drawable.div2_3,
        R.drawable.div2_4,
        R.drawable.div2_5,
        R.drawable.div2_6,
        R.drawable.div2_7
    };

    public String[] slideDescription = {
            "Eve and Eva has 4 oranges, they share the oranges. Each of them get 2 oranges.",
            "Eve and Eva has 4 oranges, they share the oranges. Each of them get 2 oranges.",
            "Example 2",
            "To solve this equation, we can divide each column individually from left to right",
            "At the first column we had 4, where 4 divided by 4 gives 1",
            "We then draw a line at the bottom to perform subtraction",
            "4 minus 4 gives 0",
            "Then we carry the 8 down",
            "8 divide by 4 gives 2, and then subtraction at the bottom gives 0 remainder",
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
