package bounty.android.example.com.bounty;

/**
 * Created by RamizMehran on 25/10/2015.
 */
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;

import java.util.HashMap;

public class MyDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "budgetDB.db";
    private static final String TABLE_INCOME = "income";
    private static final String TABLE_EXPENSE = "expense";
    private static final String TABLE_TOTAL = "total";
    private static final String TABLE_SMS = "sms";

    public static final String COLUMN_SOURCE = "src";
    public static final String COLUMN_AMOUNT = "amt";
    public static final String COLUMN_REPEAT = "isForOnce";
    public static final String COLUMN_INCOME = "income";
    public static final String COLUMN_EXPENSE = "expense";


    public MyDBHandler(Context context, String name,
                       SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_INCOME_TABLE = "CREATE TABLE " +
                TABLE_INCOME + "("
                + COLUMN_SOURCE + " TEXT," + COLUMN_AMOUNT
                + " DECIMAL(5,2) " + ")";
        db.execSQL(CREATE_INCOME_TABLE);

        String CREATE_EXPENSE_TABLE = "CREATE TABLE " +
                TABLE_EXPENSE + "("
                + COLUMN_SOURCE + " TEXT," + COLUMN_AMOUNT
                + " DECIMAL(5,2)," + COLUMN_REPEAT + " BOOLEAN " + ")";
        db.execSQL(CREATE_EXPENSE_TABLE);

        String CREATE_TOTAL_TABLE = "CREATE TABLE " +
                TABLE_TOTAL + "("
                + COLUMN_INCOME + " DECIMAL(5,2)," + COLUMN_EXPENSE
                + " DECIMAL(5,2)" + ")";
        db.execSQL(CREATE_TOTAL_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INCOME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TOTAL);
        onCreate(db);
    }

    public void addIncome(Income product) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_SOURCE, product.getSrc());
        values.put(COLUMN_AMOUNT, product.getAmt());

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE_INCOME, null, values);
        db.close();
    }

    public void addExpense(Expense product) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_SOURCE, product.getReason());
        values.put(COLUMN_AMOUNT, product.getAmt());
        values.put(COLUMN_REPEAT, product.isForOnce());

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE_EXPENSE, null, values);
        db.close();
    }

    public void addTotal(Total_ product) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_SOURCE, product.getIncome());
        values.put(COLUMN_AMOUNT, product.getExpense());

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE_TOTAL, null, values);
        db.close();
    }

    public HashMap<String, Double> findIncome() {
        String query = "Select * FROM " + TABLE_INCOME;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        Income product = new Income();

        HashMap<String, Double> incomeData = new HashMap<String, Double>();
        //Iterating through all the rows
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            incomeData.put(cursor.getString(0),Double.parseDouble(cursor.getString(1)));
            while(cursor.moveToNext()){
                incomeData.put(cursor.getString(0),Double.parseDouble(cursor.getString(1)));
            }
        }

        db.close();
        return incomeData;
    }
    public HashMap<String, Double> findExpense() {
        String query = "Select * FROM " + TABLE_EXPENSE;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        Expense product = new Expense();

        HashMap<String, Double> expenseData = new HashMap<String, Double>();
        //Iterating through all the rows
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            expenseData.put(cursor.getString(0),Double.parseDouble(cursor.getString(1)));
            while(cursor.moveToNext()){
                expenseData.put(cursor.getString(0),Double.parseDouble(cursor.getString(1)));
            }
        }

        db.close();
        return expenseData;
    }

    public Double[] findTotal() {
        String query = "Select * FROM " + TABLE_TOTAL;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        Double[] totalData = new Double[2];
        //Iterating through all the rows
        if (cursor.moveToLast()) {
            totalData[0] = Double.parseDouble(cursor.getString(0));
            totalData[1] = Double.parseDouble(cursor.getString(1));
        }

        db.close();
        return totalData;
    }

    public boolean deleteIncome(String productname) {

        boolean result = false;

        String query = "Select * FROM " + TABLE_INCOME + " WHERE " + COLUMN_SOURCE + " =  \"" + productname + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        Income product = new Income();

        if (cursor.moveToFirst()) {
            product.setSrc(cursor.getString(0));
            db.delete(TABLE_INCOME, COLUMN_SOURCE + " = ?",
                    new String[] { String.valueOf(product.getSrc()) });
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }

    public boolean deleteExpense(String productname) {

        boolean result = false;

        String query = "Select * FROM " + TABLE_EXPENSE + " WHERE " + COLUMN_SOURCE + " =  \"" + productname + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        Income product = new Income();

        if (cursor.moveToFirst()) {
            product.setSrc(cursor.getString(0));
            db.delete(TABLE_EXPENSE, COLUMN_SOURCE + " = ?",
                    new String[] { String.valueOf(product.getSrc()) });
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }
}