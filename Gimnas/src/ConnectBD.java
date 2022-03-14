import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectBD {

    private static Connection connexioBD = null;

    // Parametres de Connexió base de dades

    public void connexio() {

        String servidor = "jdbc:mysql://192.168.1.100/";
        String usuari = "dam";
        String passwd = "Fat/3232";
        String bbdd = "gimnas";

        try {
            connexioBD = DriverManager.getConnection(servidor + bbdd, usuari, passwd);
            System.out.println("Connexió amb èxit");

        } catch (SQLException e) {
            System.out.println("no funciona la connexio");
            e.printStackTrace();
        }
    }

    public static Connection getConnexioBD() {
        return connexioBD;
    }

    public void tancarConnexioBD() throws SQLException {
        connexioBD.close();
    }

}
