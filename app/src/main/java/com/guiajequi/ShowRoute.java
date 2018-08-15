package com.guiajequi;


import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

import framework.GPSTracker;

public class ShowRoute extends Activity {
	WebView webview;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_route);
		
		webview = (WebView)findViewById(R.id.webView1);
		GPSTracker gpsTracker = new GPSTracker(this);
        
        if (gpsTracker.canGetLocation())
        {
        	//Log.d("gps track location vijay", ""+gpsTracker.latitude);
        	//markerPoints.add(new LatLng(gpsTracker.latitude,gpsTracker.longitude));
        	
        	double lat = gpsTracker.latitude;
        	double lon = gpsTracker.longitude;
        	
        	double dlat = getIntent().getExtras().getDouble("latitude");
        	double dlon = getIntent().getExtras().getDouble("longitude");
        	
        	String url = "http://maps.google.com/maps?saddr=@"+dlat+","+dlon+"&daddr=@"+lat+","+lon;
        	webview.loadUrl(url);

        }
	}

}
