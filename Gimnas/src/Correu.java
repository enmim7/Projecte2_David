import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Correu {

	private String correu;

	public Correu(String correu) {
		this.correu = correu;
	}

	public Correu() {
		this.correu = correu;
	}

	@Override
	public String toString() {
		return correu;
	}

	public void setCorreu(String correu) {
		this.correu = correu;
	}

	public boolean validar() {

		Pattern pattern = Pattern
				.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
						+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

		Matcher mather = pattern.matcher(correu);

		if (mather.find() == true) {
			return true;
		} else {
			return false;
		}

	}

}
