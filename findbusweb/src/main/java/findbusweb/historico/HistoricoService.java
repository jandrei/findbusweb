package findbusweb.historico;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import findbusweb.ed.Historico;
import findbusweb.infra.BaseService;

@Stateless
public class HistoricoService extends BaseService<Historico, Integer> {

	private static final long serialVersionUID = -5383145122124909331L;

	@Inject
	HistoricoDao dao;

	@PostConstruct
	public void initDao() {
		setDao(dao);
	}

	public Historico pegaUltimoHistoricoPorStop(String routeId) {
		return dao.pegaUltimoHistoricoPorStop(routeId);
	}

	public List<Historico> pegaHistoricoRoutes(String routeID) {
		return dao.pegaHistoricoRoutes(routeID);
	}
}
