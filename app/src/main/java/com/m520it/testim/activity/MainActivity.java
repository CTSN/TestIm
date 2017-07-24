package com.m520it.testim.activity;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.m520it.testim.R;
import com.m520it.testim.common.MyApplication;
import com.m520it.testim.widget.MainTab;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;

public class MainActivity extends AppCompatActivity {


    private String id;
    private ViewPager mVp;
    private MainTab mTab;
    private List<Fragment> mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();

        initFragment();

        initTab();

    }

    private void initView() {
        id = ((MyApplication) getApplication()).getId();
        mFragments = new ArrayList<>();
        setContentView(R.layout.activity_main);
        mVp = (ViewPager) findViewById(R.id.main_vp);
        mTab = (MainTab) findViewById(R.id.tab);
    }

    private void initFragment() {
        mFragments.add(enterFragment());
        mFragments.add(enterFragment());
        mFragments.add(enterFragment());

        mVp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                Fragment fragment = (Fragment) super.instantiateItem(container,
                        position);
                getSupportFragmentManager().beginTransaction().show(fragment).commit();
                return fragment;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                Fragment fragment = mFragments.get(position);
                getSupportFragmentManager().beginTransaction().hide(fragment).commit();
            }
        });

        mVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTab.selectItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initTab() {
        mTab.setTabClickListener(new MainTab.TabClickListener() {
            @Override
            public void onClick(View v) {
                int item = (int) v.getTag();
                mVp.setCurrentItem(item);
            }
        });
    }

    /**
     * 加载 会话列表 ConversationListFragment
     */
    private Fragment enterFragment() {

        ConversationListFragment fragment = new ConversationListFragment();

        Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话非聚合显示
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "true")//设置群组会话聚合显示
                .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")//设置讨论组会话非聚合显示
                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "false")//设置系统会话非聚合显示
                .build();

        fragment.setUri(uri);
        return fragment;
    }



}
