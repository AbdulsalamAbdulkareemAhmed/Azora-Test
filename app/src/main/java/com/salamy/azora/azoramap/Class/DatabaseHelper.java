package com.salamy.azora.azoramap.Class;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.salamy.azora.azoramap.Model.NewsModel;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "newsDB";
    public static final String TABLE_NAME = "newsTable";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_IMAGE = "image";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_CONTENT = "content";

    private static final int DB_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME
                + "(" + COLUMN_ID +
                " INTEGER PRIMARY KEY , " + COLUMN_NAME +
                " VARCHAR, " + COLUMN_IMAGE +
                " VARCHAR, "+ COLUMN_DATE +
                " VARCHAR, "+ COLUMN_TITLE+
                " VARCHAR, "+ COLUMN_CONTENT+
                " TEXT); ";
        db.execSQL(sql);
    }

    //upgrading the database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS news";
        db.execSQL(sql);
        onCreate(db);
    }
//String name,String image,String date,String title,String content
    public long insertNews(NewsModel model) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_ID, model.getID());
        contentValues.put(COLUMN_NAME, model.getName());
        contentValues.put(COLUMN_IMAGE, model.getImage());
        contentValues.put(COLUMN_DATE, model.getDate());
        contentValues.put(COLUMN_TITLE,model.getTitle());
        contentValues.put(COLUMN_CONTENT, model.getContent());

       return(db.insertOrThrow(TABLE_NAME,null, contentValues));

    }
    public boolean updateNameStatus(int id ,String name, byte[] image,String date,String title,String content) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_IMAGE, image);
        contentValues.put(COLUMN_DATE, date);
        contentValues.put(COLUMN_TITLE, title);
        contentValues.put(COLUMN_CONTENT, content);

        db.update(TABLE_NAME, contentValues, COLUMN_ID + "=" + id, null);
        db.close();
        return true;
    }

    public Cursor getNews() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + COLUMN_ID + " ASC;";
        return db.rawQuery(sql, null);
    }

    public byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 0, stream);
        return stream.toByteArray();
    }
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
}