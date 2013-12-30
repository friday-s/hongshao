
package com.xue.replay.adapter;

import java.util.List;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.xue.replay.R;
import com.xue.replay.entity.Event;

public class ListAdapter extends BaseAdapter {

    private Context context;
    private List<Event> events;

    public ListAdapter(Context context, List<Event> events) {
        this.context = context;
        this.events = events;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return events.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return events.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        ViewHolder mViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.plan_item, null);
            mViewHolder = new ViewHolder();
            mViewHolder.id = (TextView) convertView.findViewById(R.id.plan_id);
            mViewHolder.name = (TextView) convertView.findViewById(R.id.plan_name);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        mViewHolder.id.setText(String.valueOf(position + 1));
        mViewHolder.name.setText(events.get(position).eventName);

        return convertView;
    }

    private class ViewHolder {
        TextView id;
        TextView name;
    }

}
