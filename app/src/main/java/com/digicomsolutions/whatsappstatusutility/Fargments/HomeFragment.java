package com.digicomsolutions.whatsappstatusutility.Fargments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.digicomsolutions.whatsappstatusutility.R;
import com.digicomsolutions.whatsappstatusutility.SampleFragment;
import com.tompee.funtablayout.BubbleTabAdapter;
import com.tompee.funtablayout.FunTabLayout;


public class HomeFragment extends Fragment implements BubbleTabAdapter.IconFetcher  {


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_home, container, false);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        FunTabLayout tabLayout = (FunTabLayout)view. findViewById(R.id.tablayout);
        BubbleTabAdapter.Builder builder = new BubbleTabAdapter.Builder(getContext()).
                setViewPager(viewPager).
                setTabPadding(24, 24, 24, 24).
                setTabIndicatorColor(Color.parseColor("#ffa022")).
                setTabBackgroundResId(R.drawable.ripple).
                setIconFetcher(this).
                setIconDimension(50).
                setTabTextAppearance(R.style.BubbleTabText);
        tabLayout.setUpWithAdapter(builder.build());
        return view;
    }

    private void setupViewPager(ViewPager viewPager) {
        viewPager.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return SampleFragment.newInstance(position);
            }

            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                String title = "";
                switch (position) {
                    case 0:
                        title = "Videos";
                        break;
                    case 1:
                        title = "Images";
                        break;

                }
                return title;
            }
        });
    }

    @Override
    public int getIcon(int position) {
        return getIconInternal(position);
    }

    @Override
    public int getSelectedIcon(int position) {
        return getIconInternal(position);
    }

    private int getIconInternal(int position) {
        int resource = R.mipmap.ic_launcher;
        switch (position) {
            case 0:
                resource = R.drawable.video;
                break;
            case 1:
                resource = R.drawable.photo;
                break;

        }
        return resource;
    }
}