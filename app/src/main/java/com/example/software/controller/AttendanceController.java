package com.example.software.controller;

import android.content.Context;

import com.example.software.entity.Attendance;
import com.example.software.entity.Courses;
import com.example.software.entity.User;
import com.example.software.utils.Const;
import com.example.software.utils.Utils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class AttendanceController extends BaseController {

    private static final String TAG = "GradesController";
    private Context context;
//    private GradesDaoImplement dao;

    public AttendanceController(Context context) {
        this.context = context;
//        this.dao = new GradesDaoImplement(this.context);
    }

    public HashMap wsRequestStatus(String dateSelected, User user, Courses courses) {

        HashMap<String, String> params = new HashMap<String, String>();
        try {
            params.put(Const.PARAMETER_STUDENT_USERNAME, user.getUsername());
            params.put(Const.PARAMETER_COURSE_ID, String.valueOf(courses.getId()));
            params.put(Const.PARAMETER_DATE_SELECTED, dateSelected);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return params;
    }

    public Attendance parseJsonToObject(JSONObject jsonOutput) {

        Attendance object = null;

        try {
            if(jsonOutput.has(Const.JSON_KEY_ATTENDANCE_STATUS)){
                JSONObject jsonOutput2 = jsonOutput.getJSONObject(Const.JSON_KEY_ATTENDANCE_STATUS);
                Gson gson = Utils.gsonBuilder();
                object = (Attendance) gson.fromJson(jsonOutput2.toString(), Attendance.class);
            }
        } catch (JSONException e) {
            e.getMessage();
        }

        return object;
    }

}
