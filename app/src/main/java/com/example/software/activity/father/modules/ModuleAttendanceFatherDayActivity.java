package com.example.software.activity.father.modules;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.software.activity.BaseActivity;
import com.example.software.activity.R;
import com.example.software.activity.implement.IBase;
import com.example.software.controller.AttendanceController;
import com.example.software.controller.CoursesController;
import com.example.software.controller.UserController;
import com.example.software.entity.Attendance;
import com.example.software.entity.Courses;
import com.example.software.entity.User;
import com.example.software.entity.WsResponse;
import com.example.software.utils.Const;
import com.example.software.utils.Utils;

import org.json.JSONObject;

import java.util.HashMap;

public class ModuleAttendanceFatherDayActivity extends BaseActivity implements IBase {

    private static final String TAG = "ModuleAttendanceFatherDayActivity";
    private TextView tvStatus, tvDate;
    private CoursesController coursesController;
    private AttendanceController attendanceController;
    private String dateSelected;
    private ImageView ivAttendanceFalta, ivAttendanceAsistio, ivAttendanceTardanza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.father_module_attendance_day);
        toolBar("Asistencia", R.string.app_name);

        initialize();
        populateView();
    }

    private void initialize() {
        dateSelected = getIntent().getExtras().getString(Const.BUNDLE_DATE_SELECTED);

        tvDate = (TextView) findViewById(R.id.tvDate);
        tvStatus = (TextView) findViewById(R.id.tvStatus);
        ivAttendanceFalta = (ImageView) findViewById(R.id.ivAttendanceFalta);
        ivAttendanceAsistio = (ImageView) findViewById(R.id.ivAttendanceAsistio);
        ivAttendanceTardanza = (ImageView) findViewById(R.id.ivAttendanceTardanza);

        userController = new UserController(this);
        coursesController = new CoursesController(this);
        attendanceController = new AttendanceController(this);

    }

    private void populateView() {
        User user = userController.findLastStudentSelected();
        Courses courses = coursesController.findLastCourseSelected();
        HashMap paramsInput = attendanceController.wsRequestStatus(dateSelected, user, courses);
        webServiceTask(Const.ACTIVITY_MODULE_ATTENDANCE_STATUS, ModuleAttendanceFatherDayActivity.this, Const.ROUTE_MODULE_ATTENDANCE_GETSTATUS, paramsInput);
    }

    private String getStatusText(int status) {
        String out = "";

        switch (status){
            case Attendance.FALTA:
                out = "Falta";
                ivAttendanceFalta.setVisibility(View.VISIBLE);
                break;
            case Attendance.ASISTIO:
                out = "Asistio";
                ivAttendanceAsistio.setVisibility(View.VISIBLE);
                break;
            case Attendance.TARDANZA:
                out = "Tardanza";
                ivAttendanceTardanza.setVisibility(View.VISIBLE);
                break;
        }

        return out;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void handleOnResponse(final JSONObject jsonOutput) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                WsResponse response = attendanceController.parseWsResponse(jsonOutput);
                int status = response.getStatus();

                if(status == Const.STATUS_SUCCESS){
                    Attendance attendance = attendanceController.parseJsonToObject(jsonOutput);

                    if(attendance != null){
                        tvStatus.setText(getStatusText(attendance.getOptionStatus()));
                        tvDate.setText(attendance.getOptionDate());
                    }else{
                        Utils.shortToast(ModuleAttendanceFatherDayActivity.this, "No se tomo la asistencia");
                    }
                }else{
                    Utils.shortToast(ModuleAttendanceFatherDayActivity.this, response.getMessage());
                }
            }
        });
    }

}
