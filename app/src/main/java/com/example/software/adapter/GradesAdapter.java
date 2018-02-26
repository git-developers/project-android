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
import com.example.software.entity.Grades;
import com.example.software.utils.Utils;

import java.util.List;

/**
 * Created by jafeth on 3/31/17.
 */

public class GradesAdapter extends BaseAdapter {

    private List<Grades> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public GradesAdapter(Context aContext, List<Grades> listData) {
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
            convertView = layoutInflater.inflate(R.layout.father_list_item_grades, null);
            holder = new ViewHolder();
            holder.GradesImageView = (ImageView) convertView.findViewById(R.id.imageView_grades);
            holder.gradeMonthlyView = (TextView) convertView.findViewById(R.id.textView_gradeMonthly);
            holder.gradeBimonthlyView = (TextView) convertView.findViewById(R.id.textView_gradeBiMonthly);
            holder.gradeTeacherView = (TextView) convertView.findViewById(R.id.textView_gradeTeacher);
            holder.bimesterView = (TextView) convertView.findViewById(R.id.textView_bimester);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Grades grades = this.listData.get(position);
        holder.gradeMonthlyView.setText("Nota mensual: " + grades.getGradeMonthly());
        holder.gradeBimonthlyView.setText("Nota Bi mensual: " + grades.getGradeBimonthly());
        holder.gradeTeacherView.setText("Nota del profesor: " + grades.getGradeTeacher());
        holder.bimesterView.setText("Bimester: " + grades.getBimester());

        int imageId = Utils.getResourceIdByName(context, grades.getImage(), "drawable");
        holder.GradesImageView.setImageResource(imageId);

        return convertView;
    }

    static class ViewHolder {
        ImageView GradesImageView;
        TextView gradeMonthlyView;
        TextView gradeBimonthlyView;
        TextView gradeTeacherView;
        TextView bimesterView;
    }

}
