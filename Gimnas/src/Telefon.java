import java.util.regex.*;

public class Telefon {

    private static String telefon;

    public Telefon(String telefon) {
        this.telefon = telefon;
    }

    public Telefon() {
        this.telefon = telefon;
    }

    public boolean validar() {

        Pattern p = Pattern.compile("^\\d{9}$");

        Matcher m = p.matcher(telefon);

        return (m.matches());
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    @Override
    public String toString() {
        return telefon;
    }

    public static String getTelefon() {
        return telefon;
    }

}