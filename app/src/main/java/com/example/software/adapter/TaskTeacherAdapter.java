package com.example.software.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.software.activity.R;
import com.example.software.entity.Task;
import com.example.software.utils.Utils;

import java.util.List;

/**
 * Created by jafeth on 3/31/17.
 */

public class TaskTeacherAdapter extends BaseAdapter {

    private List<Task> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public TaskTeacherAdapter(Context aContext, List<Task> listData) {
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
            convertView = layoutInflater.inflate(R.layout.teacher_module_task_list_item, null);
            holder = new ViewHolder();
            holder.taskImageView = (ImageView) convertView.findViewById(R.id.imageView_task);
            holder.taskTareaView = (TextView) convertView.findViewById(R.id.textView_task_tarea);
            holder.taskFechaEntregaView = (TextView) convertView.findViewById(R.id.textView_fecha_entrega);
            holder.itemView = (TextView) convertView.findViewById(R.id.textView_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Task task = this.listData.get(position);
        holder.taskTareaView.setText(task.getTarea());
        holder.taskFechaEntregaView.setText(task.getFechaEntregaDate());
        holder.itemView.setText("nota: " + task.getNota());

        int imageId = Utils.getResourceIdByName(context, task.getImage(), "drawable");
        holder.taskImageView.setImageResource(imageId);

        return convertView;
    }

    static class ViewHolder {
        ImageView taskImageView;
        TextView taskTareaView;
        TextView taskFechaEntregaView;
        TextView itemView;
    }

}
