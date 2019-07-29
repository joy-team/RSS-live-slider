package app.gathering_rss.d2_campus_fest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.main_edit_search)
    EditText editSearch;

    @BindView(R.id.main_search_btn)
    ImageButton searchBtn;

    @BindView(R.id.main_container)
    SwipeRefreshLayout swipeRefreshLayout;

    private ArrayList<Rss> feedList;
    private FeedAdapter feedAdapter;

    private int lastVisibleItemPos = -1;

    private Callback<Rss> rssCallback = new Callback<Rss>() {
        @Override
        public void onResponse(Call<Rss> call, Response<Rss> response) {
            if (!response.isSuccessful()) {
                Log.d("Callback", "Response fail");
                return;
            }
            Rss rss = response.body();
            feedList.add(rss);

            feedAdapter.notifyDataSetChanged();
        }

        @Override
        public void onFailure(Call<Rss> call, Throwable t) {
            Log.d("Callback", "" + t);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        swipeRefreshLayout.setOnRefreshListener(this);

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

                if(firstVisibleItemPos != lastVisibleItemPos && firstVisibleItemPos !=-1 ){
                    /// TODO: 2019-07-28 현재 스크롤 위치에서 focus 된 게시글 자동재생
                    Log.d("now playing", "scroll changed : "+new Integer(firstVisibleItemPos).toString());
                    FragmentManager.updateFocus_ver(feedList.get(firstVisibleItemPos).toString());
                    lastVisibleItemPos = firstVisibleItemPos;
                }
            }
        });

        searchBtn.setOnClickListener((View view) -> {
            search(editSearch.getText().toString());
        });

        callRss();
    }

    @Override
    public void onRefresh() {
        feedList.clear();
        callRss();
        swipeRefreshLayout.setRefreshing(false);
    }

    private void callRss() {
        for (String userCode: Constant.USER_CODES) {
            Feeder feeder = new Feeder(userCode);
            Call<Rss> rssCall = feeder.callRss();
            rssCall.enqueue(rssCallback);
        }
    }

    private void search(String keyword) {
        ArrayList<Rss> tmpFeedList = (ArrayList) feedList.clone();
        feedList.clear();
        recyclerView.removeAllViews();

        feedAdapter.notifyDataSetChanged();

        for (Rss rss: tmpFeedList) {
            Rss searched_rss = new Rss();
            Log.d("Search", rss.getTitle());
            searched_rss.setTitle(rss.getTitle());
            Log.d("Search", searched_rss.getTitle());
            searched_rss.setImgUrl(rss.getImgUrl());
            searched_rss.setLink(rss.getLink());
            searched_rss.setArticles(new ArrayList<Article>());
            for (Article article: rss.getArticles()) {
                Log.d("Search", article.getTitle());
                if (article.getTitle().contains(keyword)) {
                    searched_rss.getArticles().add(article);
                }
            }
            if (searched_rss.getArticles().size() != 0) {
                feedList.add(searched_rss);
            }
        }
        feedAdapter.notifyDataSetChanged();
        feedAdapter.contentsAdapter.notifyDataSetChanged();
    }
}
