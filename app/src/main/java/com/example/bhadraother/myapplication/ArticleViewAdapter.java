package com.example.bhadraother.myapplication;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

public class ArticleViewAdapter extends
        RecyclerView.Adapter<ArticleViewAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView Title;
        public TextView AuthorDate;
        public TextView Saved;
        public ViewHolder(View itemView) {
            super(itemView);
            Title = (TextView) itemView.findViewById(R.id.article_title);
            AuthorDate = (TextView) itemView.findViewById(R.id.article_authordate);
            Saved = (TextView) itemView.findViewById(R.id.save);
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
        othertextView.setText(item.getAuthor());
        if(item.isSaved()){
            viewHolder.Saved.setText("Saved");
        }
        else{
            viewHolder.Saved.setText("Save");
        }
/*
        viewHolder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                SaveInt("index",viewHolder.getAdapterPosition());
            }
        });
*/
    }

    @Override
    public int getItemCount(){
        return Items.size();
    }
    public void SaveInt(String key, int value){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }
    public int LoadInt(String key){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        int savedValue = sharedPreferences.getInt("key", 0);
        return savedValue;
    }
}
