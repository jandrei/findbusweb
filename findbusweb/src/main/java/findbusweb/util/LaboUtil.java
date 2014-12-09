package findbusweb.util;

import java.security.MessageDigest;

import javax.inject.Named;

@Named
public class LaboUtil {
	public void sysout(String valor) {
		System.out.println(valor);
	}

	public static String getMD5(String valor) {
		if (valor == null) {
			throw new RuntimeException("Valor do getMD5 não poder ser nullo");
		}
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(valor.getBytes());
			return String.valueOf(md.digest());
		} catch (Exception e) {
			return "";
		}
	}

}
