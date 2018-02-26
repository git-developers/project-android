package com.example.software.activity.father.modules;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.widget.CalendarView;
import android.widget.TextView;

import com.example.software.activity.BaseActivity;
import com.example.software.activity.R;
import com.example.software.controller.ModulesController;
import com.example.software.utils.Const;
import com.example.software.utils.Utils;

import java.util.Calendar;

public class ModuleAttendanceFatherActivity extends BaseActivity {

    private static final String TAG = "ModuleAttendanceFatherActivity";
    private ModulesController modulesController;
    private CalendarView calendarView;
    private TextView dateDisplay;
    private String dateSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.father_module_attendance);
        toolBar("Asistencia", R.string.app_name);

        initialize();

    }

    private void initialize() {

        Calendar calendarMin = Calendar.getInstance();
        calendarMin.set(2017, 3, 1);

        Calendar calendarMax = Calendar.getInstance();
        calendarMax.set(2017, 11, 20);

        calendarView = (CalendarView) findViewById(R.id.calendarView);
        calendarView.setMinDate(calendarMin.getTimeInMillis() - 2000);
        calendarView.setMaxDate(calendarMax.getTimeInMillis() - 2000);
//        calendarView.setDate(calendar.getTimeInMillis(), true, false);

        dateDisplay = (TextView) findViewById(R.id.date_display);
        dateDisplay.setText("");

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int day, int month, int year) {

                String date = year + "-" + (month + 1) + "-" + day;
                dateDisplay.setText(date);
                dateSelected = date;

                gotoAttendanceStatus();
                Utils.shortToast(ModuleAttendanceFatherActivity.this, "Dia = " + day + "\n" + "Mes = " + month + "\n" + "Año = " + year);
            }
        });

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


    private void gotoAttendanceStatus() {
        Intent intent = new Intent();
        intent.setClass(ModuleAttendanceFatherActivity.this, ModuleAttendanceFatherDayActivity.class);

        Bundle bundle = new Bundle();
        bundle.putString(Const.BUNDLE_DATE_SELECTED, dateSelected);
        intent.putExtras(bundle);

        startActivity(intent);
//        ModuleAttendanceFatherActivity.this.finish();

        /* Apply our act_1_splash exit (fade out) and menu_reports entry (fade in) animation transitions. */
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

}
