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

public class SlideAdapterSub extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;

    public SlideAdapterSub(Context context) {
        this.context = context;
    }

    public int[] slideImage = {
        R.drawable.sub1_1,
        R.drawable.sub1_2,
        R.drawable.sub2_1,
        R.drawable.sub2_2,
        R.drawable.sub2_3,
        R.drawable.sub2_4,
        R.drawable.sub2_5,
        R.drawable.sub2_6,
        R.drawable.sub2_7
    };

    public String[] slideDescription = {
            "Alice has 4 apples, she ate 1 apple. Now she has 3 apples left.",
            "Alice has 4 apples, she ate 1 apple. Now she has 3 apples left.",
            "Example 2",
            "To solve this equation, we can add each individual column from right to left. Since 1 is not enough to minus 9...",
            "We can borrow a 1 from column 2",
            "Column 2 is a tens, so the 1 will be 10 instead",
            "Now add the borrowed 10 with 1",
            "We get 11, which we can continue to minus 9 to get 2",
            "At column 2, the number 2 had been borrowed to become 1, so we can bring down the 1, which gives us the answer 12",
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
