package com.achievers.data.source.categories;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;

import com.achievers.entities.Category;

@Dao
public interface CategoryDao {

    @Query("SELECT COUNT(*) FROM " + Category.TABLE_NAME)
    int count();

    @Insert
    long insert(Category category);

    @Insert
    long[] insertAll(Category[] categories);

    @Query("SELECT * FROM " + Category.TABLE_NAME)
    Cursor selectAll();

    @Query("SELECT * FROM " + Category.TABLE_NAME + " WHERE " + Category.COLUMN_ID + " = :id")
    Cursor selectById(long id);

    @Query("DELETE FROM " + Category.TABLE_NAME + " WHERE " + Category.COLUMN_ID + " = :id")
    int deleteById(long id);

    @Update
    int update(Category category);
}