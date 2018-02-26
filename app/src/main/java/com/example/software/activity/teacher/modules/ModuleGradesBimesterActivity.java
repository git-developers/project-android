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
import com.example.software.adapter.SemesterAdapter;
import com.example.software.controller.ModulesController;
import com.example.software.entity.Semester;

import java.util.List;

public class ModuleGradesBimesterActivity extends BaseActivity {

    private static final String TAG = "ModuleGradesBimesterActivity";
    private GridView gridview;
    private ModulesController modulesController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_module_grades);
        toolBar("Seleccione bimestre", R.string.app_name);

        initialize();

        List<Semester> listObject = modulesController.getSemesters();
        gridview.setAdapter(new SemesterAdapter(ModuleGradesBimesterActivity.this, listObject));

        // When the user clicks on the ListItem
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = gridview.getItemAtPosition(position);
                Semester semester = (Semester) o;

                long idInserted = modulesController.insertSemester(username, semester);

                goToStudentList(semester);
            }
        });

    }

    private void initialize() {
        gridview = (GridView) findViewById(R.id.gridview);
        modulesController = new ModulesController(ModuleGradesBimesterActivity.this);
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

    private void goToStudentList(Semester semester) {
        Intent intent = new Intent();

        int id = semester.getId();

        intent.setClass(ModuleGradesBimesterActivity.this, ModuleGradesListStudentsActivity.class);
        startActivity(intent);
//        CoursesActivity.this.finish();

        /* Apply our act_1_splash exit (fade out) and menu_reports entry (fade in) animation transitions. */
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

}
