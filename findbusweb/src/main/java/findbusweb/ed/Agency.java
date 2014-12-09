package findbusweb.ed;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "agency")
@NamedQueries(@NamedQuery(name = "Agency.consulta", query = "select a from Agency a where a.agencyName=:id"))
public class Agency extends BaseEntity<String> {

	private static final long serialVersionUID = -1051060365598585368L;

	@Column(name = "agency_id")
	@NotNull
	private String agencyId;

	@Id
	@Column(name = "agency_name")
	@NotNull
	private String agencyName;

	@Column(name = "agency_url")
	@NotNull
	private String agencyUrl;

	@Column(name = "agency_timezone")
	@NotNull
	private String agencyTimezone;

	@Column(name = "agency_lang")
	private String agencyLang;

	@Column(name = "agency_phone")
	private Integer agencyPhone;

	@Column(name = "agency_fare_url")
	private String agencyFareUrl;

	public String getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
	}

	public String getAgencyName() {
		return agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	public String getAgencyUrl() {
		return agencyUrl;
	}

	public void setAgencyUrl(String agencyUrl) {
		this.agencyUrl = agencyUrl;
	}

	public String getAgencyTimezone() {
		return agencyTimezone;
	}

	public void setAgencyTimezone(String agencyTimezone) {
		this.agencyTimezone = agencyTimezone;
	}

	public String getAgencyLang() {
		return agencyLang;
	}

	public void setAgencyLang(String agencyLang) {
		this.agencyLang = agencyLang;
	}

	public Integer getAgencyPhone() {
		return agencyPhone;
	}

	public void setAgencyPhone(Integer agencyPhone) {
		this.agencyPhone = agencyPhone;
	}

	public String getAgencyFareUrl() {
		return agencyFareUrl;
	}

	public void setAgencyFareUrl(String agencyFareUrl) {
		this.agencyFareUrl = agencyFareUrl;
	}

	@Override
	public String getId() {
		return this.agencyName;
	}

}
