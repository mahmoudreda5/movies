package com.example.films;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MoviesDataBase extends SQLiteOpenHelper {

	private static final String DB_NAME = "moviesDataBase";
	private static final String TABLE_MOVIES = "movies";
	private static final String MOVIES_TABLE_CREATION =
			"create table " + TABLE_MOVIES + " (_id integer primary key autoincrement , name text not null , description text );";
	
	private static final int DB_VERSION = 1;
	
	SQLiteDatabase db;
	
	public MoviesDataBase(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// TODO Auto-generated method stub
		arg0.execSQL(MOVIES_TABLE_CREATION);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		arg0.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIES);
		onCreate(arg0);
	}
	
	//createNewMovie
	public long createNewMovie(String movieName, String movieDes)
	{
		db = getWritableDatabase();
		ContentValues raw = new ContentValues();
		raw.put("name", movieName);
		raw.put("description", movieDes);
		return db.insert(TABLE_MOVIES, null, raw);
	}
	
	//fetchAllMovies
	public Cursor fetchAllMovies()
	{
		db = getReadableDatabase();
		String [] rawDetails = {"_id", "name", "description"};
		Cursor cursor = db.query(TABLE_MOVIES, rawDetails, null, null, null, null, null);
		if(cursor != null)
		{
			cursor.moveToFirst();
		}
		
		return cursor;
	}
	
	//deleteMovie
	public void deleteMovie(String movieName)
	{
		db = getWritableDatabase();
		db.delete(TABLE_MOVIES, "name='" + movieName + "'", null);
	}
	
	//getMovieDescription
	public String getMovieDescription(String movieName)
	{
		db = getReadableDatabase();
		Cursor movieDes = db.rawQuery("select description from " + TABLE_MOVIES + " where name like'" + movieName + "'", null);
		movieDes.moveToFirst();
		
		return movieDes.getString(0);
	}
	
	//updateMovie
	public void updateMovie(String oldMovieName, String newMovieName, String newMovieDes)
	{
		db = getWritableDatabase();
		ContentValues newMovieData = new ContentValues();
		newMovieData.put("name", newMovieName);
		newMovieData.put("description", newMovieDes);
		db.update(TABLE_MOVIES, newMovieData, "name='" + oldMovieName + "'", null);
	}

}
