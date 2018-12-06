package thanh.ha.ui.adapters;

/**
 * Created by HaVan on 5/23/2017.
 */


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> dataSet;

    public ViewPagerAdapter(FragmentManager manager, List<Fragment> dataSet) {
        super(manager);
        this.dataSet = dataSet;
    }


    @Override
    public Fragment getItem(int position) {
        return dataSet.get(position);
    }

    @Override
    public int getCount() {
        return dataSet.size();
    }


}