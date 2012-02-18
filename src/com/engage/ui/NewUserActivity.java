package com.engage.ui;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.engage.R;
import com.engage.util.AddPhotoDialogFragment;
import com.engage.util.Preference;
import com.engage.util.Util;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class NewUserActivity extends FragmentActivity {

	private EditText fnameField;
	private EditText lnameField;
	private EditText phoneField;
	private EditText locationField;
	private EditText addressField;
	private EditText zipField;
	private EditText birthdayField;
	private Button cancelButton;
	private Button saveButton;
	private ProgressDialog saveDialog;

	private Context mContext;
	private SharedPreferences prefs;
	private static SharedPreferences.Editor editor;

	private Date birthday;

	private final String MALE = "male";
	private final String FEMALE = "female";
	private String gender;
	private int year;
	private int month;
	private int day;

	private static ParseUser user;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_user);

		mContext = this;
		prefs = getSharedPreferences(Preference.PREFS_KEY, MODE_PRIVATE);
		editor = prefs.edit();
		user = ParseUser.getCurrentUser();

		fnameField = (EditText) findViewById(R.id.new_user_field_fname);
		lnameField = (EditText) findViewById(R.id.new_user_field_lname);
		phoneField = (EditText) findViewById(R.id.new_user_field_phone);
		locationField = (EditText) findViewById(R.id.new_user_field_location);
		addressField = (EditText) findViewById(R.id.new_user_field_address);
		zipField = (EditText) findViewById(R.id.new_user_field_zip);
		birthdayField = (EditText) findViewById(R.id.new_user_field_birthday);
		cancelButton = (Button) findViewById(R.id.new_user_button_cancel);
		saveButton = (Button) findViewById(R.id.new_user_button_save);

		cancelButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(NewUserActivity.this,
						HomeActivity.class);
				startActivity(intent);
			}
		});
		saveButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				saveInfo();
			}
		});
		
		/*
		 * Set the gender to empty initially. This is done in case the user does
		 * not click one, nothing will be saved.
		 */
		gender = "";
		birthdayField.setOnFocusChangeListener(dateFieldListeneder);

		// Set the current date
		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.login, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.menu_choose_photo:
			AddPhotoDialogFragment.showAddPhotoDialog(this);
			return true;
		}
		return false;
	}

	/**
	 * Saves the users information to the server as well as the preferences.
	 */
	private void saveInfo() {
		saveDialog = ProgressDialog.show(mContext, "", "Saving...");

		String fname = fnameField.getText().toString().trim();
		String lname = lnameField.getText().toString().trim();
		String phone = phoneField.getText().toString().trim();
		String location = locationField.getText().toString().trim();
		String address = addressField.getText().toString().trim();
		String zip = zipField.getText().toString().trim();
		String birthdayText = birthdayField.getText().toString().trim();

		if (fname.length() != 0) {
			editor.putString(Preference.FIRST_NAME, fname);
			user.put(Preference.FIRST_NAME, fname);
		}
		if (lname.length() != 0) {
			editor.putString(Preference.LAST_NAME, lname);
			user.put(Preference.LAST_NAME, lname);
		}
		if (phone.length() != 0) {
			editor.putString(Preference.PHONE, phone);
			user.put(Preference.PHONE, phone);
		}
		if (location.length() != 0) {
			editor.putString(Preference.LOCATION, location);
			user.put(Preference.LOCATION, location);
		}
		if (address.length() != 0) {
			editor.putString(Preference.STREET, address);
			user.put(Preference.STREET, location);
		}
		if (zip.length() != 0) {
			editor.putString(Preference.ZIP, zip);
			user.put(Preference.ZIP, zip);
		}
		if (gender.length() != 0) {
			editor.putString(Preference.GENDER, gender);
			user.put(Preference.GENDER, gender);
		}
		// TODO - Save birthday
		if (birthdayText.length() != 0) {
			editor.putString(Preference.STREET, address);
			user.put(Preference.STREET, location);
		}
		user.saveInBackground(new SaveCallback() {
			@Override
			public void done(ParseException e) {
				if (e == null) {
					// Save was successful
					Util.displayToastMessage(mContext,
							R.string.user_save_success);
					editor.commit();
					Intent intent = new Intent(mContext, HomeActivity.class);
					startActivity(intent);
					finish();
				} else {
					Util.displayToastMessage(mContext, R.string.user_save_fail);
				}
				saveDialog.dismiss();
			}
		});
	}

	/**
	 * Sets the text in the birthday EditText to the formatted date.
	 * 
	 * @param date
	 */
	public void setDate(Date date) {
		SimpleDateFormat formater = new SimpleDateFormat("MM/dd/yyyy");
		birthdayField.setText(formater.format(date));
	}

	/**
	 * Listener for when the user clicks on the date field. Brings up the
	 * DatePicker dialog.
	 */
	private View.OnFocusChangeListener dateFieldListeneder = new View.OnFocusChangeListener() {
		public void onFocusChange(View v, boolean hasFocus) {
			if (hasFocus) {
				DialogFragment dialog = DatePickerDialogFragment.newInstance(1);
				dialog.show(getSupportFragmentManager(), "date_picker");
			}
		}
	};

	private static class DatePickerDialogFragment extends DialogFragment {
		int mNum;
		private int year;
		private int month;
		private int day;
		private FragmentActivity activity;

		/**
		 * Create a new instance of DatePickerDialogFragment, providing "num" as
		 * an argument.
		 */
		static DatePickerDialogFragment newInstance(int num) {
			DatePickerDialogFragment f = new DatePickerDialogFragment();
			// Supply num input as an argument.
			Bundle args = new Bundle();
			args.putInt("num", num);
			f.setArguments(args);
			return f;
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			final Calendar c = Calendar.getInstance();
			year = c.get(Calendar.YEAR);
			month = c.get(Calendar.MONTH);
			day = c.get(Calendar.DAY_OF_MONTH);
			activity = getActivity();
		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			DatePickerDialog datePicker = new DatePickerDialog(getActivity(),
					dateSetListener, year, month, day);
			datePicker.setIcon(R.drawable.ic_launcher);
			datePicker.setTitle(R.string.user_set_birthday);
			return datePicker;
		}

		private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.YEAR, year);
				cal.set(Calendar.MONTH, month);
				cal.set(Calendar.DAY_OF_MONTH, day);
				Date date = cal.getTime();
				// SharedPreferences.Editor editor = getActivity()
				// .getSharedPreferences(Preference.DOB, MODE_PRIVATE)
				// .edit();
				editor.putLong(Preference.DOB, date.getTime());
				user.put(Preference.DOB, date);
				((NewUserActivity) getActivity()).setDate(date);
			}
		};
	}

	Uri imageUri = null;
	private static Bitmap thumbnail;

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			if (requestCode == 65537) {
				if (data != null) {
					if (data.hasExtra("data")) {
						// Retrieves the image from the results of the activity
						// and
						// sets the image as a thumbnail at the top of the
						// screen.
						thumbnail = data.getParcelableExtra("data");
						ImageView test = (ImageView) findViewById(R.id.aa_test_image);
						test.setImageBitmap(thumbnail);
						test.setVisibility(View.VISIBLE);
						ParseObject photo = new ParseObject("photo");
						photo.put("userId", user.getObjectId());
						photo.put("url", "http://insert.url.here");
						ByteArrayOutputStream bos = new ByteArrayOutputStream();
						thumbnail.compress(CompressFormat.PNG, 0 /*
																 * ignored for
																 * PNG
																 */, bos);
						byte[] bitmapData = bos.toByteArray();
						photo.put("photoBytes", bitmapData);
						user.put("photoId", 12);
						photo.put("userId", user.getObjectId());
						user.saveInBackground();
						photo.saveInBackground();
					}
				}
			} else if (requestCode == 65536) {
				Uri selectedImage = data.getData();
				try {
					thumbnail = Media.getBitmap(this.getContentResolver(),
							selectedImage);
					ImageView test = (ImageView) findViewById(R.id.aa_test_image);
					test.setVisibility(View.VISIBLE);
					test.setImageBitmap(thumbnail);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
	}
}
