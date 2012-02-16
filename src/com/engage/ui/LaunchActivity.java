package com.engage.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.parse.Parse;
import com.parse.ParseUser;
import com.parse.PushService;

public class LaunchActivity extends FragmentActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Initialize Parse.
		Parse.initialize(this, "hgwDhFh0FfUb43fiCDA72zsiC3w4IpJeGs2cknQU",
				"NOk2stqZ9Ga3tIeeGoYkvga6PnneKt6aTrREJ8Un");
		// Subscribe to push service
		PushService.subscribe(this, "", LaunchActivity.class);

		if (ParseUser.getCurrentUser() != null) {
			// The user is logged in.
			Intent intent = new Intent(this, NewUserActivity.class);
			startActivity(intent);
		} else {
			// The user is not logged in. - Start Login Fragment
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
		}
		finish();
	}

}
