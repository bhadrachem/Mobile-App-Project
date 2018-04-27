package com.example.bhadraother.myapplication;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

import static android.support.v4.app.ActivityCompat.startActivityForResult;

public class ArticleViewAdapter extends
        RecyclerView.Adapter<ArticleViewAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView Title;
        public TextView AuthorDate;
        public ViewHolder(View itemView) {
            super(itemView);

            Title = (TextView) itemView.findViewById(R.id.article_title);
            AuthorDate = (TextView) itemView.findViewById(R.id.article_author);
        }
    }

    private List<Article> Items;
    private Context mContext;

    public ArticleViewAdapter(Context context, List<Article> items) {
        Items = items;
        mContext = context;
    }

    private Context getContext() {
        return mContext;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public ArticleViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View itemView = inflater.inflate(R.layout.item_row, parent, false);

        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ArticleViewAdapter.ViewHolder viewHolder, final int position) {
        // Get the data model based on position
        final Article item = Items.get(position);

        // Set item views based on your views and data model
        TextView textView = viewHolder.Title;
        TextView othertextView = viewHolder.AuthorDate;
        textView.setText(item.getTitle());
        othertextView.setText(item.getAuthor() + " - " + item.getDate());
    }

    @Override
    public int getItemCount(){
        return Items.size();
    }
}
