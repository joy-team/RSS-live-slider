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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);

/*
        viewPager.setClipToPadding(false);
        int dp_val = 10;
        float d = getResources().getDisplayMetrics().density;
        int margin = (int) (dp_val*d);
        viewPager.setPadding(margin, 0, margin, 0);
        viewPager.setPageMargin(margin/2);
*/
        ArrayList<CardviewAdapter> items = new ArrayList<>();

        CardviewAdapter cardviewAdapter = new CardviewAdapter(getSupportFragmentManager());
        items.add(cardviewAdapter);

        FeedAdapter feedAdapter = new FeedAdapter(this,getSupportFragmentManager(), items);
        recyclerView.setAdapter(feedAdapter);
    }
}
