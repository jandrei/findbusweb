package br.com.senac.findbus.view;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import br.com.senac.findbus.Mensagens;
import br.com.senac.findbus.R;
import br.com.senac.findbus.dao.StopDAO;
import br.com.senac.findbus.infra.Utils;
import br.com.senac.findbus.model.StopED;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapaParadas extends FragmentActivity implements OnMarkerClickListener {

	List<MarkerUtil<StopED>> markers = new ArrayList<MarkerUtil<StopED>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//
		// diz qual xml devera ser aberto para essa tela
		setContentView(R.layout.mapa);

		try {
			// pega informação se a lib do playservices esta disponivel
			int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());

			// caso esteja disponível
			if (status == ConnectionResult.SUCCESS) {
				// pega o objeto da tela que é nosso mapa
				GoogleMap map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
				// habilita encontrar a minha localização
				map.setMyLocationEnabled(true);
				map.setOnMarkerClickListener(this);

				// se essa tela for chamada da lista de stops pega o ed enviado
				// por parametro
				StopED ed = (StopED) getIntent().getSerializableExtra("stopED");

				// se encontrou o parametro mostra a parada na tela.
				if (ed != null) {

					LatLng latLng = new LatLng(ed.getStopLat(), ed.getStopLon());
					configuraPosicao(map, latLng, ed);

				} else {
					// se a tela for chamada dos botoes principais ela vem sem o
					// parametro ed

					// pego a instancia da stopdao
					StopDAO dao = StopDAO.getInstance(getBaseContext());
					List<StopED> stops = null;

					// forma de descobrir qual a minha localização
					LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
					Location myLocation = locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
					if (myLocation == null){
						return;
					}
					//teste
					// método listar proximo retorna as stops perto de mim
					// método lastknowlocation retorna a posição baseando-se
					// pela rede de celular.
					// existe or gps e outras.
					stops = dao.listarProximos(myLocation);
					LatLng ltn = null;

					// marco no mapa as paradas próximas a mim
					for (StopED stopED : stops) {
						ltn = new LatLng(stopED.getStopLat(), stopED.getStopLon());

						configuraPosicao(map, ltn, stopED);
					}

					// se encontrou uma parada pelo menos, move a camera com
					// zoom até a localização da primeira parada econtrada
					if (stops.size() > 0) {
						Utils.setMyLocation(this, map);
					}
				}
			} else {
				// se o playservices nao estiver habilitado o google mostra uma
				// dialog de erro
				int requestCode = 10;
				Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
				dialog.show();
			}
			// captura as exceptions não tratadas
		} catch (Exception e) {
			Mensagens.ExibeExceptionAlert(MapaParadas.this, e);
		}
	}

	@Override
	public boolean onMarkerClick(Marker marker) {
//		MarkerUtil<StopED> markerUtilIndexOf = new MarkerUtil<StopED>(new StopED(), marker, null);
//		if (!markers.contains(markerUtilIndexOf)) {
//			return false;
//		}
//
//		MarkerUtil<StopED> markerValue = markers.get(markers.indexOf(markerUtilIndexOf));
//		Mensagens.ExibeMensagemToast(getApplicationContext(), markerValue.getEd().getStopName());

		return false;
	}

	// adiciona marcador para uma longitude latitude.
	private void configuraPosicao(GoogleMap map, LatLng latLng, StopED ed) {
		MarkerOptions markerOptions = new MarkerOptions()
		.position(new LatLng(ed.getStopLat(), ed.getStopLon()))
		.title(ed.getStopName()).icon(BitmapDescriptorFactory.fromResource(R.drawable.bus));

		Marker markerAux = map.addMarker(markerOptions);

		markers.add(new MarkerUtil<StopED>(ed, markerAux, markerOptions));

		map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
		// map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17.0f));
	}
}
