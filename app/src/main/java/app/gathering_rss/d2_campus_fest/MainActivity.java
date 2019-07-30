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

    private ArrayList<Rss> feedList;
    private FeedAdapter feedAdapter;
    private InputMethodManager inputMethodManager;


    private int user_size;
    private int user_cnt;
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

            if(user_cnt==user_size){
                //sort feedList by pubDate
                Collections.sort(feedList,sortByPubDate);
                Collections.reverse(feedList);
            }

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

        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        user_size = Constant.USER_CODES.length;
        user_cnt = 0;

        Log.d("user_size",new Integer(user_size).toString());

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
                if (text.trim().equals("")) callRss();
            }
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
        feedList = new ArrayList<>();
        feedAdapter = new FeedAdapter(this,getSupportFragmentManager(), feedList);
        recyclerView.setAdapter(feedAdapter);
        for (String userCode: Constant.USER_CODES) {
            user_cnt++;
            Feeder feeder = new Feeder(userCode);
            Call<Rss> rssCall = feeder.callRss();
            rssCall.enqueue(rssCallback);
        }
    }

    private void search(String keyword) {
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
    }

    private final static Comparator<Rss> sortByPubDate = new Comparator<Rss>() {
        @Override
        public int compare(Rss rss1, Rss rss2) {
            return Collator.getInstance().compare(rss1.getLargestDate(),rss2.getLargestDate());
        }
    };
}
