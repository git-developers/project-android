package com.example.software.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

public class dbConnection {

    protected Context context;
    protected dbSQLiteHelper dbSQLiteHelper;
    protected SQLiteDatabase sqliteDb;
    protected SQLiteStatement sqliteSt;
    private static String BORRAR_SECUENCIA_TABLA = "DELETE FROM SQLITE_SEQUENCE WHERE NAME = ";

    public static void closeConnection(Context context) {
        new dbConnection(context).closeDb();
    }

    public dbConnection(Context context) {
        this.context = context;
        this.dbSQLiteHelper = new dbSQLiteHelper(this.context, dbTables.DB_NAME, null, dbTables.DB_VERSION);
        this.sqliteDb = this.dbSQLiteHelper.getWritableDatabase();
    }

    public void deleteTable(String tableName) {
        this.sqliteDb.delete(tableName, null, null);
        this.sqliteDb.execSQL(BORRAR_SECUENCIA_TABLA + "'" + tableName + "'");
    }

    public void endTransaction() {
        if (this.sqliteDb != null) {
            this.sqliteDb.endTransaction();
        }
    }

    public void closeDb() {
        if (this.sqliteSt != null) {
            this.sqliteSt.close();
        }
        if (this.sqliteDb != null) {
            this.sqliteDb.close();
        }
    }

    public void validatingConnection() {
        if (this.sqliteDb.isOpen()) {
            this.sqliteDb.close();
        }
    }

    public SQLiteDatabase getSqliteDb() {
        if(!this.sqliteDb.isOpen()){
            this.sqliteDb = this.dbSQLiteHelper.getWritableDatabase();
        }
        return this.sqliteDb;
    }
}
