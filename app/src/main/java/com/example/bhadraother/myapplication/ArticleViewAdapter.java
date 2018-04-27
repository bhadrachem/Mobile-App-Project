package com.example.bhadraother.myapplication;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import xyz.klinker.android.article.ArticleIntent;

public class ArticleViewAdapter extends
        RecyclerView.Adapter<ArticleViewAdapter.ViewHolder>{

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView Title;
        public ImageView Preview;
        public TextView AuthorDate;
        public Button Saved;
        public Button Share;
        public ViewHolder(View itemView) {
            super(itemView);
            Title = (TextView) itemView.findViewById(R.id.article_title);
            AuthorDate = (TextView) itemView.findViewById(R.id.article_authordate);
            Saved = (Button) itemView.findViewById(R.id.save);
            Preview = (ImageView) itemView.findViewById(R.id.preview);
            Share = (Button) itemView.findViewById(R.id.share);
        }
    }
    private ViewHolder viewHolder;
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
        viewHolder.Title.setText(item.getTitle());
        viewHolder.AuthorDate.setText(item.getAuthor()+" - "+item.getDate());
        viewHolder.Preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openArticle(v, position);
            }
        });
        viewHolder.Saved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        viewHolder.Share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareArticle(v, position);
            }
        });
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openArticle(v, position);
            }
        });
    }
    public void shareArticle(View view, int index){
        String url = Items.get(index).getURL();
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Sharing URL");
        intent.putExtra(Intent.EXTRA_TEXT, url);
        getContext().startActivity(Intent.createChooser(intent, "Share URL"));
    }
    public void openArticle(View view, int index){
        ArticleIntent intent = new ArticleIntent.Builder(getContext(), "7dd79f0f8c798222747d06c5dd39e308")
                .setToolbarColor(0)
                .build();
        String url = Items.get(index).getURL();
        intent.launchUrl(getContext(), Uri.parse(url));
    }
    public void saveArticle(View view, int index){
        String url = Items.get(index).getURL();
    //    savedArticles.add(articles.get(LoadInt("index")));
    }

    @Override
    public int getItemCount(){
        return Items.size();
    }
}
