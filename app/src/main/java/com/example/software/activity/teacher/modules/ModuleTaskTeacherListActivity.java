package com.example.software.activity.teacher.modules;

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
import com.example.software.adapter.TaskTeacherAdapter;
import com.example.software.controller.CoursesController;
import com.example.software.controller.TaskController;
import com.example.software.entity.Courses;
import com.example.software.entity.Task;
import com.example.software.entity.WsResponse;
import com.example.software.utils.Const;
import com.example.software.utils.Utils;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class ModuleTaskTeacherListActivity extends BaseActivity implements IBase {

    private static final String TAG = "ModuleTaskTeacherListActivity";
    private ListView listView;
    private CoursesController coursesController;
    private TaskController taskController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_module_task_list);
        toolBar("Seleccione tarea", R.string.app_name);

        initialize();
        populateList();
    }

    private void initialize() {
        listView = (ListView) findViewById(R.id.listView);
        coursesController = new CoursesController(ModuleTaskTeacherListActivity.this);
        taskController = new TaskController(ModuleTaskTeacherListActivity.this);
    }

    private void populateList() {
        Courses courses = coursesController.findLastCourseSelected();
        HashMap paramsInput = userController.wsTeacherListTasksByCourse(courses);
        webServiceTask(Const.ACTIVITY_MODULE_TASK_TEACHER_LIST, ModuleTaskTeacherListActivity.this, Const.ROUTE_MODULE_TASK_LIST, paramsInput);
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
                WsResponse response = userController.parseWsResponse(jsonOutput);
                int status = response.getStatus();

                if(status == Const.STATUS_SUCCESS){
                    List<Task> listObject = taskController.parseJsonToArrayObject(jsonOutput);

                    if(listObject != null && listObject.size() > 0){

                        listView.setAdapter(new TaskTeacherAdapter(ModuleTaskTeacherListActivity.this, listObject));

                        // When the user clicks on the ListItem
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                                Object o = listView.getItemAtPosition(position);
                                Task task = (Task) o;

                                gotoTaskForm(task);

                                Utils.shortToast(ModuleTaskTeacherListActivity.this, "Seleccionado:" + " " + task.getTarea());
                            }
                        });
                    }else{
                        Utils.shortToast(ModuleTaskTeacherListActivity.this, "No hay tareas");
                    }

                }else{
                    Utils.shortToast(ModuleTaskTeacherListActivity.this, response.getMessage());
                }
            }
        });

    }

    private void gotoTaskForm(Task task) {
        Intent intent = new Intent();
        intent.setClass(ModuleTaskTeacherListActivity.this, ModuleTaskTeacherFormActivity.class);
        intent.putExtra("myObject", new Gson().toJson(task));
        startActivity(intent);
        ModuleTaskTeacherListActivity.this.finish();

        /* Apply our act_1_splash exit (fade out) and menu_reports entry (fade in) animation transitions. */
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

}
