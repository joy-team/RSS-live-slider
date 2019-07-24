package app.gathering_rss.d2_campus_fest;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ContentsAdapter extends FragmentPagerAdapter {
    private Context context;
    private List<Article> contents;
    private ArrayList<ContentsFragment> fragments = new ArrayList<>();

    public ContentsAdapter(Context context, FragmentManager fm, Rss feed) {
        super(fm);
        this.context = context;
        this.contents = feed.getArticles();
    }

    @Override
    public Fragment getItem(int position) {
        //create & add fragment for each article(content)
        ContentsFragment contentsFragment = new ContentsFragment(context, contents.get(position));
        Article cur_content = contents.get(position);

        Bundle content_bundle = new Bundle();
        content_bundle.putString("DATE",cur_content.getPubDate());
        content_bundle.putString("DESCRIPTION",cur_content.getTitle());
        if(cur_content.getImgUrls().size()>0){
            Log.d("get_rss",cur_content.getImgUrls().get(0));
            content_bundle.putString("RESOURCE",cur_content.getImgUrls().get(0));
        }

        return contentsFragment.newInstance(content_bundle);
    }

    @Override
    public int getCount() {
        return contents.size();
    }

}
