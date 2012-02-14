package com.engage.ui;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.engage.R;
import com.engage.util.Preference;
import com.engage.util.Util;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class RegisterActivity extends Activity {

	private EditText emailField;
	// private EditText usernameField;
	private EditText passwordField;
	private EditText confirmField;
	private Button registerButton;
	private Button cancelButton;

	private Context mContext;
	private SharedPreferences prefs;
	private SharedPreferences.Editor editor;
	private ProgressDialog registrationProgress;

	private boolean validRegistration;

	public static final int REG_USERNAME_TAKEN = 202;
	public static final int REG_EMAIL_TAKEN = 203;
	public static final int REG_NO_CONNECTION = 110;
	public static final int REG_EMPTY = 0;
	public static final int REG_INVALID = 1;
	public static final int REG_IN_TABLE = 2;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		if (Build.VERSION.SDK_INT > 11) {
			ActionBar actionBar = getActionBar();
			// actionBar.

		} else {

		}

		mContext = this;
		prefs = getSharedPreferences(Preference.PREFS_KEY, MODE_PRIVATE);
		editor = prefs.edit();

		emailField = (EditText) findViewById(R.id.register_field_email);
		passwordField = (EditText) findViewById(R.id.register_field_password);
		confirmField = (EditText) findViewById(R.id.register_field_confirm);
		registerButton = (Button) findViewById(R.id.register_button_signup);
		cancelButton = (Button) findViewById(R.id.register_button_cancel);

		cancelButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});

		registerButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String emailText = emailField.getText().toString().trim();
				// String usernameText =
				// usernameField.getText().toString().trim();
				String passwordText = passwordField.getText().toString().trim();
				String confirmText = confirmField.getText().toString().trim();
				validRegistration = true;

				// Check if email has whitespace
				if (Util.containsWhiteSpace(emailText) && validRegistration) {
					validRegistration = false;
					Util.displayToastMessage(mContext, R.string.invalid_email);
				}

				// Check if username has whitespace
				// if (Util.containsWhiteSpace(usernameText) &&
				// validRegistration) {
				// validRegistration = false;
				// Util.displayToastMessage(mContext,
				// R.string.invalid_username_whitespace);
				// }

				// Check if email is entered
				if (emailText.length() == 0 && validRegistration) {
					validRegistration = false;
					Util.displayToastMessage(mContext,
							R.string.invalid_email_none_entered);
				}

				// Check if username is entered
				// if (usernameText.length() == 0 && validRegistration) {
				// validRegistration = false;
				// Util.displayToastMessage(mContext,
				// R.string.invalid_username_none_entered);
				// }

				// Check if password is entered
				if (passwordText.length() == 0 && validRegistration) {
					validRegistration = false;
					Util.displayToastMessage(mContext,
							R.string.invalid_password_none_entered);
				}

				// Check if confirmation password is entered
				if (confirmText.length() == 0 && validRegistration) {
					validRegistration = false;
					Util.displayToastMessage(mContext,
							R.string.invalid_password_none_entered);
				}

				// Check if passwords match
				if (!passwordText.equals(confirmText)) {
					validRegistration = false;
					Util.displayToastMessage(mContext,
							R.string.register_invalid_matching_passwords);
				}

				// Check if email is a valid email
				if (!Util.isValidEmail(emailText)) {
					validRegistration = false;
					Util.displayToastMessage(mContext, R.string.invalid_email);
				}

				if (validRegistration) {
					registrationProgress = ProgressDialog.show(
							mContext,
							"",
							getResources().getString(
									R.string.register_dialog_message), true);
					registerUser(emailText, passwordText);
				}
			}
		});

		// TODO testing
		emailField.setText("test1@test.com");
		passwordField.setText("test");
		confirmField.setText("test");
	}

	private void registerUser(final String email, String password) {
		final ParseUser newUser = new ParseUser();
		newUser.setPassword(password);
		newUser.setUsername(email);
		newUser.setEmail(email);

		newUser.signUpInBackground(new SignUpCallback() {
			@Override
			public void done(ParseException e) {
				registrationProgress.dismiss();
				if (e == null) {
					/*
					 * Successful signup Put registered information in the users
					 * SharedPreferences.
					 */
					editor.putString(Preference.EMAIL, email);
					editor.putString(Preference.PREFS_SESSIONID,
							newUser.getSessionToken());
					editor.commit();
					validRegistration = true;

					Intent intent = new Intent(mContext, NewUserActivity.class);
					startActivity(intent);
					finish();
				} else {
					if (e.getCode() == REG_EMAIL_TAKEN) {
						Util.displayToastMessage(mContext,
								R.string.invalid_email_taken);
					} else if (e.getCode() == REG_USERNAME_TAKEN) {
						Util.displayToastMessage(mContext,
								R.string.invalid_username_taken);
					} else if (e.getCode() == REG_NO_CONNECTION) {
						Util.displayToastMessage(mContext,
								R.string.login_invalid_parse_fail);
					}
				}
			}
		});
	}
}