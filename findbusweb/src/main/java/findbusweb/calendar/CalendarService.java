package findbusweb.calendar;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import findbusweb.ed.Calendars;
import findbusweb.infra.BaseService;

@Stateless
public class CalendarService extends BaseService<Calendars, String>{

	private static final long serialVersionUID = -5383145122124909331L;
	
	
	@Inject
	CalendarDao dao;
	
	
	@PostConstruct
	public void initDao() {
		setDao(dao);
	}
	

}
