package app.gathering_rss.d2_campus_fest;

import android.content.Context;
import android.graphics.drawable.Drawable;
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
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class ContentsFragment extends Fragment {
    private Context context;

    private Article content;
    private String contentDate = "";
    private String contentPlace = "";
    private String contentRes = "";
    private String contentDes = "";

    private TabLayout tabLayout;
    private TextView view_contentDate;
    private TextView view_contentDes;
    private TextView view_contentPlace;
    private ImageView view_contentRes;


    public ContentsFragment() {
    }

    public ContentsFragment(Context context, Article content){
        this.context = context;
        this.content = content;
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
            contentDate = getArguments().getString("DATE");
            contentDes = getArguments().getString("DESCRIPTION");
            try {
                contentRes = getArguments().getString("RESOURCE");
                Log.d("get_rss_suc",contentRes);
                Glide.with(getActivity().getApplicationContext()).load(contentRes).into(view_contentRes);
            }catch(Exception e){
                contentRes="";
                e.printStackTrace();
            }
        }else{
            Log.d("get_rss","no arg");
        }

        view_contentDate.setText(contentDate);
        view_contentDes.setText(contentDes);
        for (int i=0;i<2;i++){
            TabLayout.Tab tab = tabLayout.newTab();

            //Drawable tabImage = getResources().getDrawable(R.drawable.conversation);
            //tab.setIcon(tabImage);
            tabLayout.addTab(tab);
        }

        return view;
    }
}
