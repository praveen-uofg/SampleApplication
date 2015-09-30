package com.example.praveen.sampleapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.praveen.sampleapplication.HorizontalListView.HorizontalListAdapter;
import com.example.praveen.sampleapplication.HorizontalListView.HorizontalListView;
import com.example.praveen.sampleapplication.memoryutils.ImageLoader;

import java.util.List;

/**
 * Created by praveen on 9/26/2015.
 */
public class ListAdapter extends BaseAdapter {

    private Context mContext =   null;
    private List restaurantList;
    LayoutInflater layoutInflater;
    private ImageLoader imageLoader;

    HorizontalListAdapter horizontalListAdapter;
    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return restaurantList.size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public Object getItem(int position) {
        return restaurantList.get(position);
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView ==null) {
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listitem,parent,false);
            viewHolder = new ViewHolder(convertView);

            viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    ListViewItems listItem = (ListViewItems)viewHolder.checkBox.getTag();
                    listItem.setSelected(buttonView.isChecked());
                }
            });
            viewHolder.checkBox.setTag(restaurantList.get(position));

            convertView.setTag(viewHolder);
            convertView.setBackgroundResource(R.drawable.shape_drawable);

        } else {
            viewHolder = (ViewHolder)convertView.getTag();
            ((ViewHolder)convertView.getTag()).checkBox.setTag(restaurantList.get(position));
        }
        ListViewItems listViewItems = (ListViewItems)getItem(position);

        viewHolder.rest_name.setText(listViewItems.getRest_name());
        viewHolder.offers.setText(listViewItems.getOffers()+ " Offers");

        viewHolder.distance.setText(calculateDistance(listViewItems));
        viewHolder.neighbourHoodName.setText(listViewItems.getNeighbourHoodName());

        viewHolder.checkBox.setChecked(listViewItems.isSelected());
       // viewHolder.checkBox.setTag(restaurantList.get(position));

        imageLoader.DisplayImage(listViewItems.getImageResUrl(), viewHolder.logo);

        horizontalListAdapter = new HorizontalListAdapter(mContext,listViewItems.getCategories());
        HorizontalListView listView = (HorizontalListView)convertView.findViewById(R.id.hlvCustomList);
        listView.setAdapter(horizontalListAdapter);

        return convertView;
    }

    private String calculateDistance(ListViewItems listViewItems) {
        float dist;
        dist = listViewItems.getDistance();
        if (dist >= 1000) {
            dist = Math.round(listViewItems.getDistance()/1000);
            return String.valueOf(dist) + " Km" ;
        }
        return String.valueOf(Math.abs(dist)) + " m" ;
    }

    public ListAdapter(Context context, List list) {
        mContext = context;
        restaurantList = list;
        layoutInflater = LayoutInflater.from(mContext);
        imageLoader = new ImageLoader(mContext);

    }


    private static class ViewHolder {
        TextView rest_name,offers,distance,neighbourHoodName;
        ImageView logo;
        CheckBox checkBox;

        public  ViewHolder(View view) {
            rest_name = (TextView)view.findViewById(R.id.restaurant_name);
            offers = (TextView)view.findViewById(R.id.offers);
            neighbourHoodName = (TextView)view.findViewById(R.id.address_text);

            distance = (TextView)view.findViewById(R.id.distance);
            logo = (ImageView)view.findViewById(R.id.logo);
            checkBox = (CheckBox)view.findViewById(R.id.checkBox);
        }
    }
}
