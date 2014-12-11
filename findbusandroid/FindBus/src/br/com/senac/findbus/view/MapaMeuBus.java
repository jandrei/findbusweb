package br.com.senac.findbus.view;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import br.com.senac.findbus.Constantes;
import br.com.senac.findbus.Mensagens;
import br.com.senac.findbus.R;
import br.com.senac.findbus.SynchronousHttpConnection;
import br.com.senac.findbus.infra.EnviaMinhaLocalizacaoRunnable;
import br.com.senac.findbus.infra.Utils;
import br.com.senac.findbus.model.Historico;
import br.com.senac.findbus.model.RouteED;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

public class MapaMeuBus extends FragmentActivity implements OnMarkerClickListener, OnMapClickListener {
	private GoogleMap map;

	private RouteED route;

	private MarkerOptions marker;

	EnviaMinhaLocalizacaoRunnable enviar;
	
	Map<String,Historico> mapaHistorico;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapa);

		try {
			enviar = EnviaMinhaLocalizacaoRunnable.getInstance(this);
			if (!enviar.onibusEmAndamento() && getIntent().getSerializableExtra("cadeOnibus") == null) {
				this.finish();
			}

			// pega informação se a lib do playservices esta disponivel
			int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());

			// caso esteja disponível
			if (status == ConnectionResult.SUCCESS) {
				// pega o objeto da tela que é nosso mapa
				map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
				// habilita encontrar a minha localização
				map.setMyLocationEnabled(true);
				map.setOnMarkerClickListener(this);
				map.setOnMapClickListener(this);

				if (getIntent().getSerializableExtra("ed") == null) {
					return;
				}
				route = (RouteED) getIntent().getSerializableExtra("ed");
				getIntent().removeExtra("ed");
				atualizaPonto();

				if ("sim".equals(getIntent().getSerializableExtra("abrirPergunta"))) {

					Mensagens.EfetuaPergunta(this, "Sair?", "Sair do Ônibus?", new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							enviar.sairDoBus();

							getIntent().removeExtra("abrirPergunta");

							NotificationManager manager = (NotificationManager) MapaMeuBus.this.getSystemService(Context.NOTIFICATION_SERVICE);
							manager.cancelAll();

							Mensagens.ExibeMensagemAlert(MapaMeuBus.this, "Descida ocorreu com sucesso!");
							MapaMeuBus.this.finish();
						}
					}, new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub

						}
					});
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
			Mensagens.ExibeExceptionAlert(MapaMeuBus.this, e);
		}
	}

	@Override
	public void onMapClick(LatLng arg0) {
		atualizaPonto();
	}

	private void atualizaPonto() {
		try {
			getIntent().removeExtra("cadeOnibus");

//			String json = new SynchronousHttpConnection().get(Constantes.urlAmazon + Constantes.pegaUltimoHistoricoPorStop + route.getRouteId());
//			Historico historico = new Gson().fromJson(json, Historico.class);
			
			String json = new SynchronousHttpConnection().get(Constantes.urlAmazon + Constantes.pegaHistoricoRoutes + route.getRouteId());
			Historico[] lista = new Gson().fromJson(json, Historico[].class);
						

			if (lista == null || lista.length == 0) {
				Mensagens.ExibeMensagemToast(this, "Ninguém informou a localização desse ônibus até agora!");
				finish();
				return;
			}
			
			mapaHistorico = new HashMap<String, Historico>();
			
			LatLng location = null;
			map.clear();
			for (int i = 0; i < lista.length; i++) {
				Historico historico = lista[i];
				
				marker = new MarkerOptions();
				location = new LatLng(historico.getLatitude(), historico.getLongitude());
				
				String chave = getTitleMarker(historico);
						
				mapaHistorico.put(chave, historico);
				Log.i("jandrei - atualiza", chave);
				
				marker.position(location).title(chave).icon(BitmapDescriptorFactory.fromResource(R.drawable.bus_finder));
				
				map.addMarker(marker);
			}

			map.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 12.0f));
		} catch (Exception e) {
			Utils.setMyLocation(this, map);
		}
	}
	
	private String getTitleMarker(Historico historico){
		String indStatus = historico.getIndStatus();
		if (indStatus != null && !indStatus.trim().isEmpty()) {
			indStatus = indStatus.equals("1") ? "Vazio" : indStatus.equals("2") ? "Médio" : indStatus.equals("3") ? "Cheio" : " não definido ";
		}
		
		return "["+ historico.getRouteId()+"] Passou as " + Utils.formatData(historico.getDthEnvio()) + " e esta " + (indStatus);
	}

	@Override
	public boolean onMarkerClick(Marker marker) {
//		atualizaPonto();
				
		if (enviar.onibusEmAndamento()) {
			Mensagens.ExibeMensagemToast(this, marker.getTitle());
		} else {
			showPegarOnibusDialog(route,marker.getTitle());
		}
		return false;
	}

	private void showPegarOnibusDialog(final RouteED routeED,String chaveHistorico) {
		final Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.dialog_pegar_descobrir_bus);// carregando
		
		dialog.setTitle("Selecione!");// título do dialog

		final Button btnPegarBus = (Button) dialog.findViewById(R.id.btPegarBus);// se
		final Button btnCancelar = (Button) dialog.findViewById(R.id.btnCadeBus);
		btnCancelar.setText("Cancelar");
		
		Historico h = mapaHistorico.get(chaveHistorico);
		Log.i("jandrei - showdialog", chaveHistorico);
		String descHistorico = "";//h.getDescStatus();
		if (h !=null){
			descHistorico = "E ele está "+h.getDescStatus();
		}
		
		
		((TextView) dialog.findViewById(R.id.informacao)).setText("Pegar esse bus?"+descHistorico);

		btnPegarBus.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				showDizerVazioMedioOuCheioDialog(routeED);
				dialog.dismiss();
			}
		});

		btnCancelar.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		dialog.show();// mostra o dialog
	}

	private void showDizerVazioMedioOuCheioDialog(final RouteED routeED) {
		final Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.dialog_vaziomediocheio_bus);// carregando

		Button ok = (Button) dialog.findViewById(R.id.btOK);
		Button cancelar = (Button) dialog.findViewById(R.id.btnCancelar);

		final RadioButton btVazio = (RadioButton) dialog.findViewById(R.id.rbVazio);
		final RadioButton btMedio = (RadioButton) dialog.findViewById(R.id.rbMedio);
		final RadioButton btCheio = (RadioButton) dialog.findViewById(R.id.rbCheio);

		ok.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				final String indStatus = btVazio.isChecked() ? "1" : btMedio.isChecked() ? "2" : btCheio.isChecked() ? "3" : "";
				if (indStatus.isEmpty()) {
					Mensagens.ExibeMensagemToast(v.getContext(), "Selecione uma opção para prosseguir!");
					return;
				}

				enviar.pegarBus(v.getContext(), routeED, indStatus);
				atualizaPonto();
				dialog.dismiss();
			}
		});
		cancelar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		dialog.setTitle("Como esta o ônibus?");// título do dialog
		dialog.show();// mostra o dialog
	}

	class Ponto {
		public Ponto() {
		}

		public Ponto(double lat, double lon, String descricao, String id) {
			this.latLong = new LatLng(lat, lon);
			this.descricao = descricao;
			this.id = id;
		}

		LatLng latLong;
		String descricao;
		String id;

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((id == null) ? 0 : id.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Ponto other = (Ponto) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (id == null) {
				if (other.id != null)
					return false;
			} else if (!id.equals(other.id))
				return false;
			return true;
		}

		private MapaMeuBus getOuterType() {
			return MapaMeuBus.this;
		}

	}

}
