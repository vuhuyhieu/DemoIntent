package com.example.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.model.Book;

import java.util.ArrayList;

public class DatabaseBook extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "BookManagement";
    private static final String COL_ID = "ID";
    private static final String COL_NAME = "Name";
    private static final String COL_DAY_PUBLISH = "DayPublish";
    private static final String COL_TYPE = "Type";

    public DatabaseBook(@Nullable Context context) {
        super(context, TABLE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlStatement = "create table " + TABLE_NAME + " ( " +
                COL_ID + " integer primary key autoincrement, " +
                COL_NAME + " text, " +
                COL_DAY_PUBLISH + " integer, " +
                COL_TYPE + " text)";
        sqLiteDatabase.execSQL(sqlStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sqlStatement = "drop table if exists " + TABLE_NAME;
        sqLiteDatabase.execSQL(sqlStatement);
        onCreate(sqLiteDatabase);
    }

    public void addNewBook(Book book) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAME, book.getName());
        values.put(COL_DAY_PUBLISH, book.getDayPublish());
        String type = book.isNovel() ? "Tieu Thuyet" : "Giao Trinh";
        values.put(COL_TYPE, type);
        database.insert(TABLE_NAME, null, values);
        database.close();
    }

    public void deleteBook(Book book) {
        SQLiteDatabase database = getWritableDatabase();
        database.delete(TABLE_NAME, COL_ID + " = ? ", new String[]{String.valueOf(book.getId())});
        database.close();
    }

    public int getBookCount() {
        SQLiteDatabase database = getReadableDatabase();
        String query = "select * from " + TABLE_NAME;
        Cursor cursor = database.rawQuery(query, null);
        return cursor.getCount();
    }

    public ArrayList<Book> getListBook() {
        ArrayList<Book> result = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        String query = "select * from " + TABLE_NAME;
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                int dayPublish = cursor.getInt(2);
                String typeRaw = cursor.getString(3);
                boolean type = typeRaw.equals("Tieu Thuyet") ? true : false;
                result.add(new Book(id, name, dayPublish, type));
            } while (cursor.moveToNext());
        }
        return result;
    }

    public void addDemoTenBooks() {
        ArrayList<Book> listBook = new ArrayList<>();
        listBook.add(new Book("Harry Potter và bảo bối tử thần", 2012, true));
        listBook.add(new Book("Cấu trúc dữ liệu và giải thuật", 1995, false));
        listBook.add(new Book("Tâm lý học đám đông", 2000, true));
        listBook.add(new Book("Lập trình C", 2012, false));
        listBook.add(new Book("Những người khốn khổ", 1995, true));
        listBook.add(new Book("Cử thái luận hành vi", 2000, false));
        listBook.add(new Book("Các triều đại nhà Nguyễn", 2012, true));
        listBook.add(new Book("Tiếng chim hót trong bụi mận gai", 1995, true));
        listBook.add(new Book("Cơ sở dữ liệu", 2000, false));
        listBook.add(new Book("Lập trình hướng đối tượng", 1995, false));
        for (int i = 0; i < listBook.size(); i++) {
            addNewBook(listBook.get(i));
        }
    }

    public void updateBook(Book book){
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAME, book.getName());
        values.put(COL_DAY_PUBLISH, book.getDayPublish());
        String type = book.isNovel() ? "Tieu Thuyet" : "Giao Trinh";
        values.put(COL_TYPE, type);
        database.update(TABLE_NAME, values, COL_ID+" =? ", new String[]{String.valueOf(book.getId())});
        database.close();
    }
}
