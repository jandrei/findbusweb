package findbusweb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.apache.deltaspike.core.api.scope.ViewAccessScoped;

@Named
@ViewAccessScoped
public class HomeMB implements Serializable {
	private static final long serialVersionUID = -4322633329714127833L;

	private String mensagem = "digite algo";

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public void action() {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(getMensagem()));
	}

	public String getVariaveisAmbiente() {
		String teste = "<table border=\"1\">";
		List<Object> ids = new ArrayList<Object>(System.getProperties()
				.keySet());

		for (Object object : ids) {
			teste += "<tr><td style='text-align:right;'>"+object + " = </td><td>" + System.getProperty(object.toString())+"</td></tr>"					;
		}

		return "";// teste+"</table>";
	}

}
