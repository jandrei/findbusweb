package findbusweb.agency;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import findbusweb.ed.Agency;
import findbusweb.infra.BaseService;

@Stateless
public class AgencyService extends BaseService<Agency, String>{

	private static final long serialVersionUID = -5383145122124909331L;
	
	
	@Inject
	AgencyDao dao;
	
	
	@PostConstruct
	public void initDao() {
		setDao(dao);
	}
	

}
