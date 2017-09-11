package adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import main_fragment.CartFragment;
import main_fragment.ContactFragment;
import main_fragment.HomeFragment;
import main_fragment.SearchFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {
    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 1:
                return new CartFragment();
            case 2:
                return new SearchFragment();
            case 3:
                return new ContactFragment();
            default:
                return new HomeFragment();
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
