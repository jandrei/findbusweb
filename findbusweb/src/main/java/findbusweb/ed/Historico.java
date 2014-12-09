package findbusweb.ed;

import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "historico")
@NamedQueries(@NamedQuery(name = "Historico.consulta", query = "select a from Historico a where a.historicoId=:id"))
public class Historico extends BaseEntity<Integer> {

	private static final long serialVersionUID = -1051060365598585368L;

	@Id
	@NotNull
	@Column(name = "historico_id")
	@SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "historico_historico_id_seq", name = "historico_historico_id_seq")
	@GeneratedValue(generator = "historico_historico_id_seq", strategy = GenerationType.SEQUENCE)
	private Integer historicoId;

	@Column(name = "route_id")
	private String routeId;

	@Column(name = "id_imei")
	private String imeiId;

	@Column(name = "dth_envio")
	private Calendar dthEnvio;

	@Column(name = "nro_latitude", precision = 6)
	private BigDecimal latitude;

	@Column(name = "nro_longitude", precision = 6)
	private BigDecimal longitude;

	@Column(name = "ind_status")
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

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public String getRouteId() {
		return routeId;
	}

	public void setRouteId(String routeID) {
		this.routeId = routeID;
	}

	@Override
	public Integer getId() {
		return this.historicoId;
	}

	public String getIndStatus() {
		return indStatus;
	}

	public void setIndStatus(String indStatus) {
		this.indStatus = indStatus;
	}

}
