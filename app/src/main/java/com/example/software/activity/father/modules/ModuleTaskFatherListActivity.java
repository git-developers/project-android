package com.example.software.activity.father.modules;

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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class ModuleTaskFatherListActivity extends BaseActivity implements IBase {

    private static final String TAG = "ModuleTaskFatherListActivity";
    private ListView listView;
    private CoursesController coursesController;
    private TaskController taskController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.father_module_task_list);
        toolBar("Tareas", R.string.app_name);

        initialize();
        populateList();
    }

    private void initialize() {
        listView = (ListView) findViewById(R.id.listView);
        coursesController = new CoursesController(ModuleTaskFatherListActivity.this);
        taskController = new TaskController(ModuleTaskFatherListActivity.this);
    }

    private void populateList() {
        Courses courses = coursesController.findLastCourseSelected();
        HashMap paramsInput = userController.wsFatherListTasksByCourse(courses, Const.NO_PENDIENTE);
        webServiceTask(Const.ACTIVITY_MODULE_TASK_FATHER_LIST, ModuleTaskFatherListActivity.this, Const.ROUTE_MODULE_TASK_LIST, paramsInput);
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

                        listView.setAdapter(new TaskTeacherAdapter(ModuleTaskFatherListActivity.this, listObject));

                        // When the user clicks on the ListItem
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                                Object o = listView.getItemAtPosition(position);
                                Task task = (Task) o;

                                Utils.shortToast(ModuleTaskFatherListActivity.this, "Tarea:" + " " + task.getTarea());
                            }
                        });
                    }else{
                        Utils.shortToast(ModuleTaskFatherListActivity.this, "No hay tareas");
                    }

                }else{
                    Utils.shortToast(ModuleTaskFatherListActivity.this, response.getMessage());
                }
            }
        });

    }

}
