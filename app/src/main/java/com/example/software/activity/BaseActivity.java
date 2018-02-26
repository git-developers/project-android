package com.example.software.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.software.activity.father.CoursesByUserActivity;
import com.example.software.activity.father.ListChildrenActivity;
import com.example.software.activity.father.modules.ModuleNoticeFatherActivity;
import com.example.software.activity.father.ReportsActivity;
import com.example.software.activity.father.modules.ModuleAttendanceFatherDayActivity;
import com.example.software.activity.father.modules.ModuleGradesFatherActivity;
import com.example.software.activity.father.modules.ModuleTaskFatherListActivity;
import com.example.software.activity.teacher.CoursesActivity;
import com.example.software.activity.teacher.modules.ModuleAttendanceTeacherActivity;
import com.example.software.activity.teacher.modules.ModuleGradesEnterGradeActivity;
import com.example.software.activity.teacher.modules.ModuleGradesListStudentsActivity;
import com.example.software.activity.teacher.modules.ModuleNoticeTeacherActivity;
import com.example.software.activity.teacher.modules.ModuleTaskTeacherFormActivity;
import com.example.software.activity.teacher.modules.ModuleTaskTeacherListActivity;
import com.example.software.controller.UserController;
import com.example.software.entity.User;
import com.example.software.utils.Const;
import com.example.software.utils.Utils;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.HashMap;


public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private SharedPreferences sharedpreferences;
    private TextView tvUserName, tvUserEmail;
    private View llnavigationView;
    protected UserController userController;
    public User user;
    public String username;
    public Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setNavigationView();
    }

    private void initialize() {
        this.sharedpreferences = getSharedPreferences(Const.PREFERENCES, 0);
        userController = new UserController(this);
        user = userController.findLastLogged();
        username = user.getUsername();
    }

    public Toolbar toolBar(String title, int subtitle) {

        try{
            Resources res = getResources();
            String subtitleTxt = res.getString(subtitle, username, 1);

            toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setNavigationIcon(R.mipmap.ic_launcher);
            toolbar.setTitle(title);
            toolbar.setSubtitle(subtitleTxt);
            toolbar.setLogo(R.mipmap.ic_launcher);
            toolbar.setPadding(0,0,0,0);
            toolbar.setContentInsetsAbsolute(0,0);
            setSupportActionBar(toolbar);
        }catch (NullPointerException e){

        }

        return toolbar;

    }

    /**
     * http://stackoverflow.com/questions/33009469/baseactivity-for-navigation
     */
    private void setNavigationView() {
        try{
            //toolbar = se tiene que cargar desde el metodo: toolBar()
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            navigationView.bringToFront();
            llnavigationView = navigationView.getHeaderView(0);
            hideNavigationItems(navigationView);

            tvUserName = (TextView) llnavigationView.findViewById(R.id.tvUserName);
            tvUserEmail = (TextView) llnavigationView.findViewById(R.id.tvUserEmail);
            tvUserName.setText(user.getName());
            tvUserEmail.setText(user.getEmail());
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    private void hideNavigationItems(NavigationView navigationView) {
        try{

            Menu menu = navigationView.getMenu();
            MenuItem listCourses = menu.findItem(R.id.nav_list_courses);
            MenuItem listChildren = menu.findItem(R.id.nav_list_children);

            switch (user.getRole()){
                case Const.ROLE_FATHER:
                    listChildren.setVisible(true);
                    break;
                case Const.ROLE_TEACHER:
                    listCourses.setVisible(true);
                    break;
                default:
                    Utils.shortToast(this, "El usuario no tiene un rol");
                    break;
            }

        }catch (NullPointerException e){
            Log.d("POLLO", "ERROR: " + e.getMessage());
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_list_courses) {
            gotoCourses();
        } else if (id == R.id.nav_list_children) {
            gotoListChildren();
        } else if (id == R.id.nav_reports) {
            gotoReports();
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_logout) {
            gotoLogin();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void gotoLogin() {
        setSharePreferencesIsLogged(false);

        Intent intent = new Intent();
        intent.setClass(BaseActivity.this, LoginActivity.class);
        startActivity(intent);
        BaseActivity.this.finish();

        /* Apply our act_1_splash exit (fade out) and menu_reports entry (fade in) animation transitions. */
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    private void gotoCourses() {
        Intent intent = new Intent();
        intent.setClass(BaseActivity.this, CoursesActivity.class);
        startActivity(intent);
        BaseActivity.this.finish();

        /* Apply our act_1_splash exit (fade out) and menu_reports entry (fade in) animation transitions. */
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    private void gotoListChildren() {
        Intent intent = new Intent();
        intent.setClass(BaseActivity.this, ListChildrenActivity.class);
        startActivity(intent);
        BaseActivity.this.finish();

        /* Apply our act_1_splash exit (fade out) and menu_reports entry (fade in) animation transitions. */
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    private void gotoReports() {
        Intent intent = new Intent();
        intent.setClass(BaseActivity.this, ReportsActivity.class);

        Bundle bundle = new Bundle();
        bundle.putString(Const.BUNDLE_ROUTE, Const.ROUTE_REPORTS_PIECHART);
        intent.putExtras(bundle);

        startActivity(intent);
        BaseActivity.this.finish();

        /* Apply our act_1_splash exit (fade out) and menu_reports entry (fade in) animation transitions. */
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    public void failedAsyncTask(final Context context, final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Utils.shortToast(context, message);
            }
        });
    }

    public void setSharePreferencesIsLogged(boolean isLogged) {
        Editor editor = this.sharedpreferences.edit();
        editor.putBoolean(Const.IS_LOGGED, isLogged);
        editor.commit();
    }

    public boolean getSharePreferencesIsLogged() {
        return this.sharedpreferences.getBoolean(Const.IS_LOGGED, false);
    }

    public void setSharePreferencesActivity(int actividad) {
        Editor editor = this.sharedpreferences.edit();
        editor.putInt(Const.LAST_ACTIVITY, actividad);
        editor.commit();
    }

    public int getSharePreferencesActivity() {
        return this.sharedpreferences.getInt(Const.LAST_ACTIVITY, 1);
    }

    /**
     * Shows the progress UI and hides the act_4_login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                View mProgressView = (ProgressBar) findViewById(R.id.progress_bar);

                // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
                // for very easy animations. If available, use these APIs to fade-in
                // the progress spinner.
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
                    int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);

//            mProgressView.animate().setDuration(shortAnimTime).alpha(show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    mProgressView.setVisibility(show ? View.GONE : View.VISIBLE);
//                }
//            });

//            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
//            mProgressView.animate().setDuration(shortAnimTime).alpha(show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
//                }
//            });
                } else {
                    // The ViewPropertyAnimator APIs are not available, so simply show
                    // and hide the relevant UI components.
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
//            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }

            }
        });

    }

    /**
     * https://kylewbanks.com/blog/Tutorial-Android-Parsing-JSON-with-GSON
     */
    public void webServiceTask(final int currentActivity, final Context context, String route, final HashMap params) {

        final String TAG = "webServiceTask";
        Log.i(TAG, "**** CONNECT TO WEB SERVICE :: INICIO ****");
        Log.i(TAG, "WS: ROUTE: " + route);
        Log.i(TAG, "WS: PARAMS: " + params);

        showProgress(true);
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, route, null,
            new Response.Listener<JSONObject>()
            {
                @Override
                public void onResponse(JSONObject response) {

                    Log.i(TAG, "WS: onResponse: " + response.toString());

                    switch (currentActivity){
                        case Const.ACTIVITY_LOGIN:
                        ((LoginActivity)context).handleOnResponse(response);
                            break;
                        case Const.ACTIVITY_COURSES:
                        ((CoursesActivity)context).handleOnResponse(response);
                            break;
                        case Const.ACTIVITY_MODULE_NOTICE_TEACHER:
                        ((ModuleNoticeTeacherActivity)context).handleOnResponse(response);
                            break;
                        case Const.ACTIVITY_MODULE_GRADES_LIST_STUDENTS:
                        ((ModuleGradesListStudentsActivity)context).handleOnResponse(response);
                            break;
                        case Const.ACTIVITY_MODULE_GRADES_CREATE:
                        ((ModuleGradesEnterGradeActivity)context).handleOnResponse(response);
                            break;
                        case Const.ACTIVITY_MODULE_ATTENDANCE:
                        ((ModuleAttendanceTeacherActivity)context).handleOnResponse(response);
                            break;
                        case Const.ACTIVITY_MODULE_ATTENDANCE_CREATE:
                        ((ModuleAttendanceTeacherActivity)context).handleOnResponseList(response);
                            break;
                        case Const.ACTIVITY_LIST_CHILDREN:
                        ((ListChildrenActivity)context).handleOnResponse(response);
                            break;
                        case Const.ACTIVITY_COURSES_BY_USER:
                        ((CoursesByUserActivity)context).handleOnResponse(response);
                            break;
                        case Const.ACTIVITY_MODULE_NOTICE_FATHER:
                        ((ModuleNoticeFatherActivity)context).handleOnResponse(response);
                            break;
                        case Const.ACTIVITY_MODULE_GRADES_FATHER:
                        ((ModuleGradesFatherActivity)context).handleOnResponse(response);
                            break;
                        case Const.ACTIVITY_MODULE_ATTENDANCE_STATUS:
                        ((ModuleAttendanceFatherDayActivity)context).handleOnResponse(response);
                            break;
                        case Const.ACTIVITY_MODULE_TASK_CREATE:
                        ((ModuleTaskTeacherFormActivity)context).handleOnResponse(response);
                            break;
                        case Const.ACTIVITY_MODULE_TASK_TEACHER_LIST:
                        ((ModuleTaskTeacherListActivity)context).handleOnResponse(response);
                            break;
                        case Const.ACTIVITY_MODULE_TASK_FATHER_LIST:
                        ((ModuleTaskFatherListActivity)context).handleOnResponse(response);
                            break;
                    }

                    showProgress(false);
                }
            },
            new Response.ErrorListener()
            {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Utils.shortToast(context, "Hubo una excepcion, reintentar (" + error.toString() + ")");
                    Log.i(TAG, "WS: onErrorResponse: " + error.toString());
                    showProgress(false);
                }
            }
        ){

            @Override
            public byte[] getBody()  {
                JSONObject obj = new JSONObject(params);
                String objStr = obj.toString();
                byte[] objByte = objStr.getBytes();
                return objByte;
            }

            @Override
            public String getBodyContentType() {
                return Const.CONTENT_TYPE;
            }
        };

        RetryPolicy policy = new DefaultRetryPolicy(Const.SOCKET_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);

        // add it to the RequestQueue
        requestQueue.add(request);
        Log.i(TAG, "**** CONNECT TO WEB SERVICE :: FIN ****");
    }

}
