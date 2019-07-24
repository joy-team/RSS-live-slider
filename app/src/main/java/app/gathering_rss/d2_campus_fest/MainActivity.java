package app.gathering_rss.d2_campus_fest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.imageView)
    ImageView tmpImgView;

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
