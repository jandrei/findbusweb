package findbusweb.ed;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "calendar")
@NamedQueries(@NamedQuery(name = "Calendar.consulta", query = "select a from Calendars a where a.serviceId=:id"))
public class Calendars extends BaseEntity<String> {

	private static final long serialVersionUID = -1051060365598585368L;

	@Id
	@NotNull
	@Column(name = "service_Id")
	private String serviceId;

	@Column(name = "monday")
	private Integer monday;

	@Column(name = "tuesday")
	private Integer tuesday;

	@Column(name = "wednesday")
	private Integer wednesday;

	@Column(name = "thursday")
	private Integer thursday;

	@Column(name = "friday")
	private Integer friday;

	@Column(name = "saturday")
	private Integer saturday;

	@Column(name = "sunday")
	private Integer sunday;

	@Column(name = "start_date")
	private Calendar startDate;

	@Column(name = "end_date")
	private Calendar endDate;

	@Override
	public String getId() {
		return serviceId;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public Integer getMonday() {
		return monday;
	}

	public void setMonday(Integer monday) {
		this.monday = monday;
	}

	public Integer getTuesday() {
		return tuesday;
	}

	public void setTuesday(Integer tuesday) {
		this.tuesday = tuesday;
	}

	public Integer getWednesday() {
		return wednesday;
	}

	public void setWednesday(Integer wednesday) {
		this.wednesday = wednesday;
	}

	public Integer getThursday() {
		return thursday;
	}

	public void setThursday(Integer thursday) {
		this.thursday = thursday;
	}

	public Integer getFriday() {
		return friday;
	}

	public void setFriday(Integer friday) {
		this.friday = friday;
	}

	public Integer getSaturday() {
		return saturday;
	}

	public void setSaturday(Integer saturday) {
		this.saturday = saturday;
	}

	public Integer getSunday() {
		return sunday;
	}

	public void setSunday(Integer sunday) {
		this.sunday = sunday;
	}

	public Calendar getStartDate() {
		return startDate;
	}

	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}

	public Calendar getEndDate() {
		return endDate;
	}

	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}

}
