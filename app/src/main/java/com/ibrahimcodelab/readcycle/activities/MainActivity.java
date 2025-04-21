package com.ibrahimcodelab.readcycle.activities;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.ibrahimcodelab.readcycle.R;
import com.ibrahimcodelab.readcycle.fragments.HomeFragment;
import com.ibrahimcodelab.readcycle.fragments.ProfileFragment;

import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private SmoothBottomBar bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewPager);
        bottomNav = findViewById(R.id.bottom_nav_view);

        viewPager.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return position == 0 ? new HomeFragment() : new ProfileFragment();
            }

            @Override
            public int getItemCount() {
                return 2;
            }
        });

        bottomNav.setOnItemSelectedListener((OnItemSelectedListener) i -> {
            viewPager.setCurrentItem(i, false);
            return false;
        });

        viewPager.setUserInputEnabled(false);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                bottomNav.setItemActiveIndex(position);
            }
        });
    }
}