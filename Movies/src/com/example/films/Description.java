package com.example.films;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class Description extends Activity {
	
	TextView tv2, tv4;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.description);
		
		final MoviesDataBase movieDb = new MoviesDataBase(getApplicationContext());
		
		tv2 = (TextView) findViewById(R.id.tv2);
		tv4 = (TextView) findViewById(R.id.tv4);
		
		String movieName = getIntent().getExtras().getString("movieName");
		String des = movieDb.getMovieDescription(movieName);
		
		if(!des.equals(null) && !des.equals(""))
		{
			tv2.setText(movieName);
			tv4.setText(des);
		}
		else
		{
			tv2.setText(movieName);
			tv4.setText(movieName + " has no descritpion!");
		}
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		if(item.getItemId() == android.R.id.home)
		{
			finish();
		}
		
		return super.onOptionsItemSelected(item);
	}

}
