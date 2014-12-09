package findbusweb.stoptime;

import findbusweb.ed.StopTime;
import findbusweb.infra.BaseDao;

public class StopTimeDao extends BaseDao<StopTime, Integer>{
	
	public StopTimeDao() {
		super(StopTime.class);
	}

}
