package com.engage.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.engage.R;
import com.engage.util.Preference;
import com.engage.util.Util;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends Activity {

	private EditText mEmailField;
	private EditText mPasswordField;
	private Button mLoginButton;
	private Button mSignupButton;
	private Button mForgotPasswordButton;

	private ProgressDialog loginProgress;
	private Intent mIntent;
	private Context mContext;
	private SharedPreferences prefs;
	private SharedPreferences.Editor editor;

	private boolean validLogin;

	private final String TAG = "LoginActivity";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		Log.d(TAG, "Entering LoginActivity.");

		mContext = this;
		prefs = getSharedPreferences(Preference.PREFS_KEY, MODE_PRIVATE);
		editor = prefs.edit();

		mEmailField = (EditText) findViewById(R.id.login_field_email);
		mPasswordField = (EditText) findViewById(R.id.login_field_password);
		mLoginButton = (Button) findViewById(R.id.login_button_login);
		mSignupButton = (Button) findViewById(R.id.login_button_signup);

		mLoginButton.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				String emailText = mEmailField.getText().toString()
						.trim();
				String passwordText = mPasswordField.getText().toString()
						.trim();
				validLogin = true;

				// Check if the password has whitespace.
				if (Util.containsWhiteSpace(emailText)) {
					validLogin = false;
					Util.displayToastMessage(mContext,
							R.string.login_invalid_username_whitespace);
				}
				// Check if the email has been entered.
				if (emailText.length() == 0) {
					validLogin = false;
					Util.displayToastMessage(mContext,
							R.string.login_invalid_username_empty);

				}
				// Check if the password has been entered.
				if (passwordText.length() == 0) {
					validLogin = false;
					Util.displayToastMessage(mContext,
							R.string.login_invalid_password_empty);
				}

				if (validLogin) {
					loginProgress = ProgressDialog.show(
							LoginActivity.this,
							"",
							getResources().getString(
									R.string.login_dialog_message), true);
					loginProgress.setIcon(R.drawable.ic_launcher);
					loginUser(emailText, passwordText);
				}
			}
		});

		mSignupButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(mContext, RegisterActivity.class);
				startActivity(intent);
			}
		});
	}

	/**
	 * Logs the user in.
	 * 
	 * @param usernameText
	 * @param passwordText
	 */
	private void loginUser(final String usernameText, String passwordText) {
		ParseUser.logInInBackground(usernameText, passwordText,
				new LogInCallback() {
					@Override
					public void done(ParseUser user, ParseException e) {
						loginProgress.dismiss();
						if (e == null && user != null) {
							editor.putString(Preference.USER_NAME, usernameText);
							// Successful login.
							mIntent = new Intent(LoginActivity.this,
									HomeActivity.class);
							startActivity(mIntent);
							finish();
						} else if (user == null) {
							// Username or password is incorrect.
							Log.d(TAG, "Username is incorrect...");
							Util.displayToastMessage(mContext,
									R.string.login_invalid_incorrect_something);
						} else {
							// Error with connecting server.
							Util.displayToastMessage(mContext,
									R.string.login_invalid_parse_fail);
						}
					}
				});
	}

}
