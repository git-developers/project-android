package com.example.software.activity.teacher.modules;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.software.activity.BaseActivity;
import com.example.software.activity.R;
import com.example.software.adapter.TaskListAdapter;
import com.example.software.controller.ModulesController;
import com.example.software.entity.TaskList;

import java.util.List;

public class ModuleTaskTeacherActivity extends BaseActivity {

    private static final String TAG = "ModuleTaskTeacherActivity";
    private GridView gridview;
    private ModulesController modulesController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_module_task);
        toolBar("Tareas", R.string.app_name);

        initialize();

        List<TaskList> listObject = modulesController.getTaskListOptions();
        gridview.setAdapter(new TaskListAdapter(ModuleTaskTeacherActivity.this, listObject));

        // When the user clicks on the ListItem
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = gridview.getItemAtPosition(position);
                TaskList taskList = (TaskList) o;
                goToModules(taskList);
            }
        });

    }

    private void initialize() {
        gridview = (GridView) findViewById(R.id.gridview);
        modulesController = new ModulesController(ModuleTaskTeacherActivity.this);
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

    private void goToModules(TaskList taskList) {
        Intent intent = new Intent();

        int id = taskList.getId();

        switch (id){
            case TaskList.CREATE_NEW:
                intent.setClass(ModuleTaskTeacherActivity.this, ModuleTaskTeacherFormActivity.class);
                break;
            case TaskList.LIST_TASK:
                intent.setClass(ModuleTaskTeacherActivity.this, ModuleTaskTeacherListActivity.class);
                break;
            default:
                intent.setClass(ModuleTaskTeacherActivity.this, ModuleTaskTeacherListActivity.class);
                break;
        }

        startActivity(intent);
//        CoursesActivity.this.finish();

        /* Apply our act_1_splash exit (fade out) and menu_reports entry (fade in) animation transitions. */
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }



}
