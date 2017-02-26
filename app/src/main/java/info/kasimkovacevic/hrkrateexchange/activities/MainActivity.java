package info.kasimkovacevic.hrkrateexchange.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import info.kasimkovacevic.hrkrateexchange.R;
import info.kasimkovacevic.hrkrateexchange.adapters.PagerAdapter;
import info.kasimkovacevic.hrkrateexchange.views.CustomViewPager;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, ViewPager.PageTransformer {

    private static final String BOTTOM_NAVIGATION_PAGE_KEY = "info.kasimkovacevic.hrkrateexchange.activities.MainActivity.BOTTOM_NAVIGATION_PAGE_KEY";

    private Unbinder mUnbinder;

    @BindView(R.id.vp_rate_exchange)
    protected CustomViewPager mViewPager;

    @BindView(R.id.bnv_bottom_navigation)
    protected BottomNavigationView mBottomNavigationMenuView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUnbinder = ButterKnife.bind(this);
        mViewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        //Custom viewpager used with option for enabling/disabling paging
        mViewPager.setPagingEnabled(false);
        mBottomNavigationMenuView.setOnNavigationItemSelectedListener(this);
        mViewPager.setPageTransformer(false, this);

        if (savedInstanceState != null && savedInstanceState.getInt(BOTTOM_NAVIGATION_PAGE_KEY, -1) != -1) {
            ((BottomNavigationMenuView) mBottomNavigationMenuView.getChildAt(0)).getChildAt(savedInstanceState.getInt(BOTTOM_NAVIGATION_PAGE_KEY)).performClick();
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(BOTTOM_NAVIGATION_PAGE_KEY, mViewPager.getCurrentItem());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_calculator:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.action_exchange_rate:
                mViewPager.setCurrentItem(1);
                hideKeyboard();
                break;
            case R.id.action_graph:
                mViewPager.setCurrentItem(2);
                hideKeyboard();
                break;
        }
        return true;
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * Transform suggested on bottom navigation material design page
     * https://material.io/guidelines/components/bottom-navigation.html#bottom-navigation-behavior
     * http://stackoverflow.com/a/32633721
     */
    @Override
    public void transformPage(View view, float position) {
        if (position <= -1.0F || position >= 1.0F) {
            view.setTranslationX(view.getWidth() * position);
            view.setAlpha(0.0F);
        } else if (position == 0.0F) {
            view.setTranslationX(view.getWidth() * position);
            view.setAlpha(1.0F);
        } else {
            // position is between -1.0F & 0.0F OR 0.0F & 1.0F
            view.setTranslationX(view.getWidth() * -position);
            view.setAlpha(1.0F - Math.abs(position));
        }
    }

}
