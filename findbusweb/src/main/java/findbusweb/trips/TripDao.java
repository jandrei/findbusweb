package findbusweb.trips;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;

import findbusweb.ed.Trips;
import findbusweb.infra.BaseDao;

public class TripDao extends BaseDao<Trips, String>{
	
	public TripDao() {
		super(Trips.class);
	}
	
	@Override
	public void addOrdem(DetachedCriteria dc) {
		super.addOrdem(dc);
		
		dc.addOrder(Order.asc("serviceId"));
		dc.addOrder(Order.asc("routeId"));
	}

}
