package com.example.software.activity.teacher.modules;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.software.activity.BaseActivity;
import com.example.software.activity.R;
import com.example.software.activity.implement.IBase;
import com.example.software.adapter.StudentsAttendanceAdapter;
import com.example.software.controller.CoursesController;
import com.example.software.controller.ModulesController;
import com.example.software.entity.Courses;
import com.example.software.entity.User;
import com.example.software.entity.WsResponse;
import com.example.software.utils.Const;
import com.example.software.utils.Utils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ModuleAttendanceTeacherActivity extends BaseActivity implements IBase {

    private static final String TAG = "ModuleAttendanceTeacherActivity";
    private ListView listView;
    private ModulesController modulesController;
    private CoursesController coursesController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_module_attendance);
        toolBar("Asistencia", R.string.app_name);

        initialize();
        populateList();

        Button submitButton = (Button) findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptPost();
            }
        });

    }

    private void initialize() {
        listView = (ListView) findViewById(R.id.listView);
        coursesController = new CoursesController(ModuleAttendanceTeacherActivity.this);
        modulesController = new ModulesController(ModuleAttendanceTeacherActivity.this);
    }

    private void populateList() {
        Courses courses = coursesController.findLastCourseSelected();
        HashMap paramsInput = userController.wsListStudentsByCourse(courses);
        webServiceTask(Const.ACTIVITY_MODULE_ATTENDANCE, ModuleAttendanceTeacherActivity.this, Const.ROUTE_COURSES_LIST_STUDENTS, paramsInput);
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

    private void attemptPost() {

//        v = listView.getAdapter().getView(i, null, null);
//        et = (EditText) v.findViewById(R.id.et_username);
//        String username = et.getText().toString();
        List<User> listUsers = new ArrayList<User>();
        for (int i = 0; i < listView.getCount(); i++) {
            User user = (User) listView.getAdapter().getItem(i);
            listUsers.add(user);
        }

        Courses course = coursesController.findLastCourseSelected();
        HashMap paramsInput = userController.wsStudentsAttendance(username, course, listUsers);
        webServiceTask(Const.ACTIVITY_MODULE_ATTENDANCE_CREATE, ModuleAttendanceTeacherActivity.this, Const.ROUTE_MODULE_ATTENDANCE_CREATE, paramsInput);
    }

    @Override
    public void handleOnResponse(final JSONObject jsonOutput) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                WsResponse response = userController.parseWsResponse(jsonOutput);
                int status = response.getStatus();

                if(status == Const.STATUS_SUCCESS){
                    List<User> listObject = userController.parseUsersByCourse(jsonOutput);
                    if(listObject != null){
                        listView.setAdapter(new StudentsAttendanceAdapter(ModuleAttendanceTeacherActivity.this, listObject));
                    }
                }else{
                    Utils.shortToast(ModuleAttendanceTeacherActivity.this, response.getMessage());
                }
            }
        });

    }

    public void handleOnResponseList(final JSONObject jsonOutput) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                WsResponse response = userController.parseWsResponse(jsonOutput);
                int status = response.getStatus();

                if(status == Const.STATUS_SUCCESS){
                    Utils.shortToast(ModuleAttendanceTeacherActivity.this, "Creado correctamente");
                }else{
                    Utils.shortToast(ModuleAttendanceTeacherActivity.this, response.getMessage());
                }
            }
        });
    }

}
