package findbusweb.infra;

import java.io.Serializable;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import findbusweb.infra.annotations.ExceptionInterceptor;
import findbusweb.util.FacesUtil;

@Interceptor
@ExceptionInterceptor
public class ExceptionInterceptorImpl implements Serializable {

	private static final long serialVersionUID = -1505124359203621419L;

	Logger log = LoggerFactory.getLogger(ExceptionInterceptorImpl.class);

	@AroundInvoke
	public Object configura(InvocationContext ctx) throws Exception {
		try {
			Object obj = ctx.proceed();
			//new Exception().printStackTrace();
			return obj;
		} catch (Exception e) {
			FacesUtil.addError(e.getMessage());
			return null;
		}
	}

}