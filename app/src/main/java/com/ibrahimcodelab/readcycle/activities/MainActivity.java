package com.ibrahimcodelab.readcycle.activities;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.ibrahimcodelab.readcycle.R;
import com.ibrahimcodelab.readcycle.fragments.HomeFragment;
import com.ibrahimcodelab.readcycle.fragments.ProfileFragment;
import com.ibrahimcodelab.readcycle.fragments.SwapRequestsFragment;

import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private SmoothBottomBar bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewPager);
        bottomNav = findViewById(R.id.bottom_nav_view);

        viewPager.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return switch (position) {
                    case 1 -> new SwapRequestsFragment();
                    case 2 -> new ProfileFragment();
                    default -> new HomeFragment();
                };
            }

            @Override
            public int getItemCount() {
                return 3;
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