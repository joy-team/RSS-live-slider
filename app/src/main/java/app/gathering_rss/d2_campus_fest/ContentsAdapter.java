package app.gathering_rss.d2_campus_fest;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ContentsAdapter extends FragmentPagerAdapter {
    private Rss feed;
    private List<Article> contents;
    private ArrayList<ContentsFragment> fragments = new ArrayList<>();

    public ContentsAdapter(FragmentManager fm, Rss feed) {
        super(fm);
        this.feed = feed;
        this.contents = feed.getArticles();

        //create & add fragment for each article(content)
        for(int i=0;i<contents.size();i++){
            ContentsFragment contentsFragment = new ContentsFragment(contents.get(i));
            fragments.add(contentsFragment);
        }
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position).newInstance(position);
    }

    @Override
    public int getCount() {
        return contents.size();
    }

}
