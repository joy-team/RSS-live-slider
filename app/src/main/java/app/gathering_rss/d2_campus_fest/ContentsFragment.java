package app.gathering_rss.d2_campus_fest;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class ContentsFragment extends Fragment {
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

    private String feed;

    public boolean IS_VISIBLE = false;


    public ContentsFragment() {
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            Log.d("active","visible : "+this.toString());
            MainActivity.activeFragment.put(feed,this);
            IS_VISIBLE = true;

        }else{
            Log.d("get_focus","not visible : "+this.toString());
            IS_VISIBLE = false;
        }
    }

    public ContentsFragment newInstance(Bundle content_bundle) {
        ContentsFragment fragment = new ContentsFragment();
        fragment.setArguments(content_bundle);
        return fragment;
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

        if(getArguments()!=null){
            feed = getArguments().getString("RSS");
            contentDate = getArguments().getString("DATE");
            contentDes = getArguments().getString("DESCRIPTION");
            contentImg = getArguments().getStringArrayList("RESOURCE_IMG");
            contentVid = getArguments().getStringArrayList("RESOURCE_VID");
            try {
                Glide.with(getActivity().getApplicationContext()).load(contentImg.get(0)).into(view_contentRes);
            }catch(Exception e){
                e.printStackTrace();
            }
        }else{
            Log.d("get_rss","no arg");
        }

        view_contentDate.setText(contentDate);
        view_contentDes.setText(contentDes);

        //set bottom tab
        for(int i=0;i<contentImg.size();i++){
            TabLayout.Tab tab = tabLayout.newTab();

            tabView = getLayoutInflater().inflate(R.layout.contents_tab,null);
            tabImg = tabView.findViewById(R.id.tab_img);
            tabView.setMinimumHeight(50);
            tabView.setMinimumWidth(50);
            try{
                Glide.with(getActivity().getApplicationContext()).load(contentImg.get(i)).into(tabImg);
                tab.setCustomView(tabView);
            }catch (Exception e){

            }
            tabLayout.addTab(tab);
        }

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int selected = tabLayout.getSelectedTabPosition();
                Log.d("tab_select", new Integer(selected).toString());
                try {
                    Glide.with(getActivity().getApplicationContext()).load(contentImg.get(selected)).into(view_contentRes);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return view;
    }
}
