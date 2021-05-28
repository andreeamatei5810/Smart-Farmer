package com.example.mds;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.mds.model.Product;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.TreeSet;

public class DBHelper extends SQLiteOpenHelper {

    private ByteArrayOutputStream objectByteArrayOutputStream;
    private byte[] imageInByte;
    Context context;

    public DBHelper(Context context) {
        super(context, "DatabaseTest3.db", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table User(email TEXT primary key, " +
                "password TEXT, username TEXT, phoneNumber TEXT, role TEXT)");
        DB.execSQL("create Table ChatMessage(idMessage INTEGER primary key AUTOINCREMENT, textMessage TEXT, " +
                "timeMessage TEXT, emailSender TEXT, emailReceiver TEXT, readMessage TEXT)");
        DB.execSQL("create table Product(idProduct INTEGER primary key AUTOINCREMENT,farmerId TEXT,productName TEXT" + ",image BLOB,productPrice int, productDescription TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists User");
        db.execSQL("drop table if exists Product");
        db.execSQL("drop table if exists ChatMessage");

    }

    public boolean insertUser(String email, String password, String username, String phoneNumber, String role) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password", password);
        contentValues.put("username", username);
        contentValues.put("phoneNumber", phoneNumber);
        contentValues.put("role", role);
        long result = DB.insert("User", null, contentValues);
        return result != -1;
    }

    public Cursor getUser(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        System.out.println(email);
        return db.rawQuery("select * from User where email =?", new String[]{email});
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
        db.execSQL("delete from User");
    }

    public Cursor getUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select * from User", null);
    }

    public void editUserProfile(String email, String newPassword, String newUsername, String newPhoneNumber) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password", newPassword);
        contentValues.put("username", newUsername);
        contentValues.put("phoneNumber", newPhoneNumber);
        Cursor cursor = DB.rawQuery("Select * from User where email = ?", new String[]{email});
        if (cursor.getCount() > 0) {
            long result = DB.update("User", contentValues, "email=?", new String[]{email});
        }
        cursor.close();
    }

    public ArrayList<String> getUserInfo(String email) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursorP = DB.rawQuery("Select password from User where email = ?", new String[]{email});
        Cursor cursorU = DB.rawQuery("Select username from User where email = ?", new String[]{email});
        Cursor cursorA = DB.rawQuery("Select phoneNumber from User where email = ?", new String[]{email});
        ArrayList<String> str = new ArrayList<>();
        str.add(email);
        if (cursorP.getCount() > 0 && cursorU.getCount() > 0 && cursorA.getCount() > 0) {
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
        }

        cursorP.close();
        cursorU.close();
        cursorA.close();
        return str;
    }

    public void deleteUser(String email) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from User where email = ?", new String[]{email});
        if (cursor.getCount() > 0) {
            long result = DB.delete("User", "email=?", new String[]{email});
        }
        cursor.close();

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
        try (Cursor cursor = DB.rawQuery("Select * from ChatMessage where (emailSender = ? and emailReceiver =?) or (emailSender = ? and emailReceiver =?)", new String[]{emailReceiver, emailSender, emailSender, emailReceiver})) {
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

    public void readAllMessages(String email) {
        SQLiteDatabase DB = this.getWritableDatabase();
        DB.execSQL("UPDATE ChatMessage SET readMessage = ? WHERE emailReceiver =? ", new String[]{String.valueOf(Boolean.TRUE), email});
    }

    public int checkReadMessages(String emailSender, String emailCheck) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from ChatMessage where emailSender = ? and emailReceiver =? and readMessage =?", new String[]{emailSender, emailCheck, String.valueOf(Boolean.FALSE)});
        if (cursor.getCount() > 0) {
            cursor.close();
            return 1;
        } else {
            cursor.close();
            return 0;
        }
    }

    public void addProduct(Product objectProductClass) {
        try {
            SQLiteDatabase objectSqlLiteDatabase = this.getWritableDatabase();
            Bitmap imageToStoreBitmap = objectProductClass.getImage();

            objectByteArrayOutputStream = new ByteArrayOutputStream();
            imageToStoreBitmap.compress(Bitmap.CompressFormat.JPEG, 100, objectByteArrayOutputStream);
            imageInByte = objectByteArrayOutputStream.toByteArray();

            ContentValues objectContentValues = new ContentValues();


            objectContentValues.put("farmerId",objectProductClass.getFarmerId());
            objectContentValues.put("productName", objectProductClass.getProdName());
            objectContentValues.put("image", imageInByte);
            objectContentValues.put("productPrice", objectProductClass.getProdPrice());
            objectContentValues.put("productDescription", objectProductClass.getProdDescription());

            long checkQueryRuns = objectSqlLiteDatabase.insert("Product", null, objectContentValues);
            if (checkQueryRuns != -1) {
                Toast.makeText(context, "Your product has been successfully added to the database!", Toast.LENGTH_SHORT).show();
                objectSqlLiteDatabase.close();

            } else {
                Toast.makeText(context, "Did not work", Toast.LENGTH_SHORT).show();
            }


        } catch (Exception e) {
            Toast.makeText(context,e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }

    public ArrayList<Product> getAllProductsData(){
        try{
            SQLiteDatabase SQLDatabase = this.getReadableDatabase();
            ArrayList<Product> objectModelClassList = new ArrayList<>();

            Cursor objectCursor = SQLDatabase.rawQuery("select * from Product",null);
            if(objectCursor.getCount()!=-1){
                while(objectCursor.moveToNext()){
                    int prodId = objectCursor.getInt(0);
                    String productFarmer = objectCursor.getString(1);
                    String productName = objectCursor.getString(2);
                    int productPrice = Integer.parseInt(objectCursor.getString(4));
                    String productDescription = objectCursor.getString(5);

                    byte[] imageBytes = objectCursor.getBlob(3);

                    Bitmap objectBitmap = BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.length);

                    objectModelClassList.add(new Product(productFarmer,prodId,productName,productPrice,productDescription,objectBitmap));
                }
                return objectModelClassList;
            }
            else{
                Toast.makeText(context,"There are no products in database!",Toast.LENGTH_SHORT).show();
                return null;
            }
        }
        catch(Exception e){
            return null;
        }
    }

    public Product getProductById(int id){
        try{
            SQLiteDatabase SQLDatabase = this.getReadableDatabase();
            Cursor objectCursor = SQLDatabase.rawQuery("select * from Product where idProduct=?", new String[]{String.valueOf(id)});
            if(objectCursor.getCount()!=-1 && objectCursor.moveToNext()){
                int prodId = objectCursor.getInt(0);
                String productFarmer = objectCursor.getString(1);
                String productName = objectCursor.getString(2);
                int productPrice = Integer.parseInt(objectCursor.getString(4));
                String productDescription = objectCursor.getString(5);
                byte[] imageBytes = objectCursor.getBlob(3);
                Bitmap objectBitmap = BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.length);
                return new Product(productFarmer,prodId,productName,productPrice,productDescription,objectBitmap);
            }
            else{
                Toast.makeText(context,"There are no products in database!",Toast.LENGTH_SHORT).show();
                return null;
            }
        }
        catch(Exception e){
            return null;
        }
    }

    public void deleteProduct(int id) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("select * from Product where idProduct=?", new String[]{String.valueOf(id)});
        if (cursor.getCount() > 0) {
            long result = DB.delete("Product", "idProduct=?", new String[]{String.valueOf(id)});
        }
        cursor.close();

    }

    public void editProduct(Product objectProductClass, int id) {
        try {
            SQLiteDatabase objectSqlLiteDatabase = this.getWritableDatabase();
            Bitmap imageToStoreBitmap = objectProductClass.getImage();

            objectByteArrayOutputStream = new ByteArrayOutputStream();
            imageToStoreBitmap.compress(Bitmap.CompressFormat.JPEG, 100, objectByteArrayOutputStream);
            imageInByte = objectByteArrayOutputStream.toByteArray();

            ContentValues objectContentValues = new ContentValues();


            objectContentValues.put("farmerId",objectProductClass.getFarmerId());
            objectContentValues.put("productName", objectProductClass.getProdName());
            objectContentValues.put("image", imageInByte);
            objectContentValues.put("productPrice", objectProductClass.getProdPrice());
            objectContentValues.put("productDescription", objectProductClass.getProdDescription());

            long checkQueryRuns = objectSqlLiteDatabase.update("Product", objectContentValues,"idProduct = ?", new String[]{String.valueOf(id)});
            if (checkQueryRuns != -1) {
                Toast.makeText(context, "Your product has been successfully updated!", Toast.LENGTH_SHORT).show();
                objectSqlLiteDatabase.close();

            } else {
                Toast.makeText(context, "Did not work", Toast.LENGTH_SHORT).show();
            }


        } catch (Exception e) {
            Toast.makeText(context,e.getMessage(), Toast.LENGTH_SHORT).show();

        }


    }
}