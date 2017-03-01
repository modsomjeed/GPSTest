package th.ac.pim.gps;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.os.Bundle;
import android.app.Activity;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.LocationManager;
import android.location.LocationListener;
import android.location.Location;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends Activity implements LocationListener {

	private TextView responseData;
	private LocationManager locationManager;
	private Location locationTmp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		responseData = (TextView) findViewById(R.id.response);
		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		
		/*
		 * Indicates the desired accuracy for latitude and longitude. 
		 * Accuracy may be ACCURACY_FINE if desired location is fine, 
		 * else it can be ACCURACY_COARSE. 
		 * More accurate location may consume more power and may take longer.
		 */
		Criteria criteria = new Criteria();
	    criteria.setAccuracy(Criteria.ACCURACY_COARSE);
	    
	    String provider = locationManager.getBestProvider( criteria, true );
	    
		try {

			// getting GPS status
			boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

			// getting network status
			boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

			if (!isGPSEnabled && !isNetworkEnabled) {
			// no network provider is enabled
				responseData.append("[No network provider is enabled]" + "\n");
				Log.d("Network", "Network Enabled");
			} else {
				if (isNetworkEnabled) {
					responseData.append("[Network Enabled]"+provider + "\n");
					Log.d("Network", "Network Enabled");

				}
				// if GPS Enabled get lat/long using GPS Services
				if (isGPSEnabled) {
					responseData.append("[GPS Enabled]"+provider + "\n");
					Log.d("GPS", "GPS Enabled");

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000, 1, this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		// This needs to stop getting the location data and save the battery power
		locationManager.removeUpdates(this);
	}

	@Override
	public void onLocationChanged(Location loc) {
		
		String address = "";
			
		if (loc == null)
			responseData.append("*** Can not get your location***" + "\n");
		else {
			
			responseData.append("Current Location\n");
			responseData.append("Latitude: " + loc.getLatitude() + "\n");
			responseData.append("Longitude: " + loc.getLongitude() + "\n");
			responseData.append("--------------------\n");
			
		}
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
	}

}
