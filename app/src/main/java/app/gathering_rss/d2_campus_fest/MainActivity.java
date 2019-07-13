package app.gathering_rss.d2_campus_fest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        ArrayList<String> tempList = new ArrayList<>();
        tempList.add("1");
        tempList.add("2");

        //recyclerview 임시 연결
        FeedAdapter feedAdapter = new FeedAdapter(MainActivity.this, tempList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(feedAdapter);

        //viewpager 임시 연결
        CardviewAdapter cardviewAdapter = new CardviewAdapter(getSupportFragmentManager());
        viewPager.setAdapter(cardviewAdapter);

        viewPager.setClipToPadding(false);
        int dp_val = 10;
        float d = getResources().getDisplayMetrics().density;
        int margin = (int) (dp_val*d);
        viewPager.setPadding(margin, 0, margin, 0);
        viewPager.setPageMargin(margin/2);

        for(int i=0;i<4; i++){
            CardviewFragment cardviewFragment = new CardviewFragment();
            cardviewAdapter.addItem(cardviewFragment);
        }

        cardviewAdapter.notifyDataSetChanged();
    }
}
