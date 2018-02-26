package com.example.software.activity.father.modules;

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
import com.example.software.adapter.GradesAdapter;
import com.example.software.controller.CoursesController;
import com.example.software.controller.GradesController;
import com.example.software.controller.ModulesController;
import com.example.software.controller.UserController;
import com.example.software.entity.Courses;
import com.example.software.entity.Grades;
import com.example.software.entity.Semester;
import com.example.software.entity.User;
import com.example.software.entity.WsResponse;
import com.example.software.utils.Const;
import com.example.software.utils.Utils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class ModuleGradesFatherActivity extends BaseActivity implements IBase {

    private static final String TAG = "ModuleGradesFatherActivity";
    private ListView listView;
    private GradesController gradesController;
    private CoursesController coursesController;
    private ModulesController modulesController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.father_list_grades);
        toolBar("Notas", R.string.app_name);

        initialize();
        populateList();
    }

    private void initialize() {
        userController = new UserController(this);
        coursesController = new CoursesController(this);
        modulesController = new ModulesController(this);
        gradesController = new GradesController(this);
        listView = (ListView) findViewById(R.id.listView);
    }

    private void populateList() {
        User user = userController.findLastChildSelected();
        Courses courses = coursesController.findLastCourseSelected();
        Semester semester = modulesController.findLastSemesterSelected();
        HashMap paramsInput = gradesController.wsListGrades(user, courses, semester);
        webServiceTask(Const.ACTIVITY_MODULE_GRADES_FATHER, ModuleGradesFatherActivity.this, Const.ROUTE_MODULE_GRADES_LIST, paramsInput);
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
                WsResponse response = gradesController.parseWsResponse(jsonOutput);
                int status = response.getStatus();

                if(status == Const.STATUS_SUCCESS){
                    List<Grades> listObject = gradesController.parseJsonToArrayObject(jsonOutput);

                    if(listObject != null){

                        listView.setAdapter(new GradesAdapter(ModuleGradesFatherActivity.this, listObject));

                        // When the user clicks on the ListItem
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                                Object o = listView.getItemAtPosition(position);
                                Grades grades = (Grades) o;

                                Utils.shortToast(ModuleGradesFatherActivity.this, "Seleccionado:" + " " + grades.getId());
                            }
                        });
                    }
                }else{
                    Utils.shortToast(ModuleGradesFatherActivity.this, response.getMessage());
                }
            }
        });

    }

}
