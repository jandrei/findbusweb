package teste;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import findbusweb.ed.Historico;

public class RestClient {

	public static void main(String[] args) throws Exception {
		new RestClient().pegaHistoricoRoutes();
	}

	public void adicionaHistoricoTest() throws Exception {
		URL url = new URL("http://localhost:8080/findbusweb/webservice/r/adicionaHistorico");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");

		Map<String, Object> historico = new HashMap<String, Object>();
		historico.put("routeId", "rotaid");
		historico.put("imeiId", "imeiid");
		historico.put("dthEnvio", Calendar.getInstance());
		historico.put("latitude", BigDecimal.valueOf(123.123));
		historico.put("longitude", BigDecimal.valueOf(321.321));
		historico.put("indStatus", "1");

		String input = new GsonBuilder().setDateFormat("dd/MMM/yyyy HH:mm:ss").create().toJson(historico);

		OutputStream os = conn.getOutputStream();
		os.write(input.getBytes());
		os.flush();

		if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
			throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
		}

		BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

		String output;
		System.out.println("Output from Server .... \n");
		while ((output = br.readLine()) != null) {
			System.out.println(output);
		}

		conn.disconnect();
	}

	public void pegaUltimoHistoricoByRoute() throws Exception {
		URL url = new URL("http://localhost:8080/findbusweb/webservice/r/pegaUltimoHistoricoPorStop?routeId=T11");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-Type", "application/json");

		conn.connect();

		if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
			throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
		}

		BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

		String output;
		System.out.println("Output from Server .... \n");
		while ((output = br.readLine()) != null) {
			System.out.println(output);
		}

		conn.disconnect();
	}

	public void pegaHistoricoRoutes() throws Exception {
		URL url = new URL("http://localhost:8080/findbusweb/webservice/r/pegaHistoricoRoutes?routeId=T11");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-Type", "application/json");

		conn.connect();

		if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
			throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
		}

		BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

		String output;
		System.out.println("Output from Server .... \n");
		while ((output = br.readLine()) != null) {
			System.out.println(output);
		}

		conn.disconnect();
	}

}
