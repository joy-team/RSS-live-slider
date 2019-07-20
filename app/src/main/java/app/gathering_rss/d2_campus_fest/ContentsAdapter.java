package app.gathering_rss.d2_campus_fest;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class ContentsAdapter extends FragmentPagerAdapter {
    private ArrayList<ContentsFragment> items = new ArrayList<>();

    public ContentsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return items.get(position).newInstance(position);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    void addItem(ContentsFragment fragment){
        items.add(fragment);
    }
}
