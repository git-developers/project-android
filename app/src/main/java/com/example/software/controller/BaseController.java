package com.example.software.controller;

import android.app.Application;

import com.example.software.entity.User;
import com.example.software.entity.WsResponse;
import com.example.software.utils.Utils;
import com.google.gson.Gson;

import org.json.JSONObject;

public class BaseController extends Application {

    public WsResponse parseWsResponse(JSONObject jsonOutput) {

        WsResponse object = null;

        try {
            Gson gson = Utils.gsonBuilder();
            object = (WsResponse) gson.fromJson(jsonOutput.toString(), WsResponse.class);
        } catch (Exception e) {
            e.getMessage();
        }

        return object;
    }

}
