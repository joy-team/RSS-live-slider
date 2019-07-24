package app.gathering_rss.d2_campus_fest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import java.util.ArrayList;

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

            // 인스타 아이디 가져오기
            // rss.getTitle()

            // 프로필 사진 불러오기
            // loadImage(tmpImgView, rss.getImgUrl());

            // 게시글 사진 불러오기
            // try {
            //     loadImage(tmpImgView, rss.getArticles().get(1).getImgUrls().get(0));
            // } catch (Exception e) {
            //     Log.d("Callback", "" + e);
            // }
        }

        @Override
        public void onFailure(Call<Rss> call, Throwable t) {
            Log.d("Callback", "" + t);
        }
    };

    private void loadImage(ImageView iv, String imgUrl) {
        Glide.with(getApplicationContext())
                .load(imgUrl)
                .centerCrop()
                .into(iv);
    }
}
