package br.com.senac.findbus.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import br.com.senac.findbus.Mensagens;
import br.com.senac.findbus.R;

public class MenuBotoes extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_botoes);
		try{
		((Button)findViewById(R.id.btnSairSistema1)).setOnClickListener(this);
		((Button)findViewById(R.id.btnPesquisarRotas1)).setOnClickListener(this);
		
		}catch(Exception e){
			Mensagens.ExibeExceptionAlert(this, e);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnSairSistema1:
			this.finish();
			break;
		case R.id.btnPesquisarRotas1:
			abreTela(PesquisaRoutes.class);
			break;
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void abreTela(Class cls) {
		try {
			startActivity(new Intent(this, cls));
		} catch (Exception e) {
			Mensagens.ExibeExceptionAlert(this, e);
		}

	}

}
