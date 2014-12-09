package findbusweb.ed;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "stop_time")
@NamedQueries(@NamedQuery(name = "StopTime.consulta", query = "select a from StopTime a where a.stopTimeId=:id"))
public class StopTime extends BaseEntity<Integer> {

	private static final long serialVersionUID = 9093644730825368464L;

	@Id
	@Column(name = "stop_time_id")
	private Integer stopTimeId;

	@Column(name = "stop_id")
	private String stopId;

	@Column(name = "trip_id")
	private String tripId;

	@Column(name = "arrival_time")
	private Calendar arrivalTime;

	@Column(name = "departure_time")
	private Calendar departureTime;

	@Column(name = "stop_sequence")
	private Integer stopSequence;

	@Override
	public Integer getId() {
		return stopTimeId;
	}

	public String getStopId() {
		return stopId;
	}

	public void setStopId(String stopId) {
		this.stopId = stopId;
	}

	public String getTripId() {
		return tripId;
	}

	public void setTripId(String tripId) {
		this.tripId = tripId;
	}

	public Calendar getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(Calendar arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public Calendar getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(Calendar departureTime) {
		this.departureTime = departureTime;
	}

	public Integer getStopSequence() {
		return stopSequence;
	}

	public void setStopSequence(Integer stopSequence) {
		this.stopSequence = stopSequence;
	}

	public Integer getStopTimeId() {
		return stopTimeId;
	}

	public void setStopTimeId(Integer stopTimeId) {
		this.stopTimeId = stopTimeId;
	}
}
