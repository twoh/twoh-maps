
package org.example.mymap;


import java.util.List;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class MyMap extends MapActivity {
   private MapView map;
   private MapController controller;

   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.mapdisplay);
      initMapView();
      initMyLocation();
   }

   /** Find and initialize the map view. */
   private void initMapView() {
      map = (MapView) findViewById(R.id.map);
      controller = map.getController();
      map.setSatellite(true);
      map.setBuiltInZoomControls(true);
      
   }

   /** Start tracking the position on the map. */
   private void initMyLocation() {
	  int lat, lng;
	  Bundle b = this.getIntent().getExtras();
      lat = (int)(b.getDouble("latitude")*1E6);
      lng = (int)(b.getDouble("longitude")*1E6);
      Log.v("info", "The lat "+lat);
      Log.v("info", "The lng "+lng);
      
      GeoPoint point = new GeoPoint(lat,lng); 
	  
      List<Overlay> mapOverlays = map.getOverlays();
	  Drawable marker = this.getResources().getDrawable(R.drawable.marker);
	  MapOverlay itemizedOverlay = new MapOverlay(marker, this);   
      
	  OverlayItem overlayitem = new OverlayItem(point, "Lokasi saat ini", "Koordinat ("+b.getDouble("latitude")+","+b.getDouble("longitude"));
      itemizedOverlay.addOverlay(overlayitem);
      controller.setZoom(16);
      controller.animateTo(point);
         
      mapOverlays.add(itemizedOverlay);
   }

   @Override
   protected boolean isRouteDisplayed() {
      // Required by MapActivity
      return false;
   }
}
