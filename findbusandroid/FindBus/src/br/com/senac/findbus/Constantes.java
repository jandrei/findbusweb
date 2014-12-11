package br.com.senac.findbus;

public class Constantes {
	
	public static final String urlAmazon = "http://ec2-54-200-87-69.us-west-2.compute.amazonaws.com/findbusweb/webservice/r"; 
	public static final String urlLocal = "http://192.168.1.4:8080/findbusweb/webservice/r"; 
	
	public static class Stop{
		public static final String urlStop = "/stops";
	}
	public static class Route{
		public static final String urlRoute = "/routes";
	}
	public static class Agency{
		public static final String urlStop = "/agencys";
	}

	public static final String adicionaHistorico= "/adicionaHistorico";
	
	public static final String pegaUltimoHistoricoPorStop =  "/pegaUltimoHistoricoPorStop?routeId=";

	public static final String pegaHistoricoRoutes = "/pegaHistoricoRoutes?routeId=";
	
	public static final float stopsProximos = 0.001f; 
	//

}
