package br.com.senac.findbus.view;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import br.com.senac.findbus.Mensagens;
import br.com.senac.findbus.R;
import br.com.senac.findbus.dao.RouteDAO;
import br.com.senac.findbus.infra.EnviaMinhaLocalizacaoRunnable;
import br.com.senac.findbus.model.RouteED;

public class PesquisaRoutes extends Activity implements OnItemClickListener {

	EditText txtPesquisa;
	ListView lista;
	RouteDAO dao;
	List<RouteED> routes = new ArrayList<RouteED>();
	EnviaMinhaLocalizacaoRunnable enviar;// =
											// EnviaMinhaLocalizacaoRunnable.getInstance(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pesquisa_routes);
		ActionBar actionBar = getActionBar();

//		// Specify that tabs should be displayed in the action bar.
//		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
//		try {
//			ActionBar.TabListener tabListener = new ActionBar.TabListener() {
//				public void onTabSelected(ActionBar.Tab tab,
//						FragmentTransaction ft) {
//					// show the given tab
//				}
//
//				public void onTabUnselected(ActionBar.Tab tab,
//						FragmentTransaction ft) {
//					// hide the given tab
//				}
//
//				public void onTabReselected(ActionBar.Tab tab,
//						FragmentTransaction ft) {
//					// probably ignore this event
//				}
//			};
//
//			actionBar.addTab(actionBar.newTab().setText("Pesquisa")
//					.setTabListener(tabListener));
//			actionBar.addTab(actionBar.newTab().setText("Mapa")
//					.setTabListener(tabListener));
//
//		} catch (Exception e) {
//			Mensagens.ExibeExceptionAlert(this, e);
//		}

		try {
			txtPesquisa = (EditText) findViewById(R.id.edtPesquisarRoutes);
			lista = (ListView) findViewById(R.id.listaRoutes);
			registerForContextMenu(lista);
			dao = RouteDAO.getInstance(this);
			enviar = EnviaMinhaLocalizacaoRunnable.getInstance(this);
			lista.setOnItemClickListener(this);

			btnPesquisar(null);

			

		} catch (Exception e) {
			Mensagens.ExibeExceptionAlert(this, e);
		}
	}

	public void btnPesquisar(View view) {
		try {

			RouteED ed = new RouteED();
			routes = new ArrayList<RouteED>();
			ed.setRouteShortName(txtPesquisa.getText().toString().trim());
			if (ed.getRouteShortName().isEmpty()) {
				ed.setRouteShortName(null);
			}
			routes = dao.listar(ed);

			ArrayAdapter<String> fileList = null;
			ArrayList<String> result = new ArrayList<String>();

			for (RouteED p : routes) {
				result.add(p.getRouteId() + " - " + p.getRouteLongName());
			}

			fileList = new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1, result);
			lista.setAdapter(fileList);

		} catch (Exception e) {
			Mensagens.ExibeExceptionAlert(this, e);
		}
	}

	private void showPegarOnibusDialog(final RouteED routeED) {
		final Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.dialog_pegar_descobrir_bus);// carregando
		dialog.setTitle("Selecione!");// título do dialog

		final Button btnPegarBus = (Button) dialog
				.findViewById(R.id.btPegarBus);// se
		final Button btnCadeBus = (Button) dialog.findViewById(R.id.btnCadeBus);

		btnPegarBus.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				showDizerVazioMedioOuCheioDialog(routeED);
				dialog.dismiss();
			}
		});

		btnCadeBus.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				abreLocalOnibus(routeED, v.getContext());
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

		final RadioButton btVazio = (RadioButton) dialog
				.findViewById(R.id.rbVazio);
		final RadioButton btMedio = (RadioButton) dialog
				.findViewById(R.id.rbMedio);
		final RadioButton btCheio = (RadioButton) dialog
				.findViewById(R.id.rbCheio);

		ok.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				final String indStatus = btVazio.isChecked() ? "1" : btMedio
						.isChecked() ? "2" : btCheio.isChecked() ? "3" : "";
				if (indStatus.isEmpty()) {
					Mensagens.ExibeMensagemToast(v.getContext(),
							"Selecione uma opção para prosseguir!");
					return;
				}

				enviar.pegarBus(v.getContext(), routeED, indStatus);
				abreLocalOnibus(routeED, v.getContext());
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

	private void abreLocalOnibus(RouteED routeED, Context ctx) {
		Intent intent = new Intent(ctx, MapaMeuBus.class);
		
		intent.putExtra("ed", routeED);
		intent.putExtra("cadeOnibus", "OK");
		
		startActivity(intent);

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		RouteED routeED = routes.get(position);
		showPegarOnibusDialog(routeED);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_activity_actions, menu);
		return super.onCreateOptionsMenu(menu);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_importar:
			dao.importarFromWS(PesquisaRoutes.this);
			Mensagens.ExibeMensagemAlert(PesquisaRoutes.this,
					"Rotas importadas com sucesso!");
			break;
		}

		return super.onOptionsItemSelected(item);
	}

}
