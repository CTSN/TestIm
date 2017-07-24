package com.m520it.testim.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.m520it.testim.R;

/**
 * Created by roy on 2017/7/24.
 */

public class MainTab extends LinearLayout {

    private TabClickListener mTabClickListener;

    public void setTabClickListener(TabClickListener listener) {
        this.mTabClickListener = listener;
    }

    public MainTab(Context context) {
        super(context);
        init(context);
    }

    public MainTab(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        String[] tabTitles = getResources().getStringArray(R.array.main_tab);

        TypedArray IconIDType = getResources().obtainTypedArray(R.array.main_tan_iv);
        Drawable nav_up;
        for (int i = 0; i < tabTitles.length; i++) {

            nav_up = getResources().getDrawable(IconIDType.getResourceId(i, 0));
            nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());

            TextView tv = new TextView(context);
            tv.setText(tabTitles[i]);
            tv.setGravity(Gravity.CENTER);
            tv.setClickable(true);
            tv.setTag(i);
            tv.setCompoundDrawablePadding(3);
            tv.setCompoundDrawables(null, nav_up, null, null);
            tv.setTextColor(getResources().getColor(R.color.text_color));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f);
            params.setMargins(0, 10, 0, 10);
            tv.setLayoutParams(params);

            if (i == 0) {
                tv.setTextColor(getResources().getColor(R.color.text_color_hover));
                tv.setEnabled(false);
            }

            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.setEnabled(false);
                    ((TextView) v).setTextColor(getResources().getColor(R.color.text_color_hover));
                    int tag = (int) v.getTag();
                    for (int i = 0; i < getChildCount(); i++) {
                        if (i != tag) {
                            ((TextView) getChildAt(i)).setTextColor(getResources().getColor(R.color.text_color));
                            getChildAt(i).setEnabled(true);
                        }
                    }
                    if (mTabClickListener != null){
                        mTabClickListener.onClick(v);
                    }
                }
            });

            addView(tv);
        }
        IconIDType.recycle();
    }

    public interface TabClickListener {
        void onClick(View v);
    }


    public void selectItem(int selected) {
        for (int i = 0; i < getChildCount(); i++) {
            ((TextView) getChildAt(i)).setTextColor((i == selected) ? getResources().getColor(R.color.text_color_hover) :
                    getResources().getColor(R.color.text_color));
            getChildAt(i).setEnabled(i==selected?false:true);
        }
    }
}
