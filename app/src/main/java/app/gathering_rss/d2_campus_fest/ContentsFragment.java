package app.gathering_rss.d2_campus_fest;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class ContentsFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private int mParam1;
    private Context context;

    public ContentsFragment() {
    }

    public ContentsFragment(Context context){
        this.context = context;
    }

    public static ContentsFragment newInstance(int param1) {
        ContentsFragment fragment = new ContentsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_contents,container,false);
        // TODO: 2019-07-17 cardview 안의 요소들 설정
        TabLayout tabLayout = view.findViewById(R.id.tabLayout);
        for (int i=0;i<2;i++){
            TabLayout.Tab tab = tabLayout.newTab();

            Drawable tabImage = getResources().getDrawable(R.drawable.conversation);
            tab.setIcon(tabImage);
            tabLayout.addTab(tab);
        }

        return view;
    }
}
