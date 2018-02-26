package com.example.software.dao.implement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.software.dao.dbConnection;
import com.example.software.dao.dbTables;
import com.example.software.dao.interfaces.ISemester;
import com.example.software.entity.Courses;
import com.example.software.entity.Semester;

public class ModulesDaoImplement extends dbConnection implements ISemester {

    private static String ID_INCREMENT = "id_increment";
    private static String NAME = "name";

    public ModulesDaoImplement(Context context) {
        super(context);
    }

    public long insertSemester(String username, Semester semester) {
        ContentValues values = new ContentValues();
        values.put(dbTables.USERNAME, username);
        values.put(dbTables.T_SEMESTER_ID, semester.getId());
        values.put(dbTables.T_SEMESTER_NAME, semester.getName());
        return this.getSqliteDb().insert(dbTables.T_SEMESTER, null, values);
    }

    public Semester findLastSemesterSelected() {
        Semester semester = new Semester();
        Cursor cursor = null;

        try {
            cursor = this.getSqliteDb().rawQuery("SELECT * FROM " + dbTables.T_SEMESTER + " ORDER BY " + dbTables.ID_INCREMENT + " DESC LIMIT 1", null);
            while (cursor.moveToNext()) {
                semester.setId(cursor.getInt(cursor.getColumnIndex(dbTables.T_SEMESTER_ID)));
                semester.setName(cursor.getString(cursor.getColumnIndex(dbTables.T_SEMESTER_NAME)));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return semester;
    }


}
