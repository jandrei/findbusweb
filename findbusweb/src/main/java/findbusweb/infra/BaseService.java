package findbusweb.infra;

import java.util.List;

import findbusweb.ed.BaseEntity;
import findbusweb.infra.annotations.ExceptionInterceptor;
import findbusweb.infra.annotations.TransacaoInterceptor;

@ExceptionInterceptor
public abstract class BaseService<T extends BaseEntity<K>, K> implements
		Service<T, K> {

	private static final long serialVersionUID = -6095954296737352231L;

	private Dao<T, K> dao;

	public Dao<T, K> getDao() {
		return dao;
	}

	public void setDao(Dao<T, K> dao) {
		this.dao = dao;
	}

	@TransacaoInterceptor
	public T salvar(T entity) {
		if (entity.getId() == null) {
			validaInclui(entity);
			return dao.save(entity);
		}
		validaAltera(entity);
		entity = dao.update(entity);

		return entity;
	}

	public void validaAltera(T entity) {
	}

	public void validaInclui(T entity) {

	}

	public T consultar(K key) {
		return dao.find(key);
	}

	public void validaConsulta(K key) {
		if (key == null) {
			throw new RuntimeException(
					"Chave para consulta deve ser informada.");
		}
	}

	public List<T> listarTodos() {
		return dao.listAll();
	}

	public List<T> listarTodos(Integer max,Integer page) {
		return dao.listAll(max,page);
	}

	public List<T> listar(T entity){
		return dao.list(entity);
	}
	
	@TransacaoInterceptor
	public void excluir(K key) {
		dao.delete(key);
	}

}
