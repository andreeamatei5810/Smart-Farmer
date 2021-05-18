package com.example.mds;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {


    public DBHelper(Context context) {
        super(context, "Project3.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table Client(email TEXT primary key, " +
                "password TEXT, username TEXT, phoneNumber TEXT, role TEXT, preferences TEXT, privacy TEXT)");
        DB.execSQL("create Table Product(name TEXT primary key, " +
                "price INT,description TEXT)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Client");
    }

    public boolean insertClient(String email, String password, String username,String phoneNumber,String role)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password", password);
        contentValues.put("username", username);
        contentValues.put("phoneNumber",phoneNumber);
        contentValues.put("role",role);
        contentValues.put("preferences","");
        contentValues.put("privacy","public");
        long result=DB.insert("Client", null, contentValues);
        return result != -1;
    }

    public Cursor getClient(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        System.out.println(email);
        return db.rawQuery("select * from Client where email =?",new String[] {email});
    }

    public Boolean updateUserData(String text){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("string",text);
        Cursor cursor = db.rawQuery("select * from Data where string = ?",new String[] {text});
        if(cursor.getCount()>0) {
            long result = db.update("Data", contentValues,"string =?",new String[] {text});
            cursor.close();
            return result != -1;
        }
        else{
            cursor.close();
            return false;
        }
    }

    public Boolean deleteUserData(String text){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from Data where string = ?",new String[] {text});
        if(cursor.getCount()>0) {
            long result = db.delete("Data","string =?",new String[] {text});
            cursor.close();
            return result != -1;
        }
        else{
            cursor.close();
            return false;
        }
    }

    public void deleteUsers(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from client");
    }

    public Cursor getUsers(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select * from Client",null);
    }

    public Boolean editUserProfile(String oldEmail, String email, String password, String username, String phoneNumber, String preferences,
                                   String privacy) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password", password);
        contentValues.put("username", username);
        contentValues.put("phoneNumber", phoneNumber);
        contentValues.put("preferences", preferences);
        contentValues.put("privacy", privacy);
        Cursor cursor = DB.rawQuery("Select * from Client where email = ?", new String[]{oldEmail});
        if (cursor.getCount() > 0) {
            long result = DB.update("Client", contentValues, "email=?", new String[]{oldEmail});
            cursor.close();
            return result != -1;
        } else {
            cursor.close();
            return false;
        }}

    public ArrayList<String> getClientInfo(String email) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursorP = DB.rawQuery("Select password from Client where email = ?", new String[]{email});
        Cursor cursorU = DB.rawQuery("Select username from Client where email = ?", new String[]{email});
        Cursor cursorA = DB.rawQuery("Select phoneNumber from Client where email = ?", new String[]{email});
        Cursor cursorPr = DB.rawQuery("Select preferences from Client where email = ?", new String[]{email});
        Cursor cursorPy = DB.rawQuery("Select privacy from Client where email = ?", new String[]{email});
        ArrayList<String> str = new ArrayList<>();
        str.add(email);
        if (cursorP.getCount() > 0 && cursorU.getCount() > 0 && cursorA.getCount() > 0 &&
                cursorPr.getCount() > 0 && cursorPy.getCount() > 0) {
            cursorP.moveToFirst();
            int index = cursorP.getColumnIndex("password");
            String password = cursorP.getString(index);
            str.add(password);
            cursorU.moveToFirst();
            index = cursorU.getColumnIndex("username");
            String username = cursorU.getString(index);
            str.add(username);
            cursorA.moveToFirst();
            index = cursorA.getColumnIndex("phoneNumber");
            String phoneNumber = cursorA.getString(index);
            str.add(String.valueOf(phoneNumber));
            cursorPr.moveToFirst();
            index = cursorPr.getColumnIndex("preferences");
            String preferences = cursorPr.getString(index);
            str.add(preferences);
            cursorPy.moveToFirst();
            index = cursorPy.getColumnIndex("privacy");
            String privacy = cursorPy.getString(index);
            str.add(privacy);
        }

        cursorP.close();
        cursorU.close();
        cursorA.close();
        cursorPr.close();
        cursorPy.close();
        return str;
    }

    public Boolean deleteClient (String email)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Client where email = ?", new String[]{email});
        if (cursor.getCount() > 0) {
            long result = DB.delete("Client", "email=?", new String[]{email});
            cursor.close();
            return result != -1;
        } else {
            cursor.close();
            return false;
        }

    }

    public boolean insertProduct(String name, int price, String description){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",name);
        contentValues.put("price",price);
        contentValues.put("description",description);
        long productadded = DB.insert("Product",null,contentValues);
        if(productadded == -1){
            return false;
        }
        else{
            return true;
        }




    }

    public Cursor getProducts(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from Product",null);
        return cursor;
    }

}
