package com.lt.day0531zlxs;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.lt.day0531zlxs.fragment.ClassFragment;
import com.lt.day0531zlxs.fragment.HomeFragment;
import com.lt.day0531zlxs.fragment.MyFragment;
import com.lt.day0531zlxs.fragment.VipFragment;
import com.lt.day0531zlxs.fragment.ZiFragment;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {


    private NoScrollViewPager ViewPagers;
    private TabLayout tablayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
    }

    private void initView() {
        ViewPagers =findViewById(R.id.content);

        tablayout =  findViewById(R.id.tablayout);
        final ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new ClassFragment());
        fragments.add(new VipFragment());
        fragments.add(new ZiFragment());
        fragments.add(new MyFragment());
        ViewPagers.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
        tablayout.setupWithViewPager(ViewPagers);
        tablayout.getTabAt(0).setCustomView(getView("主页",R.drawable.aa));
        tablayout.getTabAt(1).setCustomView(getView("课程",R.drawable.bb));
        tablayout.getTabAt(2).setCustomView(getView("VIP",R.drawable.vippp));
        tablayout.getTabAt(3).setCustomView(getView("资料",R.drawable.zl));
        tablayout.getTabAt(4).setCustomView(getView("我的",R.drawable.wode));
    }
    public View getView(String title, int image){
        View root = LayoutInflater.from(this).inflate(R.layout.tab_item,null);
        ImageView iv_img = root.findViewById(R.id.iv_img);
        TextView tv_title = root.findViewById(R.id.tv_title);
        iv_img.setImageResource(image);
        tv_title.setText(title);
        return root;
    }


}