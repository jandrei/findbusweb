package findbusweb.infra;

import java.io.Serializable;

import findbusweb.ed.BaseEntity;

public interface MB<T extends BaseEntity<K>, K> extends Serializable{

	public abstract Service<T, K> getService();

	public abstract T getEd();

	public abstract void setEd(T ed);

	public abstract void salvar();

	public abstract void excluir(T k);

}