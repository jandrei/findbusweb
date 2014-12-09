package findbusweb.ed;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "routes")
@NamedQueries(@NamedQuery(name = "Routes.consulta", query = "select a from Routes a where a.routeId=:id"))
public class Routes extends BaseEntity<String> {

	private static final long serialVersionUID = -1051060365598585368L;

	@Id
	@NotNull
	@Column(name = "route_Id")
	private String routeId;

	@Column(name = "agency_id")
	private String agencyId;

	@NotNull
	@Column(name = "route_short_name")
	private String routeShortName;

	@NotNull
	@Column(name = "route_long_name")
	private String routeLongName;

	@Column(name = "route_desc")
	private String routeDesc;

	@NotNull
	@Column(name = "route_type")
	private Integer routeType;

	@Column(name = "route_url")
	private String routeUrl;

	@Column(name = "route_color")
	private String routeColor;

	@Column(name = "route_text_color")
	private String routeTextColor;

	public String getRouteId() {
		return routeId;
	}

	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}

	public String getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
	}

	public String getRouteShortName() {
		return routeShortName;
	}

	public void setRouteShortName(String routeShortName) {
		this.routeShortName = routeShortName;
	}

	public String getRouteLongName() {
		return routeLongName;
	}

	public void setRouteLongName(String routeLongName) {
		this.routeLongName = routeLongName;
	}

	public String getRouteDesc() {
		return routeDesc;
	}

	public void setRouteDesc(String routeDesc) {
		this.routeDesc = routeDesc;
	}

	public Integer getRouteType() {
		return routeType;
	}

	public void setRouteType(Integer routeType) {
		this.routeType = routeType;
	}

	public String getRouteUrl() {
		return routeUrl;
	}

	public void setRouteUrl(String routeUrl) {
		this.routeUrl = routeUrl;
	}

	public String getRouteColor() {
		return routeColor;
	}

	public void setRouteColor(String routeColor) {
		this.routeColor = routeColor;
	}

	public String getRouteTextColor() {
		return routeTextColor;
	}

	public void setRouteTextColor(String routeTextColor) {
		this.routeTextColor = routeTextColor;
	}

	@Override
	public String getId() {
		return this.routeId;
	}

}
