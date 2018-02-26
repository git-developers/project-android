package com.example.software.controller;

import android.content.Context;

import com.example.software.dao.implement.UserDaoImplement;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserController extends BaseController {

    private static final String TAG = "UserController";
    private Context context;
    private UserDaoImplement dao;

    public UserController(Context context) {
        this.context = context;
        this.dao = new UserDaoImplement(this.context);
    }

    public HashMap wsLoginUser(String username, String password, String registrationId) {
        HashMap<String, String> params = new HashMap<String, String>();
        try {
            params.put(Const.PARAMETER_USERNAME, username);
            params.put(Const.PARAMETER_PASSWORD, password);
            params.put(Const.PARAMETER_REGISTRATION_ID, registrationId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return params;
    }

    public HashMap wsParamUsername(String username) {
        HashMap<String, String> params = new HashMap<String, String>();
        try {
            params.put(Const.PARAMETER_USERNAME, username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return params;
    }

    public HashMap wsListStudentsByCourse(Courses courses) {
        HashMap<String, String> params = new HashMap<String, String>();
        try {
            params.put(Const.PARAMETER_COURSE_ID, String.valueOf(courses.getId()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return params;
    }

    public HashMap wsTeacherListTasksByCourse(Courses courses) {
        HashMap<String, String> params = new HashMap<String, String>();
        try {
            params.put(Const.PARAMETER_COURSE_ID, String.valueOf(courses.getId()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return params;
    }

    public HashMap wsFatherListTasksByCourse(Courses courses, String estado) {
        HashMap<String, String> params = new HashMap<String, String>();
        try {
            params.put(Const.PARAMETER_COURSE_ID, String.valueOf(courses.getId()));
            params.put(Const.PARAMETER_STATUS, estado);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return params;
    }

    public HashMap wsListNoticeFather(Courses courses) {
        HashMap<String, String> params = new HashMap<String, String>();
        try {
            params.put(Const.PARAMETER_COURSE_ID, String.valueOf(courses.getId()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return params;
    }

    public HashMap wsStudentsAttendance(String username, Courses courses, List<User> listUsers) {

        HashMap<String, String> params = new HashMap<String, String>();

        try {
            List<HashMap> listObject = new ArrayList<HashMap>();
            for (User user : listUsers){
                HashMap<String, String> params2 = new HashMap<String, String>();
                params2.put(Const.PARAMETER_STUDENT_USERNAME, "#" + user.getUsername() + "#");
                params2.put(Const.PARAMETER_ATTENDANCE_STATUS, "#" + String.valueOf(user.getAttendanceOrNull()) + "#");
                listObject.add(params2);
            }

            params.put(Const.PARAMETER_USERNAME, username);
            params.put(Const.PARAMETER_COURSE_ID, String.valueOf(courses.getId()));
            params.put(Const.PARAMETER_ATTENDANCE, String.valueOf(listObject));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return params;
    }

    public List<User> parseUsersByCourse(JSONObject jsonOutput) {

        List<User> listObject = null;

        try {
            if(jsonOutput.has(Const.JSON_KEY_USERS_BY_COURSE)){
                JSONArray jsonOutput2 = jsonOutput.getJSONArray(Const.JSON_KEY_USERS_BY_COURSE);
                Gson gson = Utils.gsonBuilder();
                Type listType = new TypeToken<List<User>>(){}.getType();
                listObject = (List<User>) gson.fromJson(jsonOutput2.toString(), listType);
            }
        } catch (Exception e) {
            e.getMessage();
        }

        return listObject;
    }

    public List<User> parseChildrenByFather(JSONObject jsonOutput) {

        List<User> listObject = null;

        try {
            if(jsonOutput.has(Const.JSON_KEY_CHILDREN)){
                JSONArray jsonOutput2 = jsonOutput.getJSONArray(Const.JSON_KEY_CHILDREN);
                Gson gson = Utils.gsonBuilder();
                Type listType = new TypeToken<List<User>>(){}.getType();
                listObject = (List<User>) gson.fromJson(jsonOutput2.toString(), listType);
            }
        } catch (Exception e) {
            e.getMessage();
        }

        return listObject;
    }

    public List<User> parseUsersByCourse(String jsonOutput) {

        List<User> listObject = null;

        try {
            Gson gson = Utils.gsonBuilder();
            Type listType = new TypeToken<List<User>>(){}.getType();
            listObject = (List<User>) gson.fromJson(jsonOutput, listType);
        } catch (Exception e) {
            e.getMessage();
        }

        return listObject;
    }

    public User parseJsonToObject(JSONObject jsonOutput) {

        User object = null;

        try {
            if(jsonOutput.has(Const.JSON_KEY_USER)){
                JSONObject jsonOutput2 = jsonOutput.getJSONObject(Const.JSON_KEY_USER);
                Gson gson = Utils.gsonBuilder();
                object = (User) gson.fromJson(jsonOutput2.toString(), User.class);
            }
        } catch (JSONException e) {
            e.getMessage();
        }

        return object;
    }

    public long insert(User user) {
        long idInserted = dao.insert(user);
        dao.closeDb();
        return idInserted;
    }

    public User findOneByUsername(String username) {
        User user = dao.findOneByUsername(username);
        dao.closeDb();
        return user;
    }

    public User findLastLogged() {
        User user = dao.findLastLogged();
        dao.closeDb();
        return user;
    }

    public User findLastStudentSelected() {
        User user = dao.findLastStudentSelected();
        dao.closeDb();
        return user;
    }

    public User findLastChildSelected() {
        User user = dao.findLastChildSelected();
        dao.closeDb();
        return user;
    }

}
