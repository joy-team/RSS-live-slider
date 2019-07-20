package app.gathering_rss.d2_campus_fest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

        ArrayList<ContentsAdapter> items = new ArrayList<>();

        ContentsAdapter contentsAdapter = new ContentsAdapter(getSupportFragmentManager());
        items.add(contentsAdapter);
        ContentsAdapter contentsAdapter1 = new ContentsAdapter(getSupportFragmentManager());
        items.add(contentsAdapter1);
        ContentsAdapter contentsAdapter2 = new ContentsAdapter(getSupportFragmentManager());
        items.add(contentsAdapter2);

        FeedAdapter feedAdapter = new FeedAdapter(this,getSupportFragmentManager(), items);
        recyclerView.setAdapter(feedAdapter);
    }
}
