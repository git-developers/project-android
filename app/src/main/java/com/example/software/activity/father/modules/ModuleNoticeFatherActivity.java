package com.example.software.activity.father.modules;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.software.activity.BaseActivity;
import com.example.software.activity.R;
import com.example.software.activity.father.CoursesByUserActivity;
import com.example.software.activity.implement.IBase;
import com.example.software.adapter.NoticeFatherAdapter;
import com.example.software.controller.CoursesController;
import com.example.software.controller.NoticeController;
import com.example.software.controller.UserController;
import com.example.software.entity.Courses;
import com.example.software.entity.Notice;
import com.example.software.entity.WsResponse;
import com.example.software.utils.Const;
import com.example.software.utils.Utils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class ModuleNoticeFatherActivity extends BaseActivity implements IBase {

    private static final String TAG = "ModuleNoticeFatherActivity";
    private ListView listView;
    private UserController userController;
    private NoticeController noticeController;
    private CoursesController coursesController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.father_list_notice);
        toolBar("Comunicados", R.string.app_name);

        initialize();
        populateList();
    }

    private void initialize() {
        userController = new UserController(this);
        noticeController = new NoticeController(this);
        coursesController = new CoursesController(this);
        listView = (ListView) findViewById(R.id.listView);
    }

    private void populateList() {
        Courses course = coursesController.findLastCourseSelected();
        HashMap paramsInput = userController.wsListNoticeFather(course);
        webServiceTask(Const.ACTIVITY_MODULE_NOTICE_FATHER, ModuleNoticeFatherActivity.this, Const.ROUTE_LIST_NOTICE_FATHER, paramsInput);
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

    private void gotoCoursesByUser() {
        Intent intent = new Intent();
        intent.setClass(ModuleNoticeFatherActivity.this, CoursesByUserActivity.class);
        startActivity(intent);
//        CoursesActivity.this.finish();

        /* Apply our act_1_splash exit (fade out) and menu_reports entry (fade in) animation transitions. */
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    @Override
    public void handleOnResponse(final JSONObject jsonOutput) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                WsResponse response = userController.parseWsResponse(jsonOutput);
                int status = response.getStatus();

                if(status == Const.STATUS_SUCCESS){
                    List<Notice> listObject = noticeController.parseJsonToArrayObject(jsonOutput);

                    if(listObject != null){

                        if(listObject.size() == 0){
                            Utils.longToast(ModuleNoticeFatherActivity.this, "No hay elementos que mostrar");
                        }

                        listView.setAdapter(new NoticeFatherAdapter(ModuleNoticeFatherActivity.this, listObject));

                        // When the user clicks on the ListItem
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                                Object o = listView.getItemAtPosition(position);
                                Notice notice = (Notice) o;

//                                long idInserted = userController.insert(user);
//                                gotoCoursesByUser();
                                Utils.shortToast(ModuleNoticeFatherActivity.this, "Seleccionado: " + notice.getMessageType());
                            }
                        });
                    }
                }else{
                    Utils.shortToast(ModuleNoticeFatherActivity.this, response.getMessage());
                }
            }
        });

    }

}
