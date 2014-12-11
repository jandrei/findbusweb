package br.com.senac.findbus.view;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import br.com.senac.findbus.Constantes;
import br.com.senac.findbus.Mensagens;
import br.com.senac.findbus.R;
import br.com.senac.findbus.SynchronousHttpConnection;
import br.com.senac.findbus.dao.RouteDAO;
import br.com.senac.findbus.dao.StopDAO;

import com.google.gson.GsonBuilder;

public class BotoesAcaoAntigos extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		final StopDAO stopDao = StopDAO.getInstance(getApplication());

		Button botao = (Button) findViewById(R.id.importacaoStops);

		botao.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				stopDao.importarFromWS(v.getContext());
			}

		});

		final RouteDAO routeDao = RouteDAO.getInstance(getBaseContext());
		Button importarRotas = (Button) findViewById(R.id.importacaoRoutes);

		importarRotas.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				routeDao.importarFromWS(v.getContext());
			}

		});

		btnChamaTelaPesquisaRoutes(null);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void btnChamaTelaExemplo1(View v) {
		try {
			startActivity(new Intent(this, MapaParadas.class));
		} catch (Exception e) {
			Mensagens.ExibeExceptionAlert(v.getContext(), e);
		}

	}

	public void btnChamaTelaPesquisaStops(View v) {
		startActivity(new Intent(this, PesquisaStops.class));
	}

	public void btnChamaTelaPesquisaRoutes(View v) {
		startActivity(new Intent(this, PesquisaRoutes.class));
	}

	public void btnAdicionaLocationTeste(View v) {
		LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Location locatioLL = locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		try {
			
			Map<String, Object> historico = new HashMap<String, Object>();
			historico.put("routeId", "rotaid");
			historico.put("imeiId", "imeiid");
			historico.put("dthEnvio", Calendar.getInstance());
			historico.put("latitude", locatioLL.getLatitude());
			historico.put("longitude", locatioLL.getLongitude());

			String input = new GsonBuilder()
					.setDateFormat("dd/MMM/yyyy HH:mm:ss").create()
					.toJson(historico);
			
			if (android.os.Build.VERSION.SDK_INT > 9) {
				StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
				StrictMode.setThreadPolicy(policy);
			}
			
			new SynchronousHttpConnection().post(Constantes.urlAmazon + Constantes.adicionaHistorico,input);
		} catch (Exception e) {
			Mensagens.ExibeExceptionAlert(this, e);
		}

	}
}
