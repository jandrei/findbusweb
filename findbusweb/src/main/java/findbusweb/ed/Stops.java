package findbusweb.ed;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "stops")
@NamedQueries(@NamedQuery(name = "Stops.consulta", query = "select a from Stops a where a.stopId=:id"))
public class Stops extends BaseEntity<Integer> {

	private static final long serialVersionUID = 9093644730825368464L;

	@Id
	@Column(name = "stop_id")
	@NotNull
	private Integer stopId;

	@Column(name = "stop_code")
	private String stopCode;

	@Column(name = "stop_name")
	@NotNull
	private String stopName;

	@Column(name = "stop_desc")
	private String stopDesc;

	@Column(name = "stop_lat",precision=6)
	@NotNull
	private BigDecimal stopLat;

	@Column(name = "stop_lon",precision=6)
	@NotNull
	private BigDecimal stopLon;

	public Integer getStopId() {
		return stopId;
	}

	public void setStopId(Integer stopId) {
		this.stopId = stopId;
	}

	public String getStopCode() {
		return stopCode;
	}

	public void setStopCode(String stopCode) {
		this.stopCode = stopCode;
	}

	public String getStopName() {
		return stopName;
	}

	public void setStopName(String stopName) {
		this.stopName = stopName;
	}

	public String getStopDesc() {
		return stopDesc;
	}

	public void setStopDesc(String stopDesc) {
		this.stopDesc = stopDesc;
	}

	public BigDecimal getStopLat() {
		return stopLat;
	}

	public void setStopLat(BigDecimal stopLat) {
		this.stopLat = stopLat;
	}

	public BigDecimal getStopLon() {
		return stopLon;
	}

	public void setStopLon(BigDecimal stopLon) {
		this.stopLon = stopLon;
	}

	@Override
	public Integer getId() {
		return stopId;
	}

}
