package com.engage.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

import com.engage.R;
import com.parse.ParseUser;

public class HomeActivity extends FragmentActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		Button logoutButton = (Button) findViewById(R.id.button1);
		logoutButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				ParseUser.logOut();
				Intent intent = new Intent(HomeActivity.this,
						LoginActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}
}
