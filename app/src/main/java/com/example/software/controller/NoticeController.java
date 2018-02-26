package com.example.software.controller;

import android.content.Context;

import com.example.software.dao.implement.CoursesDaoImplement;
import com.example.software.entity.Courses;
import com.example.software.entity.Notice;
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

public class NoticeController extends BaseController {

    private static final String TAG = "NoticeController";
    private Context context;
//    private CoursesDaoImplement dao;

    public NoticeController(Context context) {
        this.context = context;
//        this.dao = new CoursesDaoImplement(this.context);
    }

    public List<Notice> parseJsonToArrayObject(JSONObject jsonOutput) {

        List<Notice> listObject = null;

        try {
            if(jsonOutput.has(Const.JSON_KEY_NOTICES)){
                JSONArray jsonOutput2 = jsonOutput.getJSONArray(Const.JSON_KEY_NOTICES);
                Gson gson = Utils.gsonBuilder();
                Type listType = new TypeToken<List<Notice>>(){}.getType();
                listObject = (List<Notice>) gson.fromJson(jsonOutput2.toString(), listType);
            }
        } catch (Exception e) {
            e.getMessage();
        }

        return listObject;
    }

}
