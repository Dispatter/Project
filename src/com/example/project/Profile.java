package com.example.project;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
 
public class Profile extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		
		TextView name = (TextView) findViewById(R.id.textView1);
		name.setText(User.getInstance().getmUser().getUsername().toString());
		
		final ImageButton photo = (ImageButton) findViewById(R.id.imageButton1);
		
		ParseQuery<ParseUser> query = ParseUser.getQuery();
		query.whereEqualTo("objectId", User.getInstance().getmUser().getObjectId());
		query.findInBackground(new FindCallback<ParseUser>() {
			@Override
			public void done(List<ParseUser> list, ParseException e) {
				if (e == null) {
					for (final ParseUser parseUser : list) {
						ParseFile imageFile = (ParseFile) parseUser.get("photo");
							if (imageFile != null) {
								imageFile.getDataInBackground(new GetDataCallback() {
									public void done(byte[] data, ParseException e) {
										if (e == null) {
											Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
											photo.setImageBitmap(bmp);
										} 
										else {
										}
									}
								});
							}
					}
				}
		}});
	
		photo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), Photo.class));
			}
		});
		
		photo.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				return false;
			}
		});
	}	
}
