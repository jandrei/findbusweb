package findbusweb.routes;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import findbusweb.ed.Routes;
import findbusweb.infra.BaseService;

@Stateless
public class RouteService extends BaseService<Routes, String>{

	private static final long serialVersionUID = -5383145122124909331L;
	
	
	@Inject
	RouteDao dao;
	
	
	@PostConstruct
	public void initDao() {
		setDao(dao);
	}
	

}
