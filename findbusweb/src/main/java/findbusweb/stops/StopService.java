package findbusweb.stops;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import findbusweb.ed.Stops;
import findbusweb.infra.BaseService;

@Stateless
public class StopService extends BaseService<Stops, Integer>{

	private static final long serialVersionUID = -5383145122124909331L;
	
	
	@Inject
	StopDao dao;
	
	
	@PostConstruct
	public void initDao() {
		setDao(dao);
	}
	

}
