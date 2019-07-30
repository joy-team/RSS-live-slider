package app.gathering_rss.d2_campus_fest;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;
import java.util.List;
public class ContentsAdapter extends FragmentStatePagerAdapter {
    private Context context;
    private List<Article> contents;
    private int index;
    private Rss feed;

    public ContentsAdapter(Context context, FragmentManager fm, Rss feed, int index) {
        super(fm);
        this.context = context;
        this.feed = feed;
        this.contents = feed.getArticles();
        this.index = index;
    }

    @Override
    public Fragment getItem(int position) {
        //create & add fragment for each article(content)
        Log.d("search_func","new item");
        ContentsFragment contentsFragment = new ContentsFragment();
        Article cur_content = contents.get(position);

        Bundle content_bundle = new Bundle();
        content_bundle.putString("RSS",feed.toString());
        content_bundle.putString("DATE",cur_content.getFormattedPubDate());
        content_bundle.putString("DESCRIPTION",cur_content.getTitle());
        content_bundle.putStringArrayList("RESOURCE_IMG",cur_content.getImgUrls());
        content_bundle.putStringArrayList("RESOURCE_VID",cur_content.getVidUrls());

        return contentsFragment.newInstance(content_bundle);
    }

    @Override
    public int getCount() {
        return contents.size();
    }

    @Override
    public int getItemPosition(@NonNull Object item) {
        return POSITION_NONE;
    }
}
