package com.sychu.thunderchaser;

import java.util.Locale;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
//import android.view.WindowManager;
import android.widget.TextView;

public class ThunderChaserActivity extends Activity implements IOnChronometerTick  {
    
    TextView infoText = null;
    TextView distanceText = null;
    TextView counteText = null;
    
    CustomChronometer chrono = null;
    
    static boolean measuring = false;
    static long baseTime = 0;
    static long elapsedTime = 0;
    static int temperature = 18;
    static boolean displayMiles = false;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.main);
    }
    
    
    
    @Override
    public void onDestroy() {
    	if(chrono != null)
    		chrono.KillMe();
    	
    	super.onDestroy();
    }
    
    @Override
    public void onStart() {
    	setConfig();
        InitApp();
    	super.onStart();
    }
    
    @Override
    public void onStop() {
    	if(chrono != null)
    		chrono.KillMe();
    	
    	super.onStop();
    }
    
    public void InitApp() {
        infoText = (TextView)findViewById(R.id.infoView);
        distanceText = (TextView)findViewById(R.id.distanceTextView);
        counteText = (TextView)findViewById(R.id.counterTextView);
        
        chrono = new CustomChronometer(0, 50, baseTime, elapsedTime);
        chrono.setChronometerTickEvent(this);
        if(measuring) {
        	chrono.cont();
        	infoText.setText(R.string.tap_bolt_hear);
        }
        else
        	refreshViewData(elapsedTime-baseTime);
        
        
        
    }
    
    
    public double getDistance(long delta)
    {
    	double v = 331.5 * Math.sqrt(1+temperature/273.15);
    	double distance = v*delta/1000000;
    	return distance;
    }
    
    //1 mile = 1,609344 km
    private static final double kmPerMile = 1.609344;
    
    public double convertToMiles(double km) {
    	return km/kmPerMile;
    }

    
    public String getCounterString(long delta)
    {
		double time = ((double)delta) / 1000;
		
		return String.format(Locale.US, "%.2f s",time);
    }
    
    public String getDistanceString(long delta)
    {
		double distance =  getDistance(delta);
		if(displayMiles) {
			distance = convertToMiles(distance);
			return String.format(Locale.US ,"%.3f %s", distance, getResources().getString(R.string.mile));
		}
		else
			return String.format(Locale.US ,"%.3f %s", distance, getResources().getString(R.string.km));
    }
    
    public void onBoltClick(View v) {
    	if(measuring)
    	{
    		chrono.stop();
    		infoText.setText(R.string.tap_bolt_see);
    	}
    	else
    	{
    		chrono.start();
    		baseTime = chrono.getBaseTime();
    		infoText.setText(R.string.tap_bolt_hear);
    	}
    	measuring=!measuring;
    }
    

    public void refreshViewData(long delta)
    {
    	counteText.setText(getCounterString(delta));
		distanceText.setText(getDistanceString(delta));
    }
    
	public void OnChronometerTick(CustomChronometer chronometer) {
		elapsedTime = chronometer.getElapsedTime();
		refreshViewData(elapsedTime-baseTime);
	}

	
	//==================== Menu =======================
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main_menu, menu);
	    return true;
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	    case R.id.config:
	    	Intent configActivity = new Intent(getBaseContext(),
                    ThunderChaserConfig.class);
	    	startActivity(configActivity);
	        return true;
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}
	
	
	//================= Config ========================
	private void setConfig() {
	       // Get the xml/preferences.xml preferences
        SharedPreferences prefs = PreferenceManager
                        .getDefaultSharedPreferences(this);
        String tempStr = prefs.getString("temperature", "20");
        
        try {
        	temperature = Integer.parseInt(tempStr);
        } catch(Exception e) {
        	temperature = 20;
        }
        
        String kmString = getResources().getString( R.string.km);
        if( prefs.getString("distanceUnit", kmString).equals(kmString))
        	displayMiles = false;
        else
        	displayMiles = true;

	}
}