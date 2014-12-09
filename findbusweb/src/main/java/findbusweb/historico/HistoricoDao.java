package findbusweb.historico;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import findbusweb.Rest;
import findbusweb.ed.Historico;
import findbusweb.infra.BaseDao;

public class HistoricoDao extends BaseDao<Historico, Integer> {

	public HistoricoDao() {
		super(Historico.class);
	}

	@Override
	public void addOrdem(DetachedCriteria dc) {
		super.addOrdem(dc);
		dc.addOrder(Order.desc("dthEnvio"));
	}

	@Override
	public Historico save(Historico entity) {
		// TODO Auto-generated method stub
		beginTransaction();

		Historico hist = super.save(entity);

		commit();

		return hist;
	}

	public Historico pegaUltimoHistoricoPorStop(String routeId) {
		try {
			DetachedCriteria dc = DetachedCriteria.forClass(Historico.class);
			dc.add(Restrictions.eq("routeId", routeId));
			addOrdem(dc);

			Criteria cr = dc.getExecutableCriteria(getSession());
			cr.setMaxResults(1);

			return (Historico) cr.uniqueResult();
		} catch (NoResultException e) {
			return new Historico();
		}
	}
	

	public List<Historico> pegaHistoricoRoutes(String routeID){
		Calendar dthInicial = Calendar.getInstance();
		dthInicial.add(Calendar.HOUR_OF_DAY, -24);

		Calendar dthFinal = Calendar.getInstance();
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy kk:mm:ss");
		System.out.println("Rota = ["+routeID+"] de ["+sdf.format(dthInicial.getTime())+"] até ["+sdf.format(dthFinal.getTime())+"]");
		
		try {
			DetachedCriteria dc = DetachedCriteria.forClass(Historico.class);
			dc.add(Restrictions.eq("routeId", routeID));
			
			dc.add(Restrictions.between("dthEnvio", dthInicial, dthFinal));
			
			addOrdem(dc);

			Criteria cr = dc.getExecutableCriteria(getSession());
			cr.setMaxResults(50);

			return cr.list();
		} catch (NoResultException e) {
			return new ArrayList<Historico>();
		}
	}
}
