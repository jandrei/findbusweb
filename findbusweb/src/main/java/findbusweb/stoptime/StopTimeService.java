package findbusweb.stoptime;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import findbusweb.ed.StopTime;
import findbusweb.infra.BaseService;

@Stateless
public class StopTimeService extends BaseService<StopTime, Integer>{

	private static final long serialVersionUID = -5383145122124909331L;
	
	
	@Inject
	StopTimeDao dao;
	
	
	@PostConstruct
	public void initDao() {
		setDao(dao);
	}
	

}
