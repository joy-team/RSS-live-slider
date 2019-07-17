package app.gathering_rss.d2_campus_fest;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.HashMap;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {
    Context context;
    private ArrayList<CardviewAdapter> feedList;
    private FragmentManager fragmentManager;

    HashMap<Integer, Integer> viewpagerState = new HashMap<>();

    public FeedAdapter(Context context,FragmentManager fragmentManager, ArrayList<CardviewAdapter> feedList) {
        this.context = context;
        this.feedList = feedList;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feed, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedAdapter.ViewHolder holder, int position) {
        CardviewAdapter cardviewAdapter = feedList.get(position);

        for(int i=0;i<4; i++){
            CardviewFragment cardviewFragment = new CardviewFragment();
            cardviewAdapter.addItem(cardviewFragment);
        }

        cardviewAdapter.notifyDataSetChanged();

        holder.viewPager.setAdapter(cardviewAdapter);
        holder.viewPager.setId(position+1);

        if(viewpagerState.containsKey(position)){
            holder.viewPager.setCurrentItem(viewpagerState.get(position));
        }
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

    public static class ViewHolder extends RecyclerView.ViewHolder{
        // TODO: 2019-07-17 bindview 사용 
        // TODO: 2019-07-17 사용자 정보 입력할 요소들 추가 
        public ViewPager viewPager;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //set margin
            viewPager = itemView.findViewById(R.id.viewPager);
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
