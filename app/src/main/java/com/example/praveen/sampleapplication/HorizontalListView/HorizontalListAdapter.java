package com.example.praveen.sampleapplication.HorizontalListView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.praveen.sampleapplication.R;

import java.util.List;

/**
 * Created by praveen on 9/27/2015.
 */
public class HorizontalListAdapter extends BaseAdapter {

    private Context mContext;
    private List catList;
    LayoutInflater layoutInflater;

    public HorizontalListAdapter(Context context, List list) {
        Log.v(HorizontalListAdapter.class.getName(),"constructor");
        mContext = context;
        catList = list;
        layoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return catList.size();
    }

    @Override
    public Object getItem(int position) {
        return catList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.horizontallistview, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.cat.setText(catList.get(position).toString());
        viewHolder.dot.setImageResource(R.drawable.dot21);


        return  convertView;
    }

    private class ViewHolder {
        TextView cat;
        ImageView dot;

        public  ViewHolder(View view) {
            cat = (TextView)view.findViewById(R.id.type);
            dot = (ImageView)view.findViewById(R.id.type_image);
        }
    }
}
