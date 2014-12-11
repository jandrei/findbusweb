package br.com.senac.findbus.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import br.com.senac.findbus.R;
import br.com.senac.findbus.dao.RouteDAO;

public class SplashView extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);

		new Handler().postDelayed(new Runnable() {

			/*
			 * Showing splash screen with a timer. This will be useful when you
			 * want to show case your app logo / company
			 */

			@Override
			public void run() {
				// This method will be executed once the timer is over
				// Start your app main activity
				Intent i = new Intent(SplashView.this, MenuBotoes.class);
				startActivity(i);

				// close this activity
				finish();
			}
		}, 1000);

		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					RouteDAO dao = RouteDAO.getInstance(SplashView.this);
					if (dao.contar() == 0) {
						dao.importarFromWS(SplashView.this);
					}
					System.out.println(dao.contar());
				} catch (Exception e) {
					
				}
			}
		}).start();
	}
}
