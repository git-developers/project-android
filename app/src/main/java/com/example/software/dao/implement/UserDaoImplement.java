package com.example.software.dao.implement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.software.dao.dbConnection;
import com.example.software.dao.dbTables;
import com.example.software.dao.interfaces.IUser;
import com.example.software.entity.User;
import com.example.software.utils.Const;

import java.util.ArrayList;

public class UserDaoImplement extends dbConnection implements IUser {

    public UserDaoImplement(Context context) {
        super(context);
    }

    public long insert(User user) {


        Log.i("GATO", "GATO:: " + user.getInsertType());




        ContentValues values = new ContentValues();
        values.put(dbTables.USERNAME, user.getUsername());
        values.put(dbTables.T_USER_NAME, user.getName());
        values.put(dbTables.T_USER_EMAIL, user.getEmail());
        values.put(dbTables.T_USER_ROLE, user.getRole());
        values.put(dbTables.T_USER_INSERT_TYPE, user.getInsertType());
//        values.put(dbTables.T_USER_ROLE, user.getProfile().getPermission().getAlias());
//        Integer.valueOf(usuario.getPrivilegios())
        return this.getSqliteDb().insert(dbTables.T_USER, null, values);
    }

    public User findOneById(String id) {
        User user = new User();
        Cursor cursor = null;

        try {
            cursor = this.getSqliteDb().rawQuery("SELECT * FROM " + dbTables.T_USER + " WHERE id_increment=" + id, null);
            while (cursor.moveToNext()) {
                user.setUsername(cursor.getString(cursor.getColumnIndex(dbTables.USERNAME)));
                user.setName(cursor.getString(cursor.getColumnIndex(dbTables.T_USER_NAME)));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return user;
    }

    public User findOneByUsername(String username) {
        User user = new User();
        Cursor cursor = null;

        try {
            cursor = this.getSqliteDb().rawQuery("SELECT * FROM " + dbTables.T_USER + " WHERE " + dbTables.USERNAME + "='" + username + "' LIMIT 1", null);
            while (cursor.moveToNext()) {
                user.setUsername(cursor.getString(cursor.getColumnIndex(dbTables.USERNAME)));
                user.setName(cursor.getString(cursor.getColumnIndex(dbTables.T_USER_NAME)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(dbTables.T_USER_EMAIL)));
                user.setRole(cursor.getString(cursor.getColumnIndex(dbTables.T_USER_ROLE)));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return user;
    }

    public User findLastLogged() {
        User user = new User();
        Cursor cursor = null;

        try {
            cursor = this.getSqliteDb().rawQuery(
                    " SELECT * FROM " + dbTables.T_USER +
                    " WHERE " + dbTables.T_USER_INSERT_TYPE + " = '" + User.INSERT_TYPE_LOGIN + "' " +
                    " ORDER BY " + dbTables.ID_INCREMENT +
                    " DESC LIMIT 1", null);
            while (cursor.moveToNext()) {
                user.setUsername(cursor.getString(cursor.getColumnIndex(dbTables.USERNAME)));
                user.setName(cursor.getString(cursor.getColumnIndex(dbTables.T_USER_NAME)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(dbTables.T_USER_EMAIL)));
                user.setRole(cursor.getString(cursor.getColumnIndex(dbTables.T_USER_ROLE)));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return user;
    }

    public User findLastStudentSelected() {
        User user = new User();
        Cursor cursor = null;

        try {
            cursor = this.getSqliteDb().rawQuery(
                    " SELECT * FROM " + dbTables.T_USER +
                    " WHERE " + dbTables.T_USER_INSERT_TYPE + " = '" + User.INSERT_TYPE_SELECTED_STUDENT + "' " +
                    " ORDER BY " + dbTables.ID_INCREMENT +
                    " DESC LIMIT 1", null);
            while (cursor.moveToNext()) {
                user.setUsername(cursor.getString(cursor.getColumnIndex(dbTables.USERNAME)));
                user.setName(cursor.getString(cursor.getColumnIndex(dbTables.T_USER_NAME)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(dbTables.T_USER_EMAIL)));
                user.setRole(cursor.getString(cursor.getColumnIndex(dbTables.T_USER_ROLE)));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return user;
    }

    public User findLastChildSelected() {
        User user = new User();
        Cursor cursor = null;

        try {
            cursor = this.getSqliteDb().rawQuery(
                    " SELECT * FROM " + dbTables.T_USER +
                    " WHERE " + dbTables.T_USER_INSERT_TYPE + " = '" + User.INSERT_TYPE_CHILDREN + "' " +
                    " ORDER BY " + dbTables.ID_INCREMENT +
                    " DESC LIMIT 1", null);
            while (cursor.moveToNext()) {
                user.setUsername(cursor.getString(cursor.getColumnIndex(dbTables.USERNAME)));
                user.setName(cursor.getString(cursor.getColumnIndex(dbTables.T_USER_NAME)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(dbTables.T_USER_EMAIL)));
                user.setRole(cursor.getString(cursor.getColumnIndex(dbTables.T_USER_ROLE)));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return user;
    }

    public ArrayList<User> findAllByUsername(String username) {
        ArrayList<User> users = new ArrayList();
        return null;
    }

    public void deleteTable() {
        deleteTable(dbTables.T_USER);
    }

}
