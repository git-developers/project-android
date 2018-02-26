package com.example.software.controller;

import android.content.Context;

import com.example.software.dao.implement.CoursesDaoImplement;
import com.example.software.entity.Courses;
import com.example.software.entity.User;
import com.example.software.utils.Const;
import com.example.software.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class CoursesController extends BaseController {

    private static final String TAG = "CoursesController";
    private Context context;
    private CoursesDaoImplement dao;

    public CoursesController(Context context) {
        this.context = context;
        this.dao = new CoursesDaoImplement(this.context);
    }

    public HashMap wsListCourses(String username) {

        HashMap<String, String> params = new HashMap<String, String>();
        try {
            params.put(Const.PARAMETER_USERNAME, username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return params;
    }

    public List<Courses> parseJsonToArrayObject(JSONObject jsonOutput) {

        List<Courses> listObject = null;

        try {
            if(jsonOutput.has(Const.JSON_KEY_COURSES)){
                JSONArray jsonOutput2 = jsonOutput.getJSONArray(Const.JSON_KEY_COURSES);
                Gson gson = Utils.gsonBuilder();
                Type listType = new TypeToken<List<Courses>>(){}.getType();
                listObject = (List<Courses>) gson.fromJson(jsonOutput2.toString(), listType);
            }
        } catch (Exception e) {
            e.getMessage();
        }

        return listObject;
    }

    public Courses parseJsonToObject(JSONObject jsonOutput) {

        Courses object = null;

        try {
            if(jsonOutput.has(Const.JSON_KEY_COURSES)){
                JSONObject jsonOutput2 = jsonOutput.getJSONObject(Const.JSON_KEY_COURSES);
                Gson gson = Utils.gsonBuilder();
                object = (Courses) gson.fromJson(jsonOutput2.toString(), Courses.class);
            }
        } catch (JSONException e) {
            e.getMessage();
        }

        return object;
    }

    public long insert(String username, Courses courses) {
        long idInserted = dao.insert(username, courses);
        dao.closeDb();
        return idInserted;
    }

    public Courses findLastCourseSelected() {
        Courses course = dao.findLastCourseSelected();
        dao.closeDb();
        return course;
    }

}
