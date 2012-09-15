package id.attwhx.twmaps;



import java.util.List;


import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.maps.MapActivity;


@SuppressWarnings("unused")
public class Checkin extends ListActivity implements OnClickListener{

	private TextView textView;
	private DBDataSource datasource;
	private View checkinbut;
	private Double lat,lng;
	@Override
	public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.checkin);
      checkinbut = findViewById(R.id.checkinbt);
      checkinbut.setOnClickListener(this);
      textView =  (TextView) findViewById(R.id.textcheckin);
      Bundle b = this.getIntent().getExtras();
      lat = b.getDouble("latitude");
      lng = b.getDouble("longitude");
      textView.setText("Ingin check-in di koordinat berikut ? "+lat+","+lng);
      
      datasource = new DBDataSource(this);
      datasource.open();
      
      List<DBLokasi> values = datasource.getAllLokasi();
      
      ArrayAdapter<DBLokasi> adapter = new ArrayAdapter<DBLokasi>(this,
    	        android.R.layout.simple_list_item_1, values);
      setListAdapter(adapter);
	}
	
	public void onClick(View v) {
		@SuppressWarnings("unchecked")
		ArrayAdapter<DBLokasi> adapter = (ArrayAdapter<DBLokasi>) getListAdapter();
		DBLokasi lokasi = null;
		switch (v.getId())
		{
			case R.id.checkinbt:
				lokasi = datasource.createLokasi(lat,lng);
				if(lokasi !=null&&adapter!=null)
				{
					adapter.add(lokasi);
				}else
				{
					Toast.makeText(this, "NULL", Toast.LENGTH_LONG).show();
				}
				break;
		}
		adapter.notifyDataSetChanged();
	}

	@Override
	  protected void onResume() {
	    datasource.open();
	    super.onResume();
	  }

	  @Override
	  protected void onPause() {
	    datasource.close();
	    super.onPause();
	  }
	
}
