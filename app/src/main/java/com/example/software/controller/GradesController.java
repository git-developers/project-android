package com.example.software.controller;

import android.content.Context;
import android.util.Log;

import com.example.software.dao.implement.CoursesDaoImplement;
import com.example.software.entity.Courses;
import com.example.software.entity.Grades;
import com.example.software.entity.Semester;
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

public class GradesController extends BaseController {

    private static final String TAG = "GradesController";
    private Context context;
//    private GradesDaoImplement dao;

    public GradesController(Context context) {
        this.context = context;
//        this.dao = new GradesDaoImplement(this.context);
    }

    public HashMap wsListGrades(User user, Courses courses, Semester semester) {

        HashMap<String, String> params = new HashMap<String, String>();
        try {

            Log.i("GATO", "GATO getUsername:: " + user.getUsername());


            params.put(Const.PARAMETER_STUDENT_USERNAME, user.getUsername());
            params.put(Const.PARAMETER_COURSE_ID, String.valueOf(courses.getId()));
            params.put(Const.PARAMETER_BIMESTER, String.valueOf(semester.getId()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return params;
    }

    public List<Grades> parseJsonToArrayObject(JSONObject jsonOutput) {

        List<Grades> listObject = null;

        try {
            if(jsonOutput.has(Const.JSON_KEY_GRADES)){
                JSONArray jsonOutput2 = jsonOutput.getJSONArray(Const.JSON_KEY_GRADES);
                Gson gson = Utils.gsonBuilder();
                Type listType = new TypeToken<List<Grades>>(){}.getType();
                listObject = (List<Grades>) gson.fromJson(jsonOutput2.toString(), listType);
            }
        } catch (Exception e) {
            e.getMessage();
        }

        return listObject;
    }


}
