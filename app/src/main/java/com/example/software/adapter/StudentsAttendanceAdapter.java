package com.example.software.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.software.activity.R;
import com.example.software.entity.Attendance;
import com.example.software.entity.User;
import com.example.software.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jafeth on 3/31/17.
 */

public class StudentsAttendanceAdapter extends BaseAdapter {

    private List<User> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public StudentsAttendanceAdapter(Context aContext, List<User> listData) {
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

    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.teacher_list_item_ttendance, null);
            holder = new ViewHolder();
            holder.studentNameView = (TextView) convertView.findViewById(R.id.textView_studentName);
            holder.itemView = (TextView) convertView.findViewById(R.id.textView_item);
            holder.etUsername = (EditText) convertView.findViewById(R.id.et_username);
            holder.rgStatus = (RadioGroup) convertView.findViewById(R.id.rgStatus);
            holder.rbAsistio = (RadioButton) convertView.findViewById(R.id.rbAsistio);
            holder.rbTardanza = (RadioButton) convertView.findViewById(R.id.rbTardanza);
            holder.rbFalta = (RadioButton) convertView.findViewById(R.id.rbFalta);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.rgStatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
//                Integer position = (Integer) radioGroup.getTag();
//                radioGroup.getCheckedRadioButtonId()
//                holder.rgStatus.clearCheck();
                Attendance attendance  = new Attendance();
                User user = listData.get(position);

                switch(checkedId)
                {
                    case R.id.rbAsistio:
                        holder.rbAsistio.setChecked(true);
                        attendance.setCurrent(Attendance.ASISTIO);
                        user.setAttendance(attendance);
                        break;
                    case R.id.rbTardanza:
                        holder.rbTardanza.setChecked(true);
                        attendance.setCurrent(Attendance.TARDANZA);
                        user.setAttendance(attendance);
                        break;
                    case R.id.rbFalta:
                        holder.rbFalta.setChecked(true);
                        attendance.setCurrent(Attendance.FALTA);
                        user.setAttendance(attendance);
                        break;
                    default:
                        attendance.setCurrent(Attendance.FALTA);
                        user.setAttendance(attendance);
                        break;
                }
            }
        });

        User user = this.listData.get(position);
        holder.studentNameView.setText(user.getName());
        holder.itemView.setText("user: " + user.getUsername());
        holder.etUsername.setText(user.getUsername());

        return convertView;
    }

    static class ViewHolder {
        TextView studentNameView;
        TextView itemView;
        RadioButton rbAsistio;
        RadioButton rbTardanza;
        RadioButton rbFalta;
        EditText etUsername;
        RadioGroup rgStatus;
    }

}
