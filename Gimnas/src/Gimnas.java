import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Gimnas {

    private static Client cl = new Client();

    private static Activitat a1 = new Activitat();

    private static String nom;

    private static String CIF;

    private static String telefon;

    private static boolean sortir = false;

    private static boolean sortir2 = false;

    private static ArrayList<Client> clients;

    private static ArrayList<Activitat> activitats;

    static Scanner sc = new Scanner(System.in);

    /**
     *
     * @throws SQLException
     */
    public void gestioGimnas() throws SQLException {
        do {
            System.out.println("**************Gimnas*****************");
            System.out.println("1. Gestio client");
            System.out.println("2. Sortir");

            String sa = sc.next();
            char opcio = sa.charAt(0);

            switch (opcio) {
                case '1':
                    subClient();
                    break;
                case '2':
                    System.out.println("sortir");
                    sortir = true;
                    break;
                default:
                    System.out.println("Opcio no valida");
            }
        } while (!sortir);
    }

    private void subClient() throws SQLException {

        do {
            System.out.println("**************Gimnas*****************");
            System.out.println("1. Mostrar tots els clients amb informació");
            System.out.println("2. Mostrar dades de un client determinat");
            System.out.println("3. Alta de un client");
            System.out.println("4. Torna enrere");

            String sa1 = sc.next();
            char opcio2 = sa1.charAt(0);

            switch (opcio2) {
                case '1':

                    if (clients != null) {
                        clients.clear();
                    }

                    Gimnas.clients = cl.mostrarTotsClients();
                    recorrerArrayClient();
                    sortir2 = false;
                    break;
                case '2':

                    cl.consultaClient();
                    sortir2 = false;
                    break;
                case '3':

                    System.out.println("Alta client");
                    cl.altaClient();
                    sortir2 = false;
                    break;
                case '4':
                    System.out.println("sortir");
                    sortir2 = true;
                    break;
                default:
                    System.out.println("Opcio no valida");
            }
        } while (!sortir2);

    }

    private void recorrerArrayClient() {
        for (Client c1 : clients) {
            System.out.println(c1);
        }
    }

}
