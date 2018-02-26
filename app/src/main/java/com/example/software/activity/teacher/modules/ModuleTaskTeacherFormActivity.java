package com.example.software.activity.teacher.modules;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.software.activity.BaseActivity;
import com.example.software.activity.R;
import com.example.software.activity.implement.IBase;
import com.example.software.controller.CoursesController;
import com.example.software.controller.ModulesController;
import com.example.software.entity.Courses;
import com.example.software.entity.Task;
import com.example.software.entity.User;
import com.example.software.entity.WsResponse;
import com.example.software.utils.Const;
import com.example.software.utils.Utils;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

public class ModuleTaskTeacherFormActivity extends BaseActivity implements IBase, AdapterView.OnItemSelectedListener, View.OnFocusChangeListener, DatePickerDialog.OnDateSetListener {

    private static final String TAG = "ModuleTaskTeacherFormActivity";
    private ModulesController modulesController;
    private CoursesController coursesController;
    private AutoCompleteTextView txtTarea, txtTaskId;
    private EditText etFechaEntrega;
    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day;
    private Spinner spinnerNota, spinnerEstado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_module_task_form);
        toolBar("Crear tarea", R.string.app_name);

        initialize();
        setTaskValues();

        Button submitButton = (Button) findViewById(R.id.submit_notice);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptPost();
            }
        });

    }

    private void initialize() {
        coursesController = new CoursesController(ModuleTaskTeacherFormActivity.this);
        modulesController = new ModulesController(ModuleTaskTeacherFormActivity.this);
        calendar = Calendar.getInstance(TimeZone.getDefault());

        txtTaskId = (AutoCompleteTextView) findViewById(R.id.txtTaskId);
        txtTarea = (AutoCompleteTextView) findViewById(R.id.txtTarea);
        etFechaEntrega = (EditText) findViewById(R.id.etFechaEntrega);
        spinnerNota = (Spinner) findViewById(R.id.spinnerNota);

        etFechaEntrega.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    etFechaEntrega.setShowSoftInputOnFocus(false);
                }
                datePicker();
            }
        });

        //spinnerNota
        spinnerNota = (Spinner) findViewById(R.id.spinnerNota);
        spinnerNota.setOnItemSelectedListener(ModuleTaskTeacherFormActivity.this);
        List<String> listObject = modulesController.getSpinnerNotas();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listObject);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerNota.setAdapter(dataAdapter);

        //spinnerEstado
        spinnerEstado = (Spinner) findViewById(R.id.spinnerEstado);
        spinnerEstado.setOnItemSelectedListener(ModuleTaskTeacherFormActivity.this);
        List<String> listObject2 = modulesController.getTaskEstadoList();
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listObject2);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEstado.setAdapter(dataAdapter2);

    }

    private void setTaskValues() {

        String jsonMyObject = "";
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            jsonMyObject = extras.getString("myObject");
        }

        Task task = new Gson().fromJson(jsonMyObject, Task.class);

        if(task != null){
            String id = String.valueOf(task.getId());
            txtTaskId.setText(id);
            txtTarea.setText(task.getTarea());
            etFechaEntrega.setText(task.getFechaEntregaDate());
            spinnerNota.setSelection(task.getNotaInt());
            spinnerEstado.setSelection(task.getEstadoInt());
        }

    }

    private void datePicker() {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(ModuleTaskTeacherFormActivity.this, this, year, month, day);
        dialog.show();
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
        txtTarea.setError(null);
        etFechaEntrega.setError(null);

        // Store values at the time of the act_4_login attempt.
        String taskId = txtTaskId.getText().toString();
        String tarea = txtTarea.getText().toString();
        String fechaEntrega = etFechaEntrega.getText().toString();
        int notaProfesor = spinnerNota.getSelectedItemPosition();
        int estado = spinnerEstado.getSelectedItemPosition();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(tarea)) {
            txtTarea.setError(getString(R.string.error_field_required));
            focusView = txtTarea;
            cancel = true;
        }else if (TextUtils.isEmpty(fechaEntrega)) {
            etFechaEntrega.setError(getString(R.string.error_field_required));
            focusView = etFechaEntrega;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt act_4_login and focus the first form field with an error.
//            focusView.requestFocus();
        } else {
            // Show a progress spinnerNota, and kick off a background task to perform the user act_4_login attempt.
            //conectarse al web service
            Courses course = coursesController.findLastCourseSelected();
            User user = userController.findLastStudentSelected();
            HashMap paramsInput = modulesController.wsCreateTask(username, course, taskId, tarea, fechaEntrega, notaProfesor, estado);
            webServiceTask(Const.ACTIVITY_MODULE_TASK_CREATE, ModuleTaskTeacherFormActivity.this, Const.ROUTE_MODULE_TASK_CREATE, paramsInput);
        }

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdformat = new SimpleDateFormat(myFormat); //, Locale.US
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        etFechaEntrega.setText(sdformat.format(calendar.getTime()));
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        datePicker();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void handleOnResponse(final JSONObject jsonOutput) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                WsResponse response = userController.parseWsResponse(jsonOutput);
                int status = response.getStatus();

                if(status == Const.STATUS_SUCCESS){
                    Utils.shortToast(ModuleTaskTeacherFormActivity.this, "creado correctamente");
                }else{
                    Utils.shortToast(ModuleTaskTeacherFormActivity.this, response.getMessage());
                }
            }
        });
    }
}
