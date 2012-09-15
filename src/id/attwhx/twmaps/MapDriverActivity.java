package id.attwhx.twmaps;
/*
 * TWOH MAPS
 * id.attwhk.twhmaps
 * twitter.com/twhx
 * twitter.com/twoh
 * twoh.web.id
 * Created By Hafizh Herdi Naufal
 * Access Network Laboratory
 * 
 */


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;


public class MapDriverActivity extends Activity implements OnClickListener {
	private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 1; // dalam Meters
    private static final long MINIMUM_TIME_BETWEEN_UPDATES = 1000; // dalam Milliseconds
    //Instanstiasi Location Manager
    protected LocationManager locationManager;
    //Tombol-tombol pada menu utama
    protected View getLocationBut;
    protected View viewOnMapBut;
    protected View checkInBut;
    protected View aboutBut;
    protected View checkinBut;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        getLocationBut = findViewById(R.id.getLocationButton);
        viewOnMapBut = findViewById(R.id.viewOnMapButton);
        checkInBut = findViewById(R.id.checkInButton);
        aboutBut = findViewById(R.id.aboutButton);
        aboutBut.setOnClickListener(this);
        getLocationBut.setOnClickListener(this);
        viewOnMapBut.setOnClickListener(this);
        checkInBut.setOnClickListener(this);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 
                MINIMUM_TIME_BETWEEN_UPDATES, 
                MINIMUM_DISTANCE_CHANGE_FOR_UPDATES,
                new MyLocationListener()
        );
    }    

    protected void showCurrentLocation() {
    	Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location != null) {
            String message = String.format(
                    "Lokasi saat ini \n Longitude: %1$s \n Latitude: %2$s",
                    location.getLongitude(), location.getLatitude()
            );
            Toast.makeText(MapDriverActivity.this, message,
                    Toast.LENGTH_LONG).show();
            }
    }   

    private class MyLocationListener implements LocationListener {

        public void onLocationChanged(Location location) {
            String message = String.format(
                    "Deteksi Lokasi Baru \n Longitude: %1$s \n Latitude: %2$s",
                    location.getLongitude(), location.getLatitude()
            );
            Toast.makeText(MapDriverActivity.this, message, Toast.LENGTH_LONG).show();
            //switchToMap();
        }

        public void onStatusChanged(String s, int i, Bundle b) {
            Toast.makeText(MapDriverActivity.this, "Status provider berubah",
                    Toast.LENGTH_LONG).show();
        }

        public void onProviderDisabled(String s) {
            Toast.makeText(MapDriverActivity.this,
                    "Provider dinonaktifkan oleh user, GPS off",
                    Toast.LENGTH_LONG).show();
        }

        public void onProviderEnabled(String s) {
            Toast.makeText(MapDriverActivity.this,
                    "Provider diaktifkan oleh user, GPS on",
                    Toast.LENGTH_LONG).show();
        }

    }
	
    public void switchToMap()
    {
    	Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    	Intent i = new Intent(this, TWMaps.class);
    	Bundle b = new Bundle();
    	if(location!=null)
    	{
    	b.putDouble("longitude", location.getLongitude());
    	b.putDouble("latitude", location.getLatitude());
    	Log.v("info", "The lat "+location.getLatitude());
        Log.v("info", "The lng "+location.getLongitude());
        i.putExtras(b);
    	startActivity(i);
    	}else
    	{
    		Toast.makeText(this, "GPS MATI", Toast.LENGTH_LONG).show();
    	}
    	
    }
    
    public void switchToCheckIn()
    {
    	Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    	Intent i = new Intent(this, Checkin.class);
    	Bundle b = new Bundle();
    	if(location!=null)
    	{
    	b.putDouble("longitude", location.getLongitude());
    	b.putDouble("latitude", location.getLatitude());
    	Log.v("info", "The lat "+location.getLatitude());
        Log.v("info", "The lng "+location.getLongitude());
        i.putExtras(b);
    	startActivity(i);
    	}else
    	{
    		Toast.makeText(this, "GPS MATI", Toast.LENGTH_LONG).show();
    	}
    }
    
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
			case R.id.getLocationButton : showCurrentLocation(); 
											break;
			case R.id.viewOnMapButton :
				switchToMap();
				break;
			case R.id.checkInButton : switchToCheckIn();
										break;
			case R.id.aboutButton : Intent i = new Intent(this, About.class);
			startActivity(i);
		}
	}

}
