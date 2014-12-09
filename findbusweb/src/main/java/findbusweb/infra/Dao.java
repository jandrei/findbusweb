package findbusweb.infra;

import java.io.Serializable;
import java.util.List;

public interface Dao<T, K> extends Serializable{

	public abstract void beginTransaction();

	public abstract void commit();

	public abstract void rollback();

	public abstract void closeTransaction();

	public abstract void commitAndCloseTransaction();

	public abstract void flush();

	public abstract void joinTransaction();

	public abstract T save(T entity);

	public abstract void delete(Object id);

	public abstract T update(T entity);

	public abstract T find(K entityID);

	public abstract T findReferenceOnly(K entityID);

	public abstract List<T> listAll();
	
	public List<T> listAll(Integer maxResults, Integer page);
	
	public abstract List<T> list(T entity);

}