package com.promact.recyclerviewheadersectiondemo;

/**
 * Created by grishma on 17-07-2017.
 */
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
public class HeaderViewHolder extends RecyclerView.ViewHolder{
    public TextView headerTitle;
    public HeaderViewHolder(View itemView) {
        super(itemView);
        headerTitle = (TextView)itemView.findViewById(R.id.header_id);
    }
}
