package id.attwhx.twmaps;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBDataSource {

  // Database fields
  private SQLiteDatabase database;
  private DBMapsHelper dbHelper;
  private String[] allColumns = { DBMapsHelper.COLUMN_ID,
      DBMapsHelper.COLUMN_LAT, DBMapsHelper.COLUMN_LONG };

  public DBDataSource(Context context) {
    dbHelper = new DBMapsHelper(context);
  }

  public void open() throws SQLException {
    database = dbHelper.getWritableDatabase();
  }

  public void close() {
    dbHelper.close();
  }

  public DBLokasi createLokasi(Double dlat, Double dlng) {
    ContentValues values = new ContentValues();
    String lat = Double.toString(dlat);
    String lng = Double.toString(dlng);
    values.put(DBMapsHelper.COLUMN_LAT, lat);
    values.put(DBMapsHelper.COLUMN_LONG, lng);
    long insertId = database.insert(DBMapsHelper.TABLE_NAME, null,
        values);
    Cursor cursor = database.query(DBMapsHelper.TABLE_NAME,
        allColumns, DBMapsHelper.COLUMN_ID + " = " + insertId, null,
        null, null, null);
    cursor.moveToFirst();
    DBLokasi newLokasi = cursorToLokasi(cursor);
    cursor.close();
    Log.v("info", "The lat "+lat+", "+dlat);
    Log.v("info", "The lng "+lng+", "+dlng);
    return newLokasi;
  }

  public void deleteLokasi(DBLokasi lokasi) {
    long id = lokasi.getId();
    System.out.println("Comment deleted with id: " + id);
    database.delete(DBMapsHelper.TABLE_NAME, DBMapsHelper.COLUMN_ID
        + " = " + id, null);
  }

  public List<DBLokasi> getAllLokasi() {
    List<DBLokasi> daftarLokasi = new ArrayList<DBLokasi>();

    Cursor cursor = database.query(DBMapsHelper.TABLE_NAME,
        allColumns, null, null, null, null, null);

    cursor.moveToFirst();
    while (!cursor.isAfterLast()) {
      DBLokasi lokasi = cursorToLokasi(cursor);
      daftarLokasi.add(lokasi);
      cursor.moveToNext();
    }
    // Make sure to close the cursor
    cursor.close();
    return daftarLokasi;
  }

  private DBLokasi cursorToLokasi(Cursor cursor) {
	  
	  DBLokasi lokasi = new DBLokasi();
	  Log.v("info", "The getLONG "+cursor.getLong(0));
      Log.v("info", "The setLatLng "+cursor.getString(1)+","+cursor.getString(2));
	  lokasi.setId(cursor.getLong(0));
	  lokasi.setLat(cursor.getString(1));
	  lokasi.setLng(cursor.getString(2));
	  return lokasi;
  }
} 