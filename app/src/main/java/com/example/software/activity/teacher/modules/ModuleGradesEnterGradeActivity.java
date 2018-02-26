package com.example.software.activity.teacher.modules;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.example.software.activity.BaseActivity;
import com.example.software.activity.R;
import com.example.software.activity.implement.IBase;
import com.example.software.controller.CoursesController;
import com.example.software.controller.ModulesController;
import com.example.software.entity.Courses;
import com.example.software.entity.Semester;
import com.example.software.entity.User;
import com.example.software.entity.WsResponse;
import com.example.software.utils.Const;
import com.example.software.utils.Utils;

import org.json.JSONObject;

import java.util.HashMap;

public class ModuleGradesEnterGradeActivity extends BaseActivity implements IBase {

    private static final String TAG = "ModuleGradesEnterGradeActivity";
    private CoursesController coursesController;
    private ModulesController modulesController;
    private AutoCompleteTextView txtExamenMensual, txtExamenBimestral, txtNotaProfesor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_module_grades_enter_grade);
        toolBar("Ingresar notas", R.string.app_name);

        initialize();

        Button submitButton = (Button) findViewById(R.id.submit_notice);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptPost();
            }
        });
    }

    private void initialize() {
        txtExamenMensual = (AutoCompleteTextView) findViewById(R.id.txtExamenMensual);
        txtExamenBimestral = (AutoCompleteTextView) findViewById(R.id.txtExamenBimestral);
        txtNotaProfesor = (AutoCompleteTextView) findViewById(R.id.txtNotaProfesor);

        coursesController = new CoursesController(ModuleGradesEnterGradeActivity.this);
        modulesController = new ModulesController(ModuleGradesEnterGradeActivity.this);
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

    /**
     * Attempts to sign in or register the account specified by the act_4_login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual act_4_login attempt is made.
     */
    private void attemptPost() {

        // Reset errors.
        txtExamenMensual.setError(null);
        txtExamenBimestral.setError(null);
        txtNotaProfesor.setError(null);

        // Store values at the time of the act_4_login attempt.
        String examenMensual = txtExamenMensual.getText().toString();
        String examenBimestral = txtExamenBimestral.getText().toString();
        String notaProfesor = txtNotaProfesor.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(examenMensual)) {
            txtExamenMensual.setError(getString(R.string.error_field_required));
            focusView = txtExamenMensual;
            cancel = true;
        }else if (TextUtils.isEmpty(examenBimestral)) {
            txtExamenBimestral.setError(getString(R.string.error_field_required));
            focusView = txtExamenBimestral;
            cancel = true;
        }else if (TextUtils.isEmpty(notaProfesor)) {
            txtNotaProfesor.setError(getString(R.string.error_field_required));
            focusView = txtNotaProfesor;
            cancel = true;
        }else if (Integer.valueOf(examenMensual) > 20) {
            txtExamenMensual.setError(getString(R.string.error_field_length_20));
            focusView = txtExamenMensual;
            cancel = true;
        }else if (Integer.valueOf(examenBimestral) > 20) {
            txtExamenBimestral.setError(getString(R.string.error_field_length_20));
            focusView = txtExamenBimestral;
            cancel = true;
        }else if (Integer.valueOf(notaProfesor) > 20) {
            txtNotaProfesor.setError(getString(R.string.error_field_length_20));
            focusView = txtNotaProfesor;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt act_4_login and focus the first form field with an error.
//            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to perform the user act_4_login attempt.
            //conectarse al web service
            Courses course = coursesController.findLastCourseSelected();
            User user = userController.findLastStudentSelected();
            Semester semester = modulesController.findLastSemesterSelected();
            HashMap paramsInput = modulesController.wsCreateGrade(course, user, semester, examenMensual, examenBimestral, notaProfesor);
            webServiceTask(Const.ACTIVITY_MODULE_GRADES_CREATE, ModuleGradesEnterGradeActivity.this, Const.ROUTE_MODULE_GRADES_CREATE, paramsInput);
        }
    }

    @Override
    public void handleOnResponse(final JSONObject jsonOutput) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                WsResponse response = modulesController.parseWsResponse(jsonOutput);
                int status = response.getStatus();

                if(status == Const.STATUS_SUCCESS){
                    Utils.shortToast(ModuleGradesEnterGradeActivity.this, "Creado correctamente");
                }else{
                    Utils.shortToast(ModuleGradesEnterGradeActivity.this, response.getMessage());
                }
            }
        });
    }

}
