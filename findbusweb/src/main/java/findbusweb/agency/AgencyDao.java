package findbusweb.agency;

import findbusweb.ed.Agency;
import findbusweb.infra.BaseDao;

public class AgencyDao extends BaseDao<Agency, String>{
	
	public AgencyDao() {
		super(Agency.class);
	}

}
