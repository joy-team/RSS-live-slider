package app.gathering_rss.d2_campus_fest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;

import com.bumptech.glide.Glide;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private Feeder feeder;
    private Rss rss;
    private Call<Rss> rssCall;
    private ArrayList<Rss> feedList;
    private FeedAdapter feedAdapter;

    private int lastVisibleItemPos = 0;
    static ContentsFragment lastVisibleItem = null;
    static HashMap<String, ContentsFragment> activeFragment = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        feedList = new ArrayList<>();
        feedAdapter = new FeedAdapter(this,getSupportFragmentManager(), feedList);
        recyclerView.setAdapter(feedAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int firstVisibleItemPos = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
                Log.d("scroll",new Integer(firstVisibleItemPos).toString());

                if(firstVisibleItemPos != lastVisibleItemPos){
                    Log.d("active","scroll:"+new Integer(firstVisibleItemPos).toString()+"index:"+activeFragment.get(feedList.get(firstVisibleItemPos).toString()).toString());
                    lastVisibleItemPos = firstVisibleItemPos;
                }

            }
        });

        //get rss_feed
        feeder = new Feeder(Constant.HYUNJIN);
        rssCall = feeder.callRss();
        rssCall.enqueue(channelCallback);

        feeder = new Feeder(Constant.JENNY);
        rssCall = feeder.callRss();
        rssCall.enqueue(channelCallback);

        feeder = new Feeder(Constant.JISOO);
        rssCall = feeder.callRss();
        rssCall.enqueue(channelCallback);

        feeder = new Feeder(Constant.ROSE);
        rssCall = feeder.callRss();
        rssCall.enqueue(channelCallback);

        feeder = new Feeder(Constant.LISA);
        rssCall = feeder.callRss();
        rssCall.enqueue(channelCallback);
    }

    private Callback<Rss> channelCallback = new Callback<Rss>() {
        @Override
        public void onResponse(Call<Rss> call, Response<Rss> response) {
            if (!response.isSuccessful()) {
                Log.d("Callback", "Response fail");
                return;
            }
            rss = response.body();
            feedList.add(rss);

            feedAdapter.notifyDataSetChanged();
        }

        @Override
        public void onFailure(Call<Rss> call, Throwable t) {
            Log.d("Callback", "" + t);
        }
    };
}
