package br.com.senac.findbus.infra;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

public class Utils {
	
	public static String formatData(Calendar calendar,String formato){
		return new SimpleDateFormat(formato).format(calendar.getTime());
	}
	
	public static String formatData(Calendar calendar){
		return formatData(calendar,"dd/MM/yyyy HH:mm:ss");
	}

	public static void setMyLocation(Context ctx,GoogleMap map){
		try {
			LocationManager locManager = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
			Location location = locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

			map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15.0f));
		} catch (Exception e2) {
		}
	}
}
