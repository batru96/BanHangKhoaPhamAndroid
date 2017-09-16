package adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import main_fragment.TabCartFragment;
import main_fragment.TabContactFragment;
import main_fragment.TabHomeFragment;
import main_fragment.TabSearchFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {
    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 1:
                return new TabCartFragment();
            case 2:
                return new TabSearchFragment();
            case 3:
                return new TabContactFragment();
            default:
                return new TabHomeFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 1:
                return "Cart";
            case 2:
                return "Search";
            case 3:
                return "Contact";
            default:
                return "Home";
        }
    }
}
