package com.sanoj.devildeveloper.srilankavehicleinfo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.sanoj.devildeveloper.srilankavehicleinfo.fragment.Info1Fragment;
import com.sanoj.devildeveloper.srilankavehicleinfo.fragment.Info2Fragment;
import com.sanoj.devildeveloper.srilankavehicleinfo.fragment.Info3Fragment;
import com.merhold.extensiblepageindicator.ExtensiblePageIndicator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class InfoActivity extends AppCompatActivity {


    public static ViewPager vpPager;
    MyPagerAdapter adapterViewPager;

    public static TextView btnNext;
    private TextView btnSkip;
    int selectPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        ButterKnife.bind(this);
        btnNext = findViewById(R.id.btn_next);
        btnSkip =  findViewById(R.id.btn_skip);
        vpPager = findViewById(R.id.vpPager);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);
        ExtensiblePageIndicator extensiblePageIndicator = (ExtensiblePageIndicator) findViewById(R.id.flexibleIndicator);
        extensiblePageIndicator.initViewPager(vpPager);
        vpPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                selectPage = position;

                if (position == 0 || position == 1) {
                    btnSkip.setVisibility(View.VISIBLE);
                   btnNext.setText("Next");
                } else if (position == 2) {
                   btnSkip.setVisibility(View.GONE);
                   btnNext.setText("Finish");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectPage == 0) {
                    vpPager.setCurrentItem(1);
                } else if (selectPage == 1) {
                    vpPager.setCurrentItem(2);
                } else if (selectPage == 2) {
                    startActivity(new Intent(InfoActivity.this, MainActivity.class));
                    finish();
                }
            }
        });
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InfoActivity.this, MainActivity.class));
                finish();
            }
        });
    }
    public class MyPagerAdapter extends FragmentPagerAdapter {
        private int anInt = 3;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public int getCount() {
            return anInt;
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return Info1Fragment.newInstance();
                case 1:
                    return Info2Fragment.newInstance();
                case 2:
                    return Info3Fragment.newInstance();
                default:
                    return null;
            }

        }

        @Override
        public CharSequence getPageTitle(int position) {
            Log.e("page", "" + position);
            return "Page " + position;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            return fragment;
        }

    }
}
