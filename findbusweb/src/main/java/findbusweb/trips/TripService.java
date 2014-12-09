package findbusweb.trips;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import findbusweb.ed.Trips;
import findbusweb.infra.BaseService;

@Stateless
public class TripService extends BaseService<Trips, String>{

	private static final long serialVersionUID = -5383145122124909331L;
	
	
	@Inject
	TripDao dao;
	
	
	@PostConstruct
	public void initDao() {
		setDao(dao);
	}
	

}
