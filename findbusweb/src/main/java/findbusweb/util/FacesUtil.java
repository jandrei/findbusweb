package findbusweb.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class FacesUtil {

	public static void addInfo(String mensagem) {
		FacesContext.getCurrentInstance().addMessage("form1:msgs", new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação", mensagem));
	}

	public static void addWarn(String mensagem) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", mensagem));
	}

	public static void addError(String mensagem) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", mensagem));
	}

	public static void addFatal(String mensagem) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Fatal", mensagem));
	}

}
