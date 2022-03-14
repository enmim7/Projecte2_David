import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {
        Gimnas g = new Gimnas();
        ConnectBD connexio = new ConnectBD();
        connexio.connexio();
        g.gestioGimnas();
        connexio.tancarConnexioBD();
    }
}