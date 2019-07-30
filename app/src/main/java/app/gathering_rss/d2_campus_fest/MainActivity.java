package app.gathering_rss.d2_campus_fest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.lang.reflect.Array;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
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

    @BindView(R.id.noResultLayout)
    ConstraintLayout noResultLayout;

    @BindView(R.id.loadingLayout)
    ConstraintLayout loadingLayout;

    @BindView(R.id.shimmer_view_container)
    ShimmerFrameLayout shimmerLogo;

    private ArrayList<Rss> feedList;
    private FeedAdapter feedAdapter;
    private InputMethodManager inputMethodManager;

    public static int lastVisibleItemPos = -1;

    private Callback<Rss> rssCallback = new Callback<Rss>() {
        @Override
        public void onResponse(Call<Rss> call, Response<Rss> response) {
            if (!response.isSuccessful()) {
                Log.d("Callback", "Response fail");
                return;
            }
            Rss rss = response.body();
            feedList.add(rss);
            if(feedList.size()==Constant.USER_CODES.length){
                //sort feedList by pubDate
                Collections.sort(feedList,sortByPubDate);
                Collections.reverse(feedList);
                feedAdapter.notifyDataSetChanged();

                shimmerLogo.stopShimmer();
                loadingLayout.setVisibility(View.GONE);
            }
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

        feedList = new ArrayList<>();
        feedAdapter =
                new FeedAdapter(MainActivity.this, getSupportFragmentManager(), feedList);
        recyclerView.setAdapter(feedAdapter);

        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstVisibleItemPos =
                        ((LinearLayoutManager) recyclerView.getLayoutManager())
                                .findFirstCompletelyVisibleItemPosition();
                if(firstVisibleItemPos != -1 && firstVisibleItemPos != lastVisibleItemPos){
                    FragmentManager.updateFocus_ver(feedList.get(firstVisibleItemPos).toString());
                    lastVisibleItemPos = firstVisibleItemPos;
                }
            }
        });

        searchBtn.setOnClickListener((View view) -> {
            search(editSearch.getText().toString());
        });

        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = editSearch.getText().toString().toLowerCase();
                if (text.trim().equals("")) {
                    feedAdapter = new
                            FeedAdapter(MainActivity.this, getSupportFragmentManager(), feedList);
                    recyclerView.setAdapter(feedAdapter);
                }
            }
        });

        callRss();
    }

    @Override
    public void onRefresh() {
        if(noResultLayout.getVisibility() == View.VISIBLE)
            noResultLayout.setVisibility(View.GONE);

        editSearch.setText("");
        FragmentManager.playing_fragment.stopPlaying();
        feedList.clear();
        callRss();
        swipeRefreshLayout.setRefreshing(false);
    }

    private void callRss() {
        loadingLayout.setVisibility(View.VISIBLE);
        shimmerLogo.startShimmer();
        for (String userCode: Constant.USER_CODES) {
            Feeder feeder = new Feeder(userCode);
            Call<Rss> rssCall = feeder.callRss();
            rssCall.enqueue(rssCallback);
        }
    }

    private void search(String keyword) {
        if(noResultLayout.getVisibility() == View.VISIBLE)
            noResultLayout.setVisibility(View.GONE);

        ArrayList<Rss> tmpFeedList = (ArrayList) feedList.clone();
        tmpFeedList.clear();
        for (Rss rss: feedList) {
            Rss searched_rss = new Rss();
            searched_rss.setTitle(rss.getTitle());
            searched_rss.setImgUrl(rss.getImgUrl());
            searched_rss.setLink(rss.getLink());
            searched_rss.setArticles(new ArrayList<Article>());
            for (Article article: rss.getArticles()) {
                if (article.getTitle().contains(keyword)) {
                    searched_rss.getArticles().add(article);
                }
            }
            if (searched_rss.getArticles().size() != 0) {
                tmpFeedList.add(searched_rss);
            }
        }

        Collections.sort(tmpFeedList,sortByPubDate);
        Collections.reverse(tmpFeedList);

        feedAdapter = new FeedAdapter(this,getSupportFragmentManager(), tmpFeedList);
        recyclerView.setAdapter(feedAdapter);
        inputMethodManager.hideSoftInputFromWindow(editSearch.getWindowToken(), 0);

        if(tmpFeedList.size() == 0){
            noResultLayout.setVisibility(View.VISIBLE);
        }
    }

    private final static Comparator<Rss> sortByPubDate = new Comparator<Rss>() {
        @Override
        public int compare(Rss rss1, Rss rss2) {
            return Collator.getInstance().compare(rss1.getLargestDate(),rss2.getLargestDate());
        }
    };
}
