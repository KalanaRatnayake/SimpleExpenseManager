package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

/**
 * Created by Kalana on 11/19/2017.
 */

public class PersistentTransactionDAO implements TransactionDAO {
    database databaseHelper;

    public PersistentTransactionDAO(Context context){
        databaseHelper = new database(context);
    }

    @Override
    public void logTransaction(Date date, String accountNo, ExpenseType expenseType, double amount) {
        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        String dateText = new SimpleDateFormat("yyyy-MM-dd").format(date);

        ContentValues values = new ContentValues();
        values.put("accountNo",accountNo);
        values.put("date", dateText);
        values.put("expenseType",expenseType.toString());
        values.put("amount",amount);

        long row = database.insert("Transactions", null, values);
    }

    @Override
    public List<Transaction> getAllTransactionLogs() {
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        String [] column = {"date","accountNo", "expenseType","amount"};
        Cursor cursor = database.query("Transactions", column,
                null,null,null,null,null);

        List transactions = new ArrayList();

        while(cursor.moveToNext()) {

            String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
            String accountNo = cursor.getString(cursor.getColumnIndexOrThrow("accountNo"));
            String expenseType = cursor.getString(cursor.getColumnIndexOrThrow("expenseType"));
            double amount = cursor.getDouble(cursor.getColumnIndexOrThrow("amount"));

            ExpenseType expense = null;

            if(expenseType.equals("EXPENSE")){
                expense = ExpenseType.EXPENSE;
            }
            else{
                expense = ExpenseType.INCOME;
            }

            Date date1= null;
            try {
                date1 = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy").parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Transaction transaction  = new Transaction(date1, accountNo, expense, amount);
            transactions.add(transaction);

        }
        cursor.close();
        return transactions;
    }

    @Override
    public List<Transaction> getPaginatedTransactionLogs(int limit) {
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        long cnt  = DatabaseUtils.queryNumEntries(database, "Transactions");

        if(limit<=cnt){
            return getAllTransactionLogs();
        }
        else{
            String [] column = {"date","accountNo", "expenceType","amount"};
            Cursor cursor = database.query("Transactions", column,
                    null,null,null,null,null);

            List transactions = new ArrayList<>();
            int count = 0;

            while(cursor.moveToNext() && count< limit) {

                String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
                String accountNo = cursor.getString(cursor.getColumnIndexOrThrow("accountNo"));
                String expenseType = cursor.getString(cursor.getColumnIndexOrThrow("expenseType"));
                double amount = cursor.getDouble(cursor.getColumnIndexOrThrow("amount"));

                ExpenseType expense = null;

                if(expenseType.equals("EXPENSE")){
                    expense = ExpenseType.EXPENSE;
                }
                else{
                    expense = ExpenseType.INCOME;
                }

                Date date1= null;
                try {
                    date1 = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy").parse(date);

                } catch (ParseException e) {

                    e.printStackTrace();
                }

                Transaction transaction = new Transaction(date1, accountNo, expense, amount);
                transactions.add(transaction);
                count++;
            }

            cursor.close();
            return transactions;
        }
    }
}
