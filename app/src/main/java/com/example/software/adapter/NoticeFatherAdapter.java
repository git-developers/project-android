package com.example.software.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.software.activity.R;
import com.example.software.entity.Notice;
import com.example.software.utils.Utils;

import java.util.List;

/**
 * Created by jafeth on 3/31/17.
 */

public class NoticeFatherAdapter extends BaseAdapter {

    private List<Notice> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public NoticeFatherAdapter(Context aContext, List<Notice> listData) {
        this.context = aContext;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.father_list_item_notice, null);
            holder = new ViewHolder();
            holder.noticeImageView = (ImageView) convertView.findViewById(R.id.imageView_notice);
            holder.noticeMessageView = (TextView) convertView.findViewById(R.id.textView_noticeMessage);
            holder.noticeCreatedAtView = (TextView) convertView.findViewById(R.id.textView_createdAt);
            holder.itemView = (TextView) convertView.findViewById(R.id.textView_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Notice notice = this.listData.get(position);
        holder.noticeMessageView.setText(notice.getMessage());
        holder.noticeCreatedAtView.setText(notice.getCreatedAt());
        holder.itemView.setText("tipo: " + notice.getMessageType());

        int imageId = Utils.getResourceIdByName(context, notice.getImage(), "drawable");
        holder.noticeImageView.setImageResource(imageId);

        return convertView;
    }

    static class ViewHolder {
        ImageView noticeImageView;
        TextView noticeMessageView;
        TextView noticeCreatedAtView;
        TextView itemView;
    }

}
