package findbusweb.shapes;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;

import findbusweb.ed.Shapes;
import findbusweb.ed.Trips;
import findbusweb.infra.BaseDao;

public class ShapeDao extends BaseDao<Shapes, Integer>{
	
	public ShapeDao() {
		super(Shapes.class);
	}

	@Override
	public void addOrdem(DetachedCriteria dc) {
		super.addOrdem(dc);
		
		dc.addOrder(Order.asc("shapeId"));
		dc.addOrder(Order.asc("shapePtSequence"));
	}
}
