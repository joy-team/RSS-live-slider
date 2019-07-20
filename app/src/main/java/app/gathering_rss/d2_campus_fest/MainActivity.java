package app.gathering_rss.d2_campus_fest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Feeder feeder;
    private Rss rss;
    private Call<Rss> rssCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        feeder = new Feeder(Constant.HYUNJIN);

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
            /*
            //    Test code
            Log.d("Callback", "" + rss.getTitle());
            Log.d("Callback", "" + rss.getLink());
            Log.d("Callback", "" + rss.getImgUrl());
            try {
                for (Article article: rss.getArticles()) {
                    Log.d("Callback", "" + article.getLink());
                    Log.d("Callback", "" + article.getDescription());
                    Log.d("Callback", "" + article.getPubDate());
                }
            } catch (Exception e) {
                Log.d("Callback", "" + e);
            }
            */
        }

        @Override
        public void onFailure(Call<Rss> call, Throwable t) {
            Log.d("Callback", "" + t);
        }
    };
}
