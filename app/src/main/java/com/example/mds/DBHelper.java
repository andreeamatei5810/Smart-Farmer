package com.example.mds;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import androidx.annotation.RequiresApi;
import java.util.ArrayList;
import java.util.TreeSet;

public class DBHelper extends SQLiteOpenHelper {


    public DBHelper(Context context) {
        super(context, "Database.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table Client(email TEXT primary key, " +
                "password TEXT, username TEXT, phoneNumber TEXT, role TEXT, preferences TEXT, privacy TEXT)");
        DB.execSQL("create Table Product(name TEXT primary key, " +
                "price INT,description TEXT)");
        DB.execSQL("create Table ChatMessage(idMessage INTEGER primary key AUTOINCREMENT, textMessage TEXT, " +
                "timeMessage TEXT, emailSender TEXT, emailReceiver TEXT, readMessage TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Client");
        db.execSQL("drop table if exists Product");
        db.execSQL("drop table if exists ChatMessage");

    }

    public boolean insertClient(String email, String password, String username, String phoneNumber, String role) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password", password);
        contentValues.put("username", username);
        contentValues.put("phoneNumber", phoneNumber);
        contentValues.put("role", role);
        contentValues.put("preferences", "");
        contentValues.put("privacy", "public");
        long result = DB.insert("Client", null, contentValues);
        return result != -1;
    }

    public Cursor getClient(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        System.out.println(email);
        return db.rawQuery("select * from Client where email =?", new String[]{email});
    }

    public Boolean updateUserData(String text) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("string", text);
        Cursor cursor = db.rawQuery("select * from Data where string = ?", new String[]{text});
        if (cursor.getCount() > 0) {
            long result = db.update("Data", contentValues, "string =?", new String[]{text});
            cursor.close();
            return result != -1;
        } else {
            cursor.close();
            return false;
        }
    }

    public Boolean deleteUserData(String text) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from Data where string = ?", new String[]{text});
        if (cursor.getCount() > 0) {
            long result = db.delete("Data", "string =?", new String[]{text});
            cursor.close();
            return result != -1;
        } else {
            cursor.close();
            return false;
        }
    }

    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from client");
    }

    public Cursor getUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select * from Client", null);
    }

    public void editUserProfile(String oldEmail, String email, String password, String username, String phoneNumber, String preferences,
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
        }
        cursor.close();
    }

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

    public void deleteClient(String email) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Client where email = ?", new String[]{email});
        if (cursor.getCount() > 0) {
            long result = DB.delete("Client", "email=?", new String[]{email});
        }
        cursor.close();

    }

    public boolean insertProduct(String name, int price, String description) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("price", price);
        contentValues.put("description", description);
        long productAdded = DB.insert("Product", null, contentValues);
        return productAdded != -1;

    }

    public Cursor getProducts() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select * from Product", null);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void insertMessage(String textMessage, String emailSender, String emailReceiver) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("textMessage", textMessage);
        contentValues.put("timeMessage", String.valueOf(java.time.LocalDateTime.now()));
        contentValues.put("emailSender", emailSender);
        contentValues.put("emailReceiver", emailReceiver);
        contentValues.put("readMessage", String.valueOf(Boolean.FALSE));
        long result = DB.insert("ChatMessage", null, contentValues);
    }

    public ArrayList<String> getContacts(String email) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ArrayList<String> emails = new ArrayList<>();
        try (Cursor cursorSend = DB.rawQuery("Select emailReceiver from ChatMessage where emailSender = ?", new String[]{email})) {
            while (cursorSend.moveToNext()) {
                int index = cursorSend.getColumnIndex("emailReceiver");
                String emailReceiver = cursorSend.getString(index);
                emails.add(emailReceiver);
            }
        }
        try (Cursor cursorSend = DB.rawQuery("Select emailSender from ChatMessage where emailReceiver = ?", new String[]{email})) {
            while (cursorSend.moveToNext()) {
                int index = cursorSend.getColumnIndex("emailSender");
                String emailSender = cursorSend.getString(index);
                emails.add(emailSender);
            }
        }
        TreeSet<String> setEmails = new TreeSet<>(emails);
        emails.clear();
        emails.addAll(setEmails);
        return emails;
    }

    public ArrayList<String> getMessages(String emailSender, String emailReceiver) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ArrayList<String> messages = new ArrayList<>();
        try (Cursor cursor = DB.rawQuery("Select * from ChatMessage where (emailSender = ? and emailReceiver =?) or (emailSender = ? and emailReceiver =?)", new String[]{emailReceiver,emailSender,emailSender,emailReceiver})) {
            while (cursor.moveToNext()) {
                int index = cursor.getColumnIndex("textMessage");
                String currentMessage = cursor.getString(index) + "///";
                index = cursor.getColumnIndex("timeMessage");
                String timeMessage = cursor.getString(index);
                currentMessage += timeMessage + "///";
                index = cursor.getColumnIndex("emailSender");
                String email = cursor.getString(index);
                currentMessage += email;
                messages.add(currentMessage);
            }
        }
        return messages;
    }

    public void readAllMessages(String email){
        SQLiteDatabase DB = this.getWritableDatabase();
        DB.execSQL("UPDATE ChatMessage SET readMessage = ? WHERE emailReceiver =? " ,new String[]{String.valueOf(Boolean.TRUE),email});
    }

    public int checkReadMessages(String emailSender, String emailCheck){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from ChatMessage where emailSender = ? and emailReceiver =? and readMessage =?", new String[]{emailSender,emailCheck,String.valueOf(Boolean.FALSE)});
        if(cursor.getCount()>0){
            cursor.close();
            return 1;
        }
        else {
            cursor.close();
            return 0;
        }
    }


}