package findbusweb.calendar;

import findbusweb.ed.Calendars;
import findbusweb.infra.BaseDao;

public class CalendarDao extends BaseDao<Calendars, String>{
	
	public CalendarDao() {
		super(Calendars.class);
	}

}
