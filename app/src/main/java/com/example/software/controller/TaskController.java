package com.example.software.controller;

import android.content.Context;

import com.example.software.entity.Notice;
import com.example.software.entity.Task;
import com.example.software.utils.Const;
import com.example.software.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

public class TaskController extends BaseController {

    private static final String TAG = "TaskController";
    private Context context;
//    private CoursesDaoImplement dao;

    public TaskController(Context context) {
        this.context = context;
//        this.dao = new CoursesDaoImplement(this.context);
    }

    public List<Task> parseJsonToArrayObject(JSONObject jsonOutput) {

        List<Task> listObject = null;

        try {
            if(jsonOutput.has(Const.JSON_KEY_TASK)){
                JSONArray jsonOutput2 = jsonOutput.getJSONArray(Const.JSON_KEY_TASK);
                Gson gson = Utils.gsonBuilder();
                Type listType = new TypeToken<List<Task>>(){}.getType();
                listObject = (List<Task>) gson.fromJson(jsonOutput2.toString(), listType);
            }
        } catch (Exception e) {
            e.getMessage();
        }

        return listObject;
    }

}
