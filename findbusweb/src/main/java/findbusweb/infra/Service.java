package findbusweb.infra;

import java.io.Serializable;
import java.util.List;

import findbusweb.ed.BaseEntity;

public interface Service<T extends BaseEntity<K> , K> extends Serializable{
	
	public abstract void initDao();

	public abstract T salvar(T entity);

	public abstract T consultar(K key);

	public abstract List<T> listar(T entity);

	public abstract void excluir(K key);

}
