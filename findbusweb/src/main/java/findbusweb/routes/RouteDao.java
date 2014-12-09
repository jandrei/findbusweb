package findbusweb.routes;

import findbusweb.ed.Routes;
import findbusweb.infra.BaseDao;

public class RouteDao extends BaseDao<Routes, String>{
	
	public RouteDao() {
		super(Routes.class);
	}

}
