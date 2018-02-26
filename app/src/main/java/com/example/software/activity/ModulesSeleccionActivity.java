package com.example.software.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.software.activity.father.modules.ModuleNoticeFatherActivity;
import com.example.software.activity.father.modules.ModuleAttendanceFatherActivity;
import com.example.software.activity.father.modules.ModuleGradesFatherActivity;
import com.example.software.activity.father.modules.ModuleTaskFatherListActivity;
import com.example.software.activity.teacher.modules.ModuleAttendanceTeacherActivity;
import com.example.software.activity.teacher.modules.ModuleGradesBimesterActivity;
import com.example.software.activity.teacher.modules.ModuleNoticeTeacherActivity;
import com.example.software.activity.teacher.modules.ModuleTaskTeacherActivity;
import com.example.software.adapter.ModulesAdapter;
import com.example.software.controller.ModulesController;
import com.example.software.entity.Modules;
import com.example.software.utils.Const;

import java.util.List;

public class ModulesSeleccionActivity extends BaseActivity {

    private static final String TAG = "ModulesSeleccionActivity";
    private GridView gridview;
    private ModulesController modulesController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_modules);
        toolBar("Modulos", R.string.app_name);

        initialize();

        List<Modules> listObject = modulesController.getModulesListObjects();
        gridview.setAdapter(new ModulesAdapter(ModulesSeleccionActivity.this, listObject));

        // When the user clicks on the ListItem
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = gridview.getItemAtPosition(position);
                Modules module = (Modules) o;
                goToModules(module);
            }
        });

    }

    private void initialize() {
        gridview = (GridView) findViewById(R.id.gridview);
        modulesController = new ModulesController(ModulesSeleccionActivity.this);
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

    private void goToModules(Modules module) {
        Intent intent = new Intent();

        int id = module.getId();

        switch (id){
            case Const.ACTIVITY_MODULE_NOTICE_TEACHER:

                switch (user.getRole()){
                    case Const.ROLE_FATHER:
                        intent.setClass(ModulesSeleccionActivity.this, ModuleNoticeFatherActivity.class);
                        break;
                    case Const.ROLE_TEACHER:
                        intent.setClass(ModulesSeleccionActivity.this, ModuleNoticeTeacherActivity.class);
                        break;
                }

                break;
            case Const.ACTIVITY_MODULE_ATTENDANCE:

                switch (user.getRole()){
                    case Const.ROLE_FATHER:
                        intent.setClass(ModulesSeleccionActivity.this, ModuleAttendanceFatherActivity.class);
                        break;
                    case Const.ROLE_TEACHER:
                        intent.setClass(ModulesSeleccionActivity.this, ModuleAttendanceTeacherActivity.class);
                        break;
                }

                break;
            case Const.ACTIVITY_MODULE_GRADES:

                switch (user.getRole()){
                    case Const.ROLE_FATHER:
                        intent.setClass(ModulesSeleccionActivity.this, ModuleGradesFatherActivity.class);
                        break;
                    case Const.ROLE_TEACHER:
                        intent.setClass(ModulesSeleccionActivity.this, ModuleGradesBimesterActivity.class);
                        break;
                }

                break;
            case Const.ACTIVITY_MODULE_TASK:

                switch (user.getRole()){
                    case Const.ROLE_FATHER:
                        intent.setClass(ModulesSeleccionActivity.this, ModuleTaskFatherListActivity.class);
                        break;
                    case Const.ROLE_TEACHER:
                        intent.setClass(ModulesSeleccionActivity.this, ModuleTaskTeacherActivity.class);
                        break;
                }

                break;
            default:
                intent.setClass(ModulesSeleccionActivity.this, ModuleGradesBimesterActivity.class);
                break;
        }

        startActivity(intent);
//        CoursesActivity.this.finish();

        /* Apply our act_1_splash exit (fade out) and menu_reports entry (fade in) animation transitions. */
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

}
