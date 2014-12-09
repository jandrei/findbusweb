package findbusweb.ed;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public abstract class BaseEntity<K> implements Serializable {

	private static final long serialVersionUID = -3915013718564320358L;

	@Column(name = "txt_usu_inc", length = 100)
	private String usuarioInc;

	@Column(name = "dat_inc", length = 100)
	@Temporal(TemporalType.DATE)
	private Calendar dataCadastro;

	@Column(name = "txt_usu_atu", length = 100)
	private String usuarioAtu;

	@Column(name = "dat_atu", length = 100)
	@Temporal(TemporalType.DATE)
	private Calendar dataAtualizacao;

	public abstract K getId();

	public String getUsuarioInc() {
		return usuarioInc;
	}

	public void setUsuarioInc(String usuarioInc) {
		this.usuarioInc = usuarioInc;
	}

	public Calendar getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Calendar dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public String getUsuarioAtu() {
		return usuarioAtu;
	}

	public void setUsuarioAtu(String usuarioAtu) {
		this.usuarioAtu = usuarioAtu;
	}

	public Calendar getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(Calendar dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseEntity other = (BaseEntity) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}

}
