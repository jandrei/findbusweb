package findbusweb.ed;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "trips")
@NamedQueries(@NamedQuery(name = "Trips.consulta", query = "select a from Trips a where a.tripId=:id"))
public class Trips extends BaseEntity<String> {

	private static final long serialVersionUID = -1051060365598585368L;

	@Column(name = "service_id")
	private String serviceId;

	@Column(name = "route_id")
	private String routeId;

	@Id
	@NotNull
	@Column(name = "trip_id")
	private String tripId;

	@Column(name = "trip_headsign")
	private String tripHeadsign;

	@Column(name = "trip_short_name")
	private String tripShortName;

	@Column(name = "direction_id")
	private Integer directionId;

	@Column(name = "block_id")
	private String blockId;

	@Column(name = "shape_id")
	private String shapeId;

	@Column(name = "wheelchair_accessible")
	private String wheelchairAccessible;

	@Override
	public String getId() {
		return this.tripId;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getRouteId() {
		return routeId;
	}

	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}

	public String getTripId() {
		return tripId;
	}

	public void setTripId(String tripId) {
		this.tripId = tripId;
	}

	public String getTripHeadsign() {
		return tripHeadsign;
	}

	public void setTripHeadsign(String tripHeadsign) {
		this.tripHeadsign = tripHeadsign;
	}

	public String getTripShortName() {
		return tripShortName;
	}

	public void setTripShortName(String tripShortName) {
		this.tripShortName = tripShortName;
	}

	public Integer getDirectionId() {
		return directionId;
	}

	public void setDirectionId(Integer directionId) {
		this.directionId = directionId;
	}

	public String getBlockId() {
		return blockId;
	}

	public void setBlockId(String blockId) {
		this.blockId = blockId;
	}

	public String getShapeId() {
		return shapeId;
	}

	public void setShapeId(String shapeId) {
		this.shapeId = shapeId;
	}

	public String getWheelchairAccessible() {
		return wheelchairAccessible;
	}

	public void setWheelchairAccessible(String wheelchairAccessible) {
		this.wheelchairAccessible = wheelchairAccessible;
	}

}
