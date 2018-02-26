package com.example.software.activity.teacher.modules;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import com.example.software.activity.BaseActivity;
import com.example.software.activity.R;
import com.example.software.activity.implement.IBase;
import com.example.software.controller.CoursesController;
import com.example.software.controller.ModulesController;
import com.example.software.entity.Courses;
import com.example.software.entity.WsResponse;
import com.example.software.utils.Const;
import com.example.software.utils.Utils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class ModuleNoticeTeacherActivity extends BaseActivity implements IBase, OnItemSelectedListener {

    private static final String TAG = "ModuleNoticeTeacherActivity";
    private ModulesController modulesController;
    private CoursesController coursesController;
    private Spinner spinner;
    private AutoCompleteTextView mMessageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_module_notice);
        toolBar("Comunicado", R.string.app_name);

        initialize();

        List<String> listObject = modulesController.getSpinnerNoticeType();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listObject);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        Button submitButton = (Button) findViewById(R.id.submit_notice);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptPost();
            }
        });

    }

    private void initialize() {

        coursesController = new CoursesController(ModuleNoticeTeacherActivity.this);
        modulesController = new ModulesController(ModuleNoticeTeacherActivity.this);
        mMessageView = (AutoCompleteTextView) findViewById(R.id.txtMesssage);

        //spinner
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(ModuleNoticeTeacherActivity.this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selecciono: " + item + " -- " + position, Toast.LENGTH_LONG).show();
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
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
        mMessageView.setError(null);

        // Store values at the time of the act_4_login attempt.
        String message = mMessageView.getText().toString();
        int noticeType = spinner.getSelectedItemPosition();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid email address.
        if (noticeType <= 0) {
            Utils.shortToast(this, "Seleccione una opcion");
            cancel = true;
        }else if (TextUtils.isEmpty(message)) {
            mMessageView.setError(getString(R.string.error_field_required));
            focusView = mMessageView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt act_4_login and focus the first form field with an error.
//            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to perform the user act_4_login attempt.
            //conectarse al web service

            Courses course = coursesController.findLastCourseSelected();
            HashMap paramsInput = modulesController.wsCreateNotice(username, course, message, noticeType);
            webServiceTask(Const.ACTIVITY_MODULE_NOTICE_TEACHER, ModuleNoticeTeacherActivity.this, Const.ROUTE_MODULE_CREATE_NOTICE, paramsInput);
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
                    Utils.shortToast(ModuleNoticeTeacherActivity.this, "Creado correctamente");
                }else{
                    Utils.shortToast(ModuleNoticeTeacherActivity.this, response.getMessage());
                }
            }
        });
    }

}
