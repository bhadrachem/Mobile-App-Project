package com.example.bhadraother.androidminiapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.bhadraother.myapplication.R;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.support.v4.app.ActivityCompat.startActivityForResult;

public class ArticleViewAdapter extends
        RecyclerView.Adapter<ArticleViewAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView Title;
        public TextView Authordate;
        //public TextView bucketDate;

        public ViewHolder(View itemView) {
            super(itemView);

            Title = (TextView) itemView.findViewById();
            dateTextView = (TextView) itemView.findViewById(R.id.item_date);
            checkBox = (CheckBox) itemView.findViewById(R.id.taskCheck);

        }
    }

    private List<BucketItem> Items;
    private Context mContext;

    public BucketListAdapter(Context context, List<BucketItem> items) {
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

        View itemView = inflater.inflate(R.layout.content_bucket_list, parent, false);

        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ArticleViewAdapter.ViewHolder viewHolder, final int position) {
        // Get the data model based on position
        final BucketItem item = Items.get(position);

        // Set item views based on your views and data model
        TextView textView = viewHolder.nameTextView;
        TextView othertextView = viewHolder.dateTextView;
        textView.setText(item.getName());
        othertextView.setText(item.getDate().toString());
        if (!item.isComplete()) {
            textView.setClickable(false);
            textView.setActivated(false);
            textView.setEnabled(false);
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(item.getDate());

                Intent intent = new Intent(getContext(), EditItemActivity.class);
                intent.putExtra("name", item.getName());
                intent.putExtra("desc", item.getDesc());
                intent.putExtra("latitude", item.getLatitude());
                intent.putExtra("longitude", item.getLongitude());
                intent.putExtra("day", calendar.get(Calendar.DAY_OF_MONTH));
                intent.putExtra("month", calendar.get(Calendar.MONTH));
                intent.putExtra("year", calendar.get(Calendar.YEAR));
                intent.putExtra("position", position);
                ((Activity)mContext).startActivityForResult(intent, 2);
            }
        });

        // If checkBox is changed, changes boolean complete of the item
        CheckBox checkBox = viewHolder.checkBox;
        checkBox.setOnCheckedChangeListener(null);
        checkBox.setChecked(item.isComplete());
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                item.setComplete(isChecked);
            }
        });

    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return Items.size();
    }
}
