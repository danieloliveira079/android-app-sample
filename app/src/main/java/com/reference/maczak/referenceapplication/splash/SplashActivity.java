package com.reference.maczak.referenceapplication.splash;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.reference.maczak.referenceapplication.R;
import com.reference.maczak.referenceapplication.main.MainActivity;

public class SplashActivity extends FragmentActivity implements ViewPager.OnPageChangeListener{

    private ImageView titleBackground;
    private ImageView loremBackground;
    private ViewPager viewPager;
    private SplashFragmentPagerAdapter adapter;
    private CirclePageIndicator indicator;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        titleBackground = (ImageView) findViewById(R.id.splash_background_title);
        loremBackground = (ImageView) findViewById(R.id.splash_background_lorem);
        viewPager = (ViewPager) findViewById(R.id.splash_viewpager);
        indicator = (CirclePageIndicator) findViewById(R.id.splash_indicator);
        adapter = new SplashFragmentPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);
        indicator.setViewPager(viewPager);

        findViewById(R.id.splash_start_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                SplashActivity.this.finish();
            }
        });

    }

    public int getIntAlpha(float alpha) {
        int ret = (Math.round(alpha * 255));
        return ret;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if(position==0) {
            titleBackground.setImageAlpha(getIntAlpha(1 - positionOffset));
            loremBackground.setImageAlpha(getIntAlpha(positionOffset));
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
