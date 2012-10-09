
package id.attwhx.twmaps;


import java.util.ArrayList;
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

public class TWMaps extends MapActivity {
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
	  if(b.containsKey("longitude"))
	  {
		  
	      lat = (int)(b.getDouble("latitude")*1E6);
	      lng = (int)(b.getDouble("longitude")*1E6);
	      Log.v("info", "The lat "+lat);
	      Log.v("infoMAPS", "MASUK KE SATU");
	      Log.v("info", "The lng "+lng);
	      GeoPoint point = new GeoPoint(lat,lng);
	      List<Overlay> mapOverlays = map.getOverlays();
		  Drawable marker = this.getResources().getDrawable(R.drawable.marker);
		  MapOverlay itemizedOverlay = new MapOverlay(marker, this);   
	      
		  OverlayItem overlayitem = new OverlayItem(point, "Info Lokasi", "Koordinat ("+b.getDouble("latitude")+","+b.getDouble("longitude")+")");
	      itemizedOverlay.addOverlay(overlayitem);
	      controller.setZoom(16);
	      controller.animateTo(point);
	      mapOverlays.add(itemizedOverlay);
	      
	  }else
	  {
		  Log.v("infoMAPS", "MASUK KE DUA");
		  List<Overlay> mapOverlays = map.getOverlays();
		  Drawable marker = this.getResources().getDrawable(R.drawable.marker);
		  MapOverlay itemizedOverlay = new MapOverlay(marker, this);
		  ArrayList<DBLokasi> daftarLokasi = b.getParcelableArrayList("daftar");
		  for(int i=0;i<daftarLokasi.size();i++)
		  {
			  Log.v("infoMAPS", "Marker ke "+i);
			  DBLokasi a = daftarLokasi.get(i);
			  lat = (int)(Double.valueOf(a.getLat())*1E6);
		      lng = (int)(Double.valueOf(a.getLng())*1E6);
		      GeoPoint point = new GeoPoint(lat,lng);
		      OverlayItem overlayitem = new OverlayItem(point, "Info Lokasi", "Koordinat ke "+i+" ("+a.getLat()+","+a.getLng()+")");
		      itemizedOverlay.addOverlay(overlayitem);
		      controller.setZoom(16);
		      controller.animateTo(point);
		      mapOverlays.add(itemizedOverlay);
		  }
	  }
       
	  
      
         
      
   }

   @Override
   protected boolean isRouteDisplayed() {
      // Required by MapActivity
      return false;
   }
}
