package com.asa.engage;

import android.app.Activity;
import android.os.Bundle;

public class EngageActivity extends Activity {
	DatabaseAdapter dbAdapter;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
		dbAdapter = new DatabaseAdapter(this);
		dbAdapter.open();
    }
}