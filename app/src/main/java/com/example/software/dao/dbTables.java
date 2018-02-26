package com.example.software.dao;

import com.example.software.utils.Const;

public class dbTables {
    public static String DB_NAME = "project.db";
    public static int DB_VERSION = Const.VERSION;
    public static String ID_INCREMENT = "id_increment";
    public static String CREATED_AT = "created_at";
    public static String USERNAME = "username";

    public static String T_USER = "t_user";
    public static String T_USER_NAME = "name";
    public static String T_USER_EMAIL = "email";
    public static String T_USER_ROLE = "role";
    public static String T_USER_INSERT_TYPE = "insert_type";

    public static String T_COURSES = "t_courses";
    public static String T_COURSES_ID = "id_course";
    public static String T_COURSES_NAME = "name";

    public static String T_SEMESTER = "t_semester";
    public static String T_SEMESTER_ID = "id_semester";
    public static String T_SEMESTER_NAME = "name";

    public static String CREATE_T_USER =
            "CREATE TABLE " + T_USER + "(" +
            ID_INCREMENT + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            T_USER_NAME + " VARCHAR(50)," +
            T_USER_EMAIL + " VARCHAR(200)," +
            USERNAME + " VARCHAR(50)," +
            T_USER_ROLE + " VARCHAR(50)," +
            T_USER_INSERT_TYPE + " VARCHAR(50)," +
            CREATED_AT + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";

    public static String CREATE_T_COURSES =
            "CREATE TABLE " + T_COURSES + "(" +
            ID_INCREMENT + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            USERNAME + " VARCHAR(50)," +
            T_COURSES_ID + " INTEGER(11)," +
            T_COURSES_NAME + " VARCHAR(50)," +
            CREATED_AT + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";

    public static String CREATE_T_SEMESTER =
            "CREATE TABLE " + T_SEMESTER + "(" +
            ID_INCREMENT + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            USERNAME + " VARCHAR(50)," +
            T_SEMESTER_ID + " INTEGER(11)," +
            T_SEMESTER_NAME + " VARCHAR(50)," +
            CREATED_AT + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";


}
