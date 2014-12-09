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
@Table(name = "shapes")
@NamedQueries(@NamedQuery(name = "Shapes.consulta", query = "select a from Shapes a where a.shapeSerial=:id"))
public class Shapes extends BaseEntity<Integer> {

	private static final long serialVersionUID = -1051060365598585368L;

	@Id
	@NotNull
	@Column(name = "shape_serial")
	private Integer shapeSerial;

	@Column(name = "shape_id")
	private String shapeId;

	@Column(name = "shape_pt_lat", precision = 6)
	private BigDecimal shapePtLat;

	@Column(name = "shape_pt_lon", precision = 6)
	private BigDecimal shapePtLon;

	@Column(name = "shape_pt_sequence")
	private Integer shapePtSequence;

	@Override
	public Integer getId() {
		return shapeSerial;
	}

	public String getShapeId() {
		return shapeId;
	}

	public void setShapeId(String shapeId) {
		this.shapeId = shapeId;
	}

	public BigDecimal getShapePtLat() {
		return shapePtLat;
	}

	public void setShapePtLat(BigDecimal shapePtLat) {
		this.shapePtLat = shapePtLat;
	}

	public BigDecimal getShapePtLon() {
		return shapePtLon;
	}

	public void setShapePtLon(BigDecimal shapePtLon) {
		this.shapePtLon = shapePtLon;
	}

	public Integer getShapePtSequence() {
		return shapePtSequence;
	}

	public void setShapePtSequence(Integer shapePtSequence) {
		this.shapePtSequence = shapePtSequence;
	}

	public Integer getShapeSerial() {
		return shapeSerial;
	}

	public void setShapeSerial(Integer shapeSerial) {
		this.shapeSerial = shapeSerial;
	}

}
