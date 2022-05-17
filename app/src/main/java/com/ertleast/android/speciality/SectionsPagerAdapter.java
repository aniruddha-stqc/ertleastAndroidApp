package com.ertleast.android.speciality;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.ertleast.android.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{
            R.string.tab_text_01,
            R.string.tab_text_02,
            R.string.tab_text_03,
            R.string.tab_text_04,
            R.string.tab_text_05,
            R.string.tab_text_06,
            R.string.tab_text_07,
            R.string.tab_text_08,
            R.string.tab_text_09,
            R.string.tab_text_10,
            R.string.tab_text_11,
            R.string.tab_text_12,
            R.string.tab_text_13,
            R.string.tab_text_14,
            R.string.tab_text_15,
            R.string.tab_text_16};
    private final Context mContext;
    private int speciality_item_count;
    private String[][] specialityArray;

    public SectionsPagerAdapter(Context context, FragmentManager fm, int speciality_item_count, String[][] specialityArray) {
        super(fm);
        mContext = context;
        this.speciality_item_count = speciality_item_count;
        this.specialityArray = specialityArray;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        //return PlaceholderFragment.newInstance(position + 1);
        return PlaceholderFragment.newInstance(position, specialityArray);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        //return mContext.getResources().getString(TAB_TITLES[position]);
        return specialityArray[position][1];
    }

    @Override
    public int getCount() {
        // Show 16 total pages.
        return speciality_item_count;
    }
}