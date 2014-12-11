package br.com.senac.findbus.infra;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.StrictMode;
import android.support.v4.app.NotificationCompat;
import android.telephony.TelephonyManager;
import br.com.senac.findbus.Constantes;
import br.com.senac.findbus.Mensagens;
import br.com.senac.findbus.SynchronousHttpConnection;
import br.com.senac.findbus.model.RouteED;
import br.com.senac.findbus.view.MapaMeuBus;
import br.com.senac.findbus.view.PesquisaRoutes;

import com.google.gson.GsonBuilder;

public class EnviaMinhaLocalizacaoRunnable {

	private static EnviaMinhaLocalizacaoRunnable INSTANCE;

	Context context;
	boolean continuar = false;

	RouteED routeED;
	private String indStatus;

	long mili = 1000l;
	long segundo = 1;
	long minuto = segundo * 60;
	long hora = minuto * 60;

	// de tres em tres segundos
	long tempoEmMiliSegundosEntreEnvios = mili * segundo * 5;

	SynchronousHttpConnection connect = new SynchronousHttpConnection();

	private EnviaMinhaLocalizacaoRunnable(Context context) {
		this.context = context;
	}

	public static EnviaMinhaLocalizacaoRunnable getInstance(Context context) {
		if (INSTANCE == null) {
			INSTANCE = new EnviaMinhaLocalizacaoRunnable(context);
		}

		return INSTANCE;
	}

	private void run() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (continuar) {
					try {
						envia();
						criaNotificacao();
						Thread.sleep(tempoEmMiliSegundosEntreEnvios);
					} catch (Exception e) {
						try {
							//qualquer erro que der na hora de enviar a localização do meu onibus eu simplesmente espero 3 x o tempo normal para tentar enviar novamente
							Thread.sleep(tempoEmMiliSegundosEntreEnvios * 3);
						} catch (Exception ee) {
						}
					}
				}
				setRouteED(null);
			}
		}).start();
	}

	private void envia() throws Exception {
		LocationManager locManager = (LocationManager) this.context.getSystemService(Context.LOCATION_SERVICE);
		Location location = locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

		TelephonyManager telephonyManager = (TelephonyManager) this.context.getSystemService(Context.TELEPHONY_SERVICE);

		Map<String, Object> historico = new HashMap<String, Object>();
		historico.put("routeId", routeED.getRouteId());
		historico.put("imeiId", telephonyManager.getDeviceId());
		historico.put("dthEnvio", Calendar.getInstance());
		historico.put("latitude", location.getLatitude());
		historico.put("longitude", location.getLongitude());
		historico.put("indStatus", indStatus);

		String input = new GsonBuilder().setDateFormat("dd/MMM/yyyy HH:mm:ss").create().toJson(historico);

		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}

		connect.post(Constantes.urlAmazon + Constantes.adicionaHistorico, input);
	}

	private void setRouteED(RouteED routeED) {
		this.routeED = routeED;
	}

	public void pegarBus(Context context, RouteED routeed,String indStatus) {
		if (this.continuar) {
			Mensagens.ExibeMensagemToast(context, "Finalize uma viagem para pegar outro ônibus!");
			return;
		}
		this.context = context;
		this.routeED = routeed;
		this.continuar = true;
		this.indStatus = indStatus;

		run();

	}
	public boolean onibusEmAndamento(){
		return this.continuar;
	}

	private void criaNotificacao() {
		// Add as notification
		NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

		NotificationCompat.Builder builder = new NotificationCompat.Builder(context).setSmallIcon(br.com.senac.findbus.R.drawable.icone_bus) 
				.setContentTitle("Onibus [" + this.routeED.getRouteShortName() + "] em movimento.").setContentText(this.routeED.getRouteLongName());

		Intent notificationIntent = new Intent(context, MapaMeuBus.class);
		notificationIntent.putExtra("abrirPergunta", "sim");
		notificationIntent.putExtra("ed", routeED);

		PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		builder.setContentIntent(contentIntent);

		manager.notify(1, builder.build());
	}

	public void sairDoBus() {
		this.continuar = false;
	}

	// public RouteED getRouteED() {
	// return routeED;
	// }
}
