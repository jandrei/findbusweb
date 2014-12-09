package findbusweb.infra;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;

import findbusweb.infra.annotations.TransacaoInterceptor;

public abstract class BaseDao<T, K> implements Dao<T, K> {
	private static final long serialVersionUID = 1L;

	@DataRepository
	@Inject
	private EntityManager em;

	private Class<T> entityClass;

	/*
	 * (non-Javadoc)
	 * 
	 * @see laboratorio.infra.DAO#beginTransaction()
	 */
	public void beginTransaction() {
		em.getTransaction().begin();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see laboratorio.infra.DAO#commit()
	 */
	public void commit() {
		em.getTransaction().commit();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see laboratorio.infra.DAO#rollback()
	 */
	public void rollback() {
		em.getTransaction().rollback();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see laboratorio.infra.DAO#closeTransaction()
	 */
	public void closeTransaction() {
		em.close();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see laboratorio.infra.DAO#commitAndCloseTransaction()
	 */
	public void commitAndCloseTransaction() {
		commit();
		closeTransaction();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see laboratorio.infra.DAO#flush()
	 */
	public void flush() {
		em.flush();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see laboratorio.infra.DAO#joinTransaction()
	 */
	public void joinTransaction() {
		em.joinTransaction();
	}

	public BaseDao(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see laboratorio.infra.DAO#save(T)
	 */
	@TransacaoInterceptor
	public T save(T entity) {
		em.persist(entity);

		return entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see laboratorio.infra.DAO#delete(java.lang.Object, java.lang.Class)
	 */
	@TransacaoInterceptor
	public void delete(Object id) {
		T entity = em.getReference(entityClass, id);

		em.remove(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see laboratorio.infra.DAO#update(T)
	 */
	@TransacaoInterceptor
	public T update(T entity) {
		return em.merge(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see laboratorio.infra.DAO#find(K)
	 */
	public T find(K entityID) {
		String simpleName = entityClass.getSimpleName();
		Query query = em.createNamedQuery(simpleName + ".consulta");
		query.setParameter("id", entityID);

		return (T) query.getSingleResult();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see laboratorio.infra.DAO#findReferenceOnly(K)
	 */
	public T findReferenceOnly(K entityID) {
		return em.getReference(entityClass, entityID);
	}

	public List<T> listAll() {
		return listAll(null, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see laboratorio.infra.DAO#findAll()
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<T> listAll(Integer maxResults, Integer page) {
		DetachedCriteria dc = DetachedCriteria.forClass(entityClass);

		addOrdem(dc);

		Criteria cr = dc.getExecutableCriteria((Session) em.getDelegate());

		if (maxResults != null && page != null) {
			cr.setFirstResult((page - 1) * maxResults);
			cr.setMaxResults(maxResults);
		}

		return cr.list();
	}

	public void addOrdem(DetachedCriteria dc) {

	}

	public List<T> list(T entity) {
		return list(entity, null);
	}

	public List<T> list(T entity, Integer maxResults) {

		DetachedCriteria dc = DetachedCriteria.forClass(entityClass);
		dc.add(Example.create(entity).enableLike().ignoreCase());
		addOrdem(dc);

		Criteria cr = dc.getExecutableCriteria((Session) em.getDelegate());
		if (maxResults != null) {
			cr.setMaxResults(maxResults);
		}
		return cr.list();
	}

	@SuppressWarnings("unchecked")
	protected T findOneResult(String namedQuery, Map<String, Object> parameters) {
		T result = null;

		try {
			Query query = em.createNamedQuery(namedQuery);

			if (parameters != null && !parameters.isEmpty()) {
				populateQueryParameters(query, parameters);
			}

			result = (T) query.getSingleResult();

		} catch (NoResultException e) {
			System.out
					.println("No result found for named query: " + namedQuery);
		} catch (Exception e) {
			System.out.println("Error while running query: " + e.getMessage());
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}

		return result;
	}

	private void populateQueryParameters(Query query,
			Map<String, Object> parameters) {
		for (Entry<String, Object> entry : parameters.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
	}
	
	public Session getSession(){
		return (Session) em.getDelegate();
	}
}
