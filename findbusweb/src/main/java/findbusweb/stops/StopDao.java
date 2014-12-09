package findbusweb.stops;

import findbusweb.ed.Stops;
import findbusweb.infra.BaseDao;

public class StopDao extends BaseDao<Stops, Integer>{
	
	public StopDao() {
		super(Stops.class);
	}

}
