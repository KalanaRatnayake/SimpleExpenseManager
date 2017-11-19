package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Kalana on 11/19/2017.
 */

public class database extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "150533H.db";

    public database(Context context){
        super  (context, DATABASE_NAME, null, DATABASE_VERSION);
        //pass the arguments to the SQLiteOpenHelper to create the database.
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL("CREATE TABLE Accounts (accountNo TEXT PRIMARY KEY, bankName TEXT, accountHolderName TEXT, balance REAL)");
        sqLiteDatabase.execSQL("CREATE TABLE Transactions (date TEXT, accountNo TEXT, expenceType TEXT, amount REAL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion){
        return;
    }

}
