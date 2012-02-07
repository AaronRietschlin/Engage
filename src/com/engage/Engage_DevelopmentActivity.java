package com.engage;

import android.app.Activity;
import android.os.Bundle;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.PushService;
import com.parse.SignUpCallback;

public class Engage_DevelopmentActivity extends Activity {

	public static String USER_PHONE = "phone";
	public static String USER_FIRST_NAME = "firstName";
	public static String USER_LAST_NAME = "lastName";
	public static String USER_PHOTO_ID = "photoId";
	public static String USER_HOMETOWN = "hometown";
	public static String USER_GENDER = "gender";
	public static String USER_INTERESTS = "interests";
	public static String USER_REPUTATION = "reputation";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Parse.initialize(this, "hgwDhFh0FfUb43fiCDA72zsiC3w4IpJeGs2cknQU",
				"NOk2stqZ9Ga3tIeeGoYkvga6PnneKt6aTrREJ8Un");
		PushService.subscribe(this, "", Engage_DevelopmentActivity.class);

		ParseUser user = new ParseUser();
		user.setUsername("TestName1");
		user.setPassword("test");
		user.setEmail("test@test.com");
		user.put(USER_PHONE, "650-253-0000");
		user.put(USER_FIRST_NAME, "Testy");
		user.put(USER_LAST_NAME, "McTesterson");
		user.put(USER_PHOTO_ID, 0);
		user.put(USER_HOMETOWN, "Testsville");
		user.put(USER_GENDER, "male");
		user.put(USER_INTERESTS, "{\"id1\":0,\"id2\":2}");
		user.put(USER_REPUTATION, 3);

		user.signUpInBackground(new SignUpCallback() {
			public void done(ParseException e) {
				if (e == null) {
					// Hooray! Let them use the app now.
				} else {
					// Sign up didn't succeed. Look at the ParseException
					// to figure out what went wrong
				}
			}
		});

		ParseObject attractionDetail = new ParseObject("attraction_detail");
		attractionDetail.put("attractionDetailId", 0);
		attractionDetail
				.put("traveler_comment",
						"{\"userId\":0,\"commentId\":0,\"upside\":\"Traveler Upside.\",\"downside\":\"Downside\"}");
		attractionDetail
				.put("local_upside",
						"{\"userId\":0,\"commentId\":0,\"upside\":\"Local Upside.\",\"downside\":\"Local Downside\"}");
		attractionDetail.put("address", "123 Test St");
		attractionDetail.put("state", "Ohio");
		attractionDetail.put("city", "TestsVille");
		attractionDetail.put("upvotes", 2);
		attractionDetail.put("downvotes", 0);

		ParseObject path = new ParseObject("path");
		path.put("pathId", 0);
		path.put("userId", 2);
		path.put("attractions", "{\"id1\":0,\"id2\":2}");
		
		attractionDetail.saveInBackground();
		path.saveInBackground();
	}
}