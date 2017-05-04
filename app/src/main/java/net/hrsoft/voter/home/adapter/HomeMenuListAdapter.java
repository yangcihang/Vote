package net.hrsoft.voter.home.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import net.hrsoft.voter.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author YangCihang.
 * @since 2017/4/12 0012.
 */

public class HomeMenuListAdapter extends BaseAdapter {
    private List<String> mList = new ArrayList<>();

    public void addItem(String item) {
        mList.add(item);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_home, parent, false);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.homeItemTxt.setText(mList.get(position));
        return convertView;
    }

    private class Holder {
        private TextView homeItemTxt;

        Holder(View convertView) {
            homeItemTxt = (TextView) convertView.findViewById(R.id.txt_item_home_list);
        }
    }
}
