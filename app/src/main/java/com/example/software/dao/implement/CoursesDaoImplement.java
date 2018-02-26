package com.example.software.dao.implement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.software.dao.dbConnection;
import com.example.software.dao.dbTables;
import com.example.software.dao.interfaces.ICourses;
import com.example.software.entity.Courses;
import com.example.software.utils.Const;

import java.util.ArrayList;

public class CoursesDaoImplement extends dbConnection implements ICourses {

    private static String ID_INCREMENT = "id_increment";
    private static String NAME = "name";

    public CoursesDaoImplement(Context context) {
        super(context);
    }

    public long insert(String username, Courses courses) {
        ContentValues values = new ContentValues();
        values.put(dbTables.USERNAME, username);
        values.put(dbTables.T_COURSES_ID, courses.getId());
        values.put(dbTables.T_COURSES_NAME, courses.getName());
        return this.getSqliteDb().insert(dbTables.T_COURSES, null, values);
    }


    public Courses findLastCourseSelected() {
        Courses course = new Courses();
        Cursor cursor = null;

        try {
            cursor = this.getSqliteDb().rawQuery("SELECT * FROM " + dbTables.T_COURSES + " ORDER BY " + dbTables.ID_INCREMENT + " DESC LIMIT 1", null);
            while (cursor.moveToNext()) {
                course.setId(cursor.getInt(cursor.getColumnIndex(dbTables.T_COURSES_ID)));
                course.setName(cursor.getString(cursor.getColumnIndex(dbTables.T_COURSES_NAME)));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return course;
    }


}
