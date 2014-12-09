package findbusweb.infra;

import java.io.Serializable;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import findbusweb.infra.annotations.TransacaoInterceptor;

@Interceptor
@TransacaoInterceptor
public class TransacaoInterceptorImpl implements Serializable {

	private static final long serialVersionUID = -1505124359203621419L;

	Logger log = LoggerFactory.getLogger(TransacaoInterceptorImpl.class);
	
	@DataRepository
	@Inject
	private EntityManager em;

	@AroundInvoke
	public Object configura(InvocationContext ctx) throws Exception {
		boolean active = em.getTransaction().isActive();

		try {
			if (!active) {
				em.getTransaction().begin();
				log.info("iniciando transacao em " + ctx.getTarget().getClass().getCanonicalName() + "." + ctx.getMethod().getName());
			}else{
				log.info("abortando inicio de = " + ctx.getTarget().getClass().getCanonicalName() + "." + ctx.getMethod().getName());
			}

			Object obj = ctx.proceed();

			if (!active) {
				em.getTransaction().commit();
				log.info("comitando transacao em " + ctx.getTarget().getClass().getCanonicalName() + "." + ctx.getMethod().getName());
			}

			return obj;
		} catch (Exception e) {
			if (!active) {
				em.getTransaction().rollback();
				log.info("rollback transacao em " + ctx.getTarget().getClass().getCanonicalName() + "." + ctx.getMethod().getName());
			}
			throw e;
		}
	}

}