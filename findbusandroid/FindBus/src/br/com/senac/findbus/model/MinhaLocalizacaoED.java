package br.com.senac.findbus.model;

import java.io.Serializable;
import java.util.Calendar;

public class MinhaLocalizacaoED implements Serializable {

	private String imei;

	private Double longitude;

	private Double latitude;

	private Calendar dataHoraDoLugar;

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Calendar getDataHoraDoLugar() {
		return dataHoraDoLugar;
	}

	public void setDataHoraDoLugar(Calendar dataHoradoLugar) {
		this.dataHoraDoLugar = dataHoradoLugar;
	}

}
