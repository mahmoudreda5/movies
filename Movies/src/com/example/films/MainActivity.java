package com.example.films;

import android.support.v7.app.ActionBarActivity;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements DialogInterface.OnDismissListener{

	String MOVIE_NAME;
	MoviesDataBase movieDb;
    ArrayAdapter<String> arr;
	
	EditText ed1, ed2;
	Button b1;
	ListView lv1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		movieDb = new MoviesDataBase(getApplicationContext());
		arr = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1);
		
		ed1 = (EditText) findViewById(R.id.ed1);
		ed2 = (EditText) findViewById(R.id.ed2);
		b1 = (Button) findViewById(R.id.b1);
		lv1 = (ListView) findViewById(R.id.lv1);
		
		Cursor c = movieDb.fetchAllMovies();
		while(!c.isAfterLast())
		{
			String movName = c.getString(1);
			arr.add(movName);
			c.moveToNext();
		}
		
		lv1.setAdapter(arr);
		
		b1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String movieName, movieDes;
				
				if(!(ed1.getText().toString().equals("")))
				{
					if(!(ed2.getText().toString().equals("")))
					{
						movieName = ed1.getText().toString();
						movieDes = ed2.getText().toString();
						
						movieDb.createNewMovie(movieName, movieDes);
						arr.add(movieName);
						lv1.setAdapter(arr);
					}
					else
					{
						movieName = ed1.getText().toString();
						movieDes = "";
						
						movieDb.createNewMovie(movieName, movieDes);
						arr.add(movieName);
						lv1.setAdapter(arr);
					}
					
					Toast.makeText(getApplicationContext(), "Movie Added!", Toast.LENGTH_LONG).show();
					
					ed1.getText().clear();
					ed2.getText().clear();
				}
				else
				{
					Toast.makeText(getApplicationContext(), "enter valid data", Toast.LENGTH_LONG).show();
				}
				
			}
		});
		
		lv1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				String movieName = ((TextView)arg1).getText().toString();
				Intent intent = new Intent(getApplicationContext(), Description.class);
				intent.putExtra("movieName", movieName);
				startActivity(intent);
			}
		});
		
		
		
		lv1.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				
				registerForContextMenu(arg1);
				
				return false;
			}
		});
		
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		switch(item.getItemId())
		{
		case R.id.item1:
			FragmentManager manager = getFragmentManager();
			UpdateDialog myDialog = new UpdateDialog();
			Bundle bundle = new Bundle();
			bundle.putString("MOVIE_NAME", MOVIE_NAME);
			myDialog.setArguments(bundle);
			myDialog.show(manager, "myDialog");
			
			Intent intent = new Intent(getApplicationContext(), UpdateDialog.class);
			intent.putExtra("MOVIE_NAME", MOVIE_NAME);
			
			
			break;
		case R.id.item2:
			movieDb.deleteMovie(MOVIE_NAME);
			Toast.makeText(getApplicationContext(), "Movie Deleted!", Toast.LENGTH_LONG).show();
			
			break;
		}
		
		return super.onContextItemSelected(item);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inf = new MenuInflater(getApplicationContext());
		inf.inflate(R.menu.update_delete, menu);
		
		MOVIE_NAME = ((TextView) v).getText().toString();
		//Toast.makeText(getApplicationContext(), ((TextView) v).getText().toString(), 1).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	
	@Override
	public void onContextMenuClosed(Menu menu) {
		// TODO Auto-generated method stub
		super.onContextMenuClosed(menu);
		super.onResume();
		arr = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1);
		Cursor c = movieDb.fetchAllMovies();
		while(!c.isAfterLast())
		{
			String movName = c.getString(1);
			arr.add(movName);
			c.moveToNext();
		}
		
		lv1.setAdapter(arr);
	}

	@Override
	public void onDismiss(DialogInterface dialog) {
		// TODO Auto-generated method stub
		arr = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1);
		Cursor c = movieDb.fetchAllMovies();
		while(!c.isAfterLast())
		{
			String movName = c.getString(1);
			arr.add(movName);
			c.moveToNext();
		}
		
		lv1.setAdapter(arr);
		
		//Toast.makeText(getApplicationContext(), "called!", 1).show();
	}
	
}
