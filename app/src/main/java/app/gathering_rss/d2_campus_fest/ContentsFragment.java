package app.gathering_rss.d2_campus_fest;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ClippingMediaSource;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static android.view.View.VISIBLE;

public class ContentsFragment extends Fragment {

    private Activity mActivity;

    private String contentDate = "";
    private String contentPlace = "";
    private ArrayList<String> contentImg = new ArrayList<>();
    private ArrayList<String> contentVid = new ArrayList<>();
    private String contentDes = "";

    private TabLayout tabLayout;
    private View tabView;
    private ImageView tabImg;

    private TextView view_contentDate;
    private TextView view_contentDes;
    private TextView view_contentPlace;
    private ImageView view_contentRes;
    private ProgressBar progressBar;

    private String str_feed;

    private PlayerView playerView;
    private SimpleExoPlayer player = null;
    private Boolean playReady = true;
    private int curWindow = 0;
    private long playBackPos = 0;

    private int playVidIdx = 0;
    private int playImgIdx = 0;
    private Timer timer;


    public ContentsFragment() {
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getView() != null) {
            if(isVisibleToUser){
                FragmentManager.updateFocus_hor(str_feed, this);
            }
        }
    }

    public ContentsFragment newInstance(Bundle content_bundle) {
        ContentsFragment fragment = new ContentsFragment();
        fragment.setArguments(content_bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Activity) {
            mActivity = (Activity) context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_contents,container,false);

        tabLayout = view.findViewById(R.id.tabLayout);
        view_contentDate = view.findViewById(R.id.content_date);
        view_contentDes = view.findViewById(R.id.content_des);
        view_contentPlace = view.findViewById(R.id.content_place);
        view_contentRes = view.findViewById(R.id.content_res);
        playerView = view.findViewById(R.id.video_view);
        progressBar = view.findViewById(R.id.progressBar);

        if(getArguments()!=null){
            str_feed = getArguments().getString("RSS");
            contentDate = getArguments().getString("DATE");
            contentDes = getArguments().getString("DESCRIPTION");
            contentImg = getArguments().getStringArrayList("RESOURCE_IMG");
            contentVid = getArguments().getStringArrayList("RESOURCE_VID");

            if(FragmentManager.activeFragment.get(str_feed)==null) {
                FragmentManager.activeFragment.put(str_feed,this);
                Log.d("now playing", "put fragment");
            }

            if(contentVid.size()>0){
                playVidIdx = 0;
                try {
                    Glide.with(mActivity.getApplicationContext())
                            .load(contentImg.get(playVidIdx))
                            .listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    progressBar.setVisibility(View.GONE);
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    progressBar.setVisibility(View.GONE);
                                    return false;
                                }
                            })
                            .into(view_contentRes);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }else{
                playImgIdx = 0;
                try {
                    Glide.with(mActivity.getApplicationContext())
                            .load(contentImg.get(playImgIdx))
                            .listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    progressBar.setVisibility(View.GONE);
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    progressBar.setVisibility(View.GONE);
                                    return false;
                                }
                            })
                            .into(view_contentRes);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }


        }else{
            Log.d("get_rss","no arg");
        }

        view_contentDate.setText(contentDate);
        view_contentDes.setText(contentDes);

        //set bottom tab
        for(int i=0;i<contentVid.size();i++){
            TabLayout.Tab tab = tabLayout.newTab();

            tabView = getLayoutInflater().inflate(R.layout.contents_tab,null);
            tabImg = tabView.findViewById(R.id.tab_img);
            tabView.setMinimumHeight(50);
            tabView.setMinimumWidth(50);
            try{
                Glide.with(mActivity.getApplicationContext())
                        .asBitmap()
                        .load(contentVid.get(i))
                        .thumbnail(0.1f)
                        .into(tabImg);
                tab.setCustomView(tabView);
            }catch (Exception e){

            }
            tabLayout.addTab(tab);
        }

        for(int i=0;i<contentImg.size();i++){
            TabLayout.Tab tab = tabLayout.newTab();

            tabView = getLayoutInflater().inflate(R.layout.contents_tab,null);
            tabImg = tabView.findViewById(R.id.tab_img);
            tabView.setMinimumHeight(50);
            tabView.setMinimumWidth(50);
            try{
                Glide.with(mActivity.getApplicationContext())
                        .load(contentImg.get(i))
                        .thumbnail(0.1f)
                        .into(tabImg);
                tab.setCustomView(tabView);
            }catch (Exception e){

            }
            tabLayout.addTab(tab);
        }

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int selected = tabLayout.getSelectedTabPosition();
                for(int i=0;i<contentImg.size();i++)
                    tabLayout.getTabAt(i).getCustomView().findViewById(R.id.tab_img).setBackgroundColor(0000000);
                tabLayout.getTabAt(selected).getCustomView().findViewById(R.id.tab_img).setBackgroundResource(R.drawable.tab_border);

                Log.d("tab_select", new Integer(selected).toString());
                if (selected < contentVid.size()) {
                    playVidIdx = selected;
                    try {
                        Glide.with(mActivity.getApplicationContext())
                                .load(contentImg.get(playVidIdx))
                                .into(view_contentRes);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                } else {
                    playImgIdx = selected - contentVid.size();
                    try {
                        Glide.with(mActivity.getApplicationContext())
                                .load(contentImg.get(playImgIdx))
                                .into(view_contentRes);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        Log.d("Scroll", "create");

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("Scroll", "pause");
        if (FragmentManager.playing_fragment == this) {
            Log.d("Scroll", "pause & stop");
            FragmentManager.playing_fragment.stopPlaying();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("Scroll", "resume: " + FragmentManager.playing_fragment);
        if (!FragmentManager.hasFocus) {
            Log.d("Scroll", "focus");
            FragmentManager.updateFocus_ver(str_feed);
            FragmentManager.hasFocus = true;
        } else if (FragmentManager.playing_fragment == this) {
            Log.d("Scroll", "resume & start");
            FragmentManager.playing_fragment.startPlaying();
        }
    }

    public void startPlaying(){
        if(FragmentManager.playing_fragment!=null && FragmentManager.playing_fragment!=this)
            //stop previous focused feed
            FragmentManager.playing_fragment.stopPlaying();

        if(FragmentManager.playing_fragment!=this){
            FragmentManager.playing_fragment = this;
            playVidIdx = 0;
            playImgIdx = 0;
            Log.d("now playing","feed : "+str_feed+" fragment : "+this.toString()+" / date : "+contentDate);
        }

        if (timer != null) timer.cancel();
        timer = new Timer();
        timer.scheduleAtFixedRate(new SliderTimer(), 0, 5000);
    }

    public void stopPlaying(){
        Log.d("now playing","stop previous playing");
        if (timer != null) timer.cancel();
        releasePlayer();
    }


    private void initPlayer(String videoUrl) {
        playerView.setVisibility(VISIBLE);
        playerView.setUseController(false);

        player = ExoPlayerFactory.newSimpleInstance(mActivity.getApplicationContext());
        player.setVolume(0f);

        playerView.setPlayer(player);
        player.setPlayWhenReady(playReady);
        player.seekTo(curWindow, playBackPos);

        Uri uri = Uri.parse(videoUrl);
        MediaSource mediaSource = buildMediaSource(uri);
        ClippingMediaSource clippingSource = new ClippingMediaSource(mediaSource, 5_000_000);
        player.prepare(clippingSource);
    }

    private void releasePlayer() {
        if (player != null) {
            playBackPos = player.getCurrentPosition();
            curWindow = player.getCurrentWindowIndex();
            playReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("Mozilla/5.0"))
                .createMediaSource(uri);
    }

    private class SliderTimer extends TimerTask {
        @Override
        public void run() {
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d("Scroll", contentDate);
                    Log.d("Scroll", "" + contentVid.size());
                    Log.d("Scroll", "" + playVidIdx);
                    Log.d("Scroll", "" + contentImg.size());
                    Log.d("Scroll", "" + playImgIdx);

                    if (playVidIdx == contentVid.size() && playImgIdx == contentImg.size()) {
                        RecyclerView rv = mActivity.findViewById(R.id.recyclerView);
                        FeedAdapter.ViewHolder vh =
                                (FeedAdapter.ViewHolder) rv.findViewHolderForAdapterPosition(MainActivity.lastVisibleItemPos);
                        try {
                            ViewPager vp = vh.viewPager;
                            int curPage = vp.getCurrentItem();
                            if (curPage < vp.getAdapter().getCount()-1) {
                                vp.setCurrentItem(curPage+1);
                            } else if (curPage == vp.getAdapter().getCount()-1) {
                                vp.setCurrentItem(0);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        playVidIdx = 0;
                        playImgIdx = 0;
                    }

                    Log.d("Scroll", contentDate);
                    Log.d("Scroll", "" + contentImg.size());
                    Log.d("Scroll", "" + playImgIdx);

                    if (playVidIdx < contentVid.size()) {
                        for(int i=0;i<contentImg.size();i++)
                            tabLayout.getTabAt(i).getCustomView().findViewById(R.id.tab_img).setBackgroundColor(0000000);
                        tabLayout.getTabAt(playVidIdx).getCustomView().findViewById(R.id.tab_img).setBackgroundResource(R.drawable.tab_border);

                        initPlayer(contentVid.get(playVidIdx));
                        playVidIdx += 1;
                    } else if (playImgIdx < contentImg.size()) {
                        for(int i=0;i<contentImg.size();i++)
                            tabLayout.getTabAt(i).getCustomView().findViewById(R.id.tab_img).setBackgroundColor(0000000);
                        tabLayout.getTabAt(playImgIdx).getCustomView().findViewById(R.id.tab_img).setBackgroundResource(R.drawable.tab_border);

                        try {
                            Glide.with(mActivity.getApplicationContext())
                                    .load(contentImg.get(playImgIdx))
                                    .into(view_contentRes);
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                        playImgIdx += 1;
                    }
                }
            });
        }
    }
}
