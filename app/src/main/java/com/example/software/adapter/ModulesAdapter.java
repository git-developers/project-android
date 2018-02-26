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
import com.example.software.entity.Modules;
import com.example.software.utils.Utils;

import java.util.List;

/**
 * Created by jafeth on 3/31/17.
 */

public class ModulesAdapter extends BaseAdapter {

    private List<Modules> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public ModulesAdapter(Context aContext, List<Modules> listData) {
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
            convertView = layoutInflater.inflate(R.layout.teacher_grid_item_modules, null);
            holder = new ViewHolder();
            holder.moduleImageView = (ImageView) convertView.findViewById(R.id.imageView_module);
            holder.moduleNameView = (TextView) convertView.findViewById(R.id.textView_moduleName);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Modules module = this.listData.get(position);
        holder.moduleNameView.setText(module.getName());

        int imageId = Utils.getResourceIdByName(context, module.getImage(), "drawable");
        holder.moduleImageView.setImageResource(imageId);

        return convertView;
    }

    static class ViewHolder {
        ImageView moduleImageView;
        TextView moduleNameView;
    }

}
