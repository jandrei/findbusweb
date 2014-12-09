package findbusweb.shapes;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import findbusweb.ed.Shapes;
import findbusweb.infra.BaseService;

@Stateless
public class ShapeService extends BaseService<Shapes, Integer>{

	private static final long serialVersionUID = -5383145122124909331L;
	
	
	@Inject
	ShapeDao dao;
	
	
	@PostConstruct
	public void initDao() {
		setDao(dao);
	}
	

}
