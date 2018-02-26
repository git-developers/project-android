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
import com.example.software.entity.Courses;
import com.example.software.utils.Utils;

import java.util.List;

/**
 * Created by jafeth on 3/31/17.
 */

public class CoursesAdapter extends BaseAdapter {

    private List<Courses> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public CoursesAdapter(Context aContext, List<Courses> listData) {
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
            convertView = layoutInflater.inflate(R.layout.teacher_list_item_courses, null);
            holder = new ViewHolder();
            holder.courseImageView = (ImageView) convertView.findViewById(R.id.imageView_course);
            holder.courseNameView = (TextView) convertView.findViewById(R.id.textView_courseName);
            holder.itemView = (TextView) convertView.findViewById(R.id.textView_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Courses course = this.listData.get(position);
        holder.courseNameView.setText(course.getName());
        holder.itemView.setText("item: " + course.getId());

        int imageId = Utils.getResourceIdByName(context, course.getImage(), "drawable");
        holder.courseImageView.setImageResource(imageId);

        return convertView;
    }

    static class ViewHolder {
        ImageView courseImageView;
        TextView courseNameView;
        TextView itemView;
    }

}
