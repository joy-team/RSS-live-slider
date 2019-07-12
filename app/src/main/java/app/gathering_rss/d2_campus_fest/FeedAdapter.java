package app.gathering_rss.d2_campus_fest;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.BreakIterator;
import java.util.ArrayList;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {
    Context context;
    ArrayList<String> feedList;

    public FeedAdapter(Context context, ArrayList<String> feedList) {
        this.context = context;
        this.feedList = feedList;
    }

    @NonNull
    @Override
    public FeedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_temp,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedAdapter.ViewHolder holder, int position) {
        String text = feedList.get(position);
        holder.textView.setText(text);
    }

    @Override
    public int getItemCount() {
        return this.feedList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.text1);
        }
    }
}
