package br.com.senac.findbus.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

public class Historico implements Serializable {

	private static final long serialVersionUID = -8108499335466726717L;

	private Integer historicoId;

	private String routeId;

	private String imeiId;

	private Calendar dthEnvio;

	private double latitude;

	private double longitude;
	
	private String indStatus;

	public Integer getHistoricoId() {
		return historicoId;
	}

	public void setHistoricoId(Integer historicoId) {
		this.historicoId = historicoId;
	}

	public String getImeiId() {
		return imeiId;
	}

	public void setImeiId(String imeiId) {
		this.imeiId = imeiId;
	}

	public Calendar getDthEnvio() {
		return dthEnvio;
	}

	public void setDthEnvio(Calendar dthEnvio) {
		this.dthEnvio = dthEnvio;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getRouteId() {
		return routeId;
	}

	public void setRouteId(String routeID) {
		this.routeId = routeID;
	}
	
	public String getIndStatus() {
		return indStatus;
	}
	
	public String getDescStatus(){
		return getIndStatus().equals("1") ? "Vazio" : getIndStatus().equals("2") ? "Médio" : getIndStatus().equals("3") ?"Cheio":"Não definido";
	}
	
	public void setIndStatus(String indStatus) {
		this.indStatus = indStatus;
	}

}
