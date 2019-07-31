package app.gathering_rss.d2_campus_fest;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class FragmentManager {
    static HashMap<String, ContentsFragment> activeFragment = new HashMap<>();
    static String playing_feed = null;
    static ContentsFragment playing_fragment = null;
    static boolean hasFocus = true;

    static void updateFocus_ver(String str_feed) {
        Log.d("Scroll", str_feed);
        //scroll -> focus changed

        playing_feed = str_feed;

        //start new focused feed
        if(activeFragment.get(str_feed)!=null){
            Log.d("Scroll", "start");
            activeFragment.get(str_feed).startPlaying();
        }
    }

    static void updateFocus_hor(String str_feed, ContentsFragment fragment){
        //slide viewpager
        try {
            if (str_feed.equals(playing_feed)) {
                Log.d("now playing", "viewpager moved");
                //viewpager (in focused page) changed

                //start new focused feed
                fragment.startPlaying();

                activeFragment.put(str_feed, fragment);
                playing_feed = str_feed;
            } else if (!str_feed.equals(playing_feed)) {
                //viewpager (in non-focused page) changed
                activeFragment.put(str_feed, fragment);
            }
        }catch(Exception e){

        }
    }
}
