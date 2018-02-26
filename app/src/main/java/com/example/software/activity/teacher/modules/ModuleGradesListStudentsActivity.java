package com.example.software.activity.teacher.modules;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.software.activity.BaseActivity;
import com.example.software.activity.R;
import com.example.software.activity.implement.IBase;
import com.example.software.adapter.StudentsAdapter;
import com.example.software.controller.CoursesController;
import com.example.software.entity.Courses;
import com.example.software.entity.User;
import com.example.software.entity.WsResponse;
import com.example.software.utils.Const;
import com.example.software.utils.Utils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class ModuleGradesListStudentsActivity extends BaseActivity implements IBase {

    private static final String TAG = "ListChildrenActivity";
    private ListView listView;
    private CoursesController coursesController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_module_grades_list_students);
        toolBar("Seleccione alumno", R.string.app_name);

        initialize();
        populateList();
    }

    private void initialize() {
        listView = (ListView) findViewById(R.id.listView);
        coursesController = new CoursesController(ModuleGradesListStudentsActivity.this);
    }

    private void populateList() {
        Courses courses = coursesController.findLastCourseSelected();
        HashMap paramsInput = userController.wsListStudentsByCourse(courses);
        webServiceTask(Const.ACTIVITY_MODULE_GRADES_LIST_STUDENTS, ModuleGradesListStudentsActivity.this, Const.ROUTE_COURSES_LIST_STUDENTS, paramsInput);
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

    private void gotoEnterNotes() {
        Intent intent = new Intent();
        intent.setClass(ModuleGradesListStudentsActivity.this, ModuleGradesEnterGradeActivity.class);
        startActivity(intent);
//        CoursesActivity.this.finish();

        /* Apply our act_1_splash exit (fade out) and menu_reports entry (fade in) animation transitions. */
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
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

                        listView.setAdapter(new StudentsAdapter(ModuleGradesListStudentsActivity.this, listObject));

                        // When the user clicks on the ListItem
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                                Object o = listView.getItemAtPosition(position);
                                User user = (User) o;

                                long idInserted = userController.insert(user);

                                gotoEnterNotes();

                                Utils.shortToast(ModuleGradesListStudentsActivity.this, "Seleccionado:" + " " + user.getName());
                            }
                        });
                    }
                }else{
                    Utils.shortToast(ModuleGradesListStudentsActivity.this, response.getMessage());
                }
            }
        });

    }

}
