package com.example.films;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateDialog extends DialogFragment {
	
	MoviesDataBase movDb;
	
	EditText ed3, ed4;
	Button cancel, update;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.update_dialog, null);
		
		ed3 = (EditText) view.findViewById(R.id.ed3);
		ed4 = (EditText) view.findViewById(R.id.ed4);
		cancel = (Button) view.findViewById(R.id.b2);
		update = (Button) view.findViewById(R.id.b3);
		
		movDb = new MoviesDataBase(getActivity());
		final String oldMovName = (String) getArguments().get("MOVIE_NAME");
		
		cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dismiss();
			}
		});
		
		update.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String newMovName, newMovDes;
				if(!(ed3.getText().toString().equals("")))
				{
					if(!(ed4.getText().toString().equals("")))
					{
						newMovName = ed3.getText().toString();
						newMovDes = ed4.getText().toString();
					}
					else
					{
						newMovName = ed3.getText().toString();
						newMovDes = "";
					}
					
					movDb.updateMovie(oldMovName, newMovName, newMovDes);
					dismiss();
					Toast.makeText(getActivity(), "Movie Updated!", Toast.LENGTH_LONG).show();
				}
				else
				{
					Toast.makeText(getActivity(), "enter valid data", Toast.LENGTH_LONG).show();
				}
				
			}
		});
		
		return view;
	}
	
	

}
