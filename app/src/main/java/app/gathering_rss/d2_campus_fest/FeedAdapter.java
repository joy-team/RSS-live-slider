package app.gathering_rss.d2_campus_fest;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.HashMap;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {
    Context context;
    private ArrayList<Rss> feedList;
    private FragmentManager fragmentManager;

    private String userId;
    private String userImg;

    HashMap<Integer, Integer> viewpagerState = new HashMap<>();

    public FeedAdapter(Context context,FragmentManager fragmentManager, ArrayList<Rss> feedList) {
        this.context = context;
        this.feedList = feedList;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feed, parent, false);

        return new ViewHolder(view);
    }

    //정보 및 이벤트 처리 구현
    @Override
    public void onBindViewHolder(@NonNull FeedAdapter.ViewHolder holder, int position) {
        Rss userFeed = feedList.get(position);
        ContentsAdapter contentsAdapter = new ContentsAdapter(fragmentManager, userFeed);

        holder.viewPager.setAdapter(contentsAdapter);
        holder.viewPager.setId(position+1);

        if(viewpagerState.containsKey(position)){
            holder.viewPager.setCurrentItem(viewpagerState.get(position));
        }

        //set user's info
        userId = userFeed.getTitle();
        userImg = userFeed.getImgUrl();

        holder.view_userId.setText(userId);
        Glide.with(context).load(userImg).into(holder.view_userImg);
    }

    @Override
    public int getItemCount() {
        return feedList.size();
    }

    @Override
    public void onViewRecycled(@NonNull ViewHolder holder) {
        viewpagerState.put(holder.getAdapterPosition(),holder.viewPager.getCurrentItem());
        super.onViewRecycled(holder);
    }

    //Layout 불러오기
    public static class ViewHolder extends RecyclerView.ViewHolder{
        // TODO: 2019-07-17 bindview 사용
        public ViewPager viewPager;
        public TextView view_userId;
        public ImageView view_userImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            viewPager = itemView.findViewById(R.id.viewPager);
            view_userId = itemView.findViewById(R.id.feed_user_id);
            view_userImg = itemView.findViewById(R.id.feed_user_img);

            //set margin
            viewPager.setClipToPadding(false);
            /*
            int dp_val = 10;
            float d = getResources().getDisplayMetrics().density;
            int margin = (int) (dp_val*d);
            */
            int margin = 30;
            viewPager.setPadding(margin, 0, margin, 0);
            viewPager.setPageMargin(margin/2);
        }
    }
}
