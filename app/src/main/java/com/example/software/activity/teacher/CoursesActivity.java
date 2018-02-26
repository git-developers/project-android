package com.example.software.activity.teacher;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.software.activity.BaseActivity;
import com.example.software.activity.R;
import com.example.software.activity.implement.IBase;
import com.example.software.activity.ModulesSeleccionActivity;
import com.example.software.adapter.CoursesAdapter;
import com.example.software.controller.CoursesController;
import com.example.software.entity.Courses;
import com.example.software.entity.WsResponse;
import com.example.software.utils.Const;
import com.example.software.utils.Utils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class CoursesActivity extends BaseActivity implements IBase {

    private static final String TAG = "CoursesActivity";
    private ListView listView;
    private CoursesController coursesController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_courses);
        toolBar("Cursos", R.string.app_name);

        initialize();
        populateList();

    }

    private void initialize() {
        listView = (ListView) findViewById(R.id.listView);
        coursesController = new CoursesController(CoursesActivity.this);
    }

    private void populateList() {
        HashMap paramsInput = coursesController.wsListCourses(username);
        webServiceTask(Const.ACTIVITY_COURSES, CoursesActivity.this, Const.ROUTE_COURSES, paramsInput);
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

    private void gotoModules() {
        Intent intent = new Intent();
        intent.setClass(CoursesActivity.this, ModulesSeleccionActivity.class);
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
                WsResponse response = coursesController.parseWsResponse(jsonOutput);
                int status = response.getStatus();

                if(status == Const.STATUS_SUCCESS){
                    List<Courses> listObject = coursesController.parseJsonToArrayObject(jsonOutput);

                    if(listObject != null){

                        listView.setAdapter(new CoursesAdapter(CoursesActivity.this, listObject));

                        // When the user clicks on the ListItem
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                                Object o = listView.getItemAtPosition(position);
                                Courses course = (Courses) o;

                                long idInserted = coursesController.insert(username, course);

                                gotoModules();

                                Utils.shortToast(CoursesActivity.this, "Seleccionado:" + " " + course.getName());
                            }
                        });
                    }
                }else{
                    Utils.shortToast(CoursesActivity.this, response.getMessage());
                }
            }
        });

    }

}
