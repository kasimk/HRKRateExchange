package info.kasimkovacevic.hrkrateexchange.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import info.kasimkovacevic.hrkrateexchange.fragments.ChangesGraphFragment;
import info.kasimkovacevic.hrkrateexchange.fragments.CurrencyCalculatorFragment;
import info.kasimkovacevic.hrkrateexchange.fragments.ExchangeRateFragment;

/**
 * @author Kasim Kovacevic
 */
public class PagerAdapter extends FragmentPagerAdapter {


    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new CurrencyCalculatorFragment();
            case 1:
                return new ExchangeRateFragment();
            case 2:
                return new ChangesGraphFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
