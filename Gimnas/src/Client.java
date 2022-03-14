
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {

    private static ConnectBD conn = new ConnectBD(); // Connexió BD

    static Scanner sc = new Scanner(System.in); // Scanner

    // Atributs Client

    private DNI dni;

    private String nom;

    private String cognom;

    private LocalDate data_naix;

    private Telefon telefon;

    private Correu Correu;

    private IBAN IBAN;

    private int edat;

    private String login;

    private String quantitatReserves;

    private String comunicacio;

    private String sexe;

    static Connection connexioBD;

    // Alta dels clients

    public void altaClient() throws SQLException {

        do {
            System.out.println("Fica el nom del client");
            nom = sc.nextLine();
        } while (nom.length() == 0); // Si el nom no te valor return
        setNom(nom);

        do {
            System.out.println("Fica el cognom del client");
            cognom = sc.nextLine();
        } while (cognom.length() == 0); // Si el cognom no te valor return
        setCognom(cognom);

        boolean correcto = false;

        do {
            System.out.println("Fica la Data de naixament en format 0000-00-00 de client");
            String dateString = sc.next();
            SimpleDateFormat DateFor = new SimpleDateFormat("yyyy-M-d");
            DateFor.setLenient(false);
            try {
                DateFor.parse(dateString);
                data_naix = LocalDate.parse(dateString);
                correcto = false;
            } catch (ParseException e) {
                correcto = true;
                System.out.println(dateString + " El format incorrecte");
            }

        } while (correcto != false);

        setData_naix(data_naix);

        sc.nextLine();

        Correu validadorCorreu = new Correu();

        String Correu;

        do {
            System.out.println("Fica el Correu");
            Correu = sc.nextLine();
            validadorCorreu = new Correu(Correu);

        } while (!validadorCorreu.validar());

        validadorCorreu.setCorreu(Correu);
        setCorreu(validadorCorreu);

        String dni;
        DNI validadorDni = new DNI();
        Client a;
        do {
            do {
                System.out.println("Fica el DNI de client");
                dni = sc.nextLine();
                validadorDni = new DNI(dni);

            } while (!validadorDni.validar());

            a = consultaClientDNI(dni);

            if (a != null) {
                System.out.println("aquet dni existeix");

            } else {
                System.out.println("aquet dni no existeix");
                validadorDni.setCuenta(dni);
                setDni(validadorDni);
            }
        } while (a != null);
        String login2;
        do {
            System.out.println("Fica el Login");

            login2 = sc.nextLine();
            a = consultaClientLogin(login2);
            if (a != null) {
                System.out.println("aquet login existeix");
            } else {
                System.out.println("aquet login no existeix");
            }

        } while (a != null);

        setLogin(login2);

        Telefon validadorTelefon = new Telefon();

        String numero;
        do {
            System.out.println("Fica el telefon de client");
            numero = sc.nextLine();
            validadorTelefon = new Telefon(numero);

        } while (!validadorTelefon.validar());

        validadorTelefon.setTelefon(numero);
        setTelefon(validadorTelefon);

        IBAN validadorIBAN = new IBAN();

        String cuenta;

        do {
            System.out.println("Fica el IBAN");
            cuenta = sc.nextLine();
            validadorIBAN = new IBAN(cuenta);

        } while (!validadorIBAN.validar());

        validadorIBAN.setCuenta(cuenta);
        setIBAN(validadorIBAN);

        do {
            System.out.println("Fica el sexe F o M");
            ;
            sexe = sc.nextLine();

        } while (sexe == "F" | sexe == "M");
        setSexe(sexe);

        do {
            System.out.println("Vols rebre Comunicacio (Si o No)");

            comunicacio = sc.nextLine();

        } while (comunicacio == "Si" | comunicacio == "No");
        setComunicacio(comunicacio);

        this.connexioBD = conn.getConnexioBD();

        CallableStatement SQL = connexioBD.prepareCall("{call altaClient(?,?,?,?,?,?,?,?,?,?)}");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");
        String data_format = data_naix.format(formatter);

        SQL.setString(1, dni);
        SQL.setString(2, nom);
        SQL.setString(3, cognom);
        SQL.setString(4, data_format);
        String telefon = Telefon.getTelefon();
        SQL.setString(5, telefon);
        SQL.setString(6, Correu);
        String iban = IBAN.getCuenta();
        SQL.setString(7, iban);
        SQL.setString(8, login);
        SQL.setString(9, sexe);
        SQL.setString(10, comunicacio);
        SQL.executeUpdate();

    }

    // Consulta client

    public void consultaClient() throws SQLException {
        System.out.println("Fica el dni del client");
        String dni = sc.nextLine();

        Client a = consultaClientDNI(dni);
        if (a != null) {
            System.out.println(a.toString());
        } else {
            System.out.println("No hi ha cap client amb aquet DNI " + dni);
        }
    }

    public Client consultaClientDNI(String dni) throws SQLException {

        this.connexioBD = conn.getConnexioBD();

        String SQL = "SELECT * FROM clients where dni = ?";

        PreparedStatement ps = connexioBD.prepareStatement(SQL);
        ps.setString(1, dni);
        ps.executeQuery();
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            afegirDadesClient(rs);
            return this;
        }
        return null;
    }

    public Client consultaClientLogin(String login) throws SQLException {

        this.connexioBD = conn.getConnexioBD();

        String SQL = "SELECT * FROM clients where login = ?";

        PreparedStatement ps = connexioBD.prepareStatement(SQL);
        ps.setString(1, login);
        ps.executeQuery();
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            afegirDadesClient(rs);
            return this;
        }
        return null;
    }

    @Override
    public String toString() {
        if (quantitatReserves == null) {
            return "nom=" + nom + " DNI=" + dni + ", cognom=" + cognom + ", login=" + login + ", telefon="
                    + telefon + ", sexe=" + sexe + ", data_naix=" + data_naix + ", edat " + edat + ", IBAN=" + IBAN
                    + ", correu=" + Correu
                    + ",";
        }
        return "nom=" + nom + " DNI=" + dni + ", cognom=" + cognom + ", login=" + login + ", telefon="
                + telefon + ", sexe=" + sexe + ", data_naix=" + data_naix + ", edat " + edat + ", IBAN=" + IBAN
                + ", Reserves=" + quantitatReserves + ",";
    }

    public ArrayList<Client> consultaClientsMesReserves() throws SQLException {
        ArrayList<Client> clients = new ArrayList<>();

        this.connexioBD = conn.getConnexioBD();

        CallableStatement SQL = connexioBD.prepareCall("{call consultaClientsMesReserves()}");
        ResultSet rs = SQL.executeQuery();

        while (rs.next()) {
            setQuantitatReserves(rs.getString("_suma"));
            Client c2 = new Client();
            c2.afegirDadesClient(rs);
            clients.add(c2);

        }

        return clients;

    }

    public ArrayList<Client> mostrarTotsClients() throws SQLException {
        ArrayList<Client> clients = new ArrayList<>();
        String SQL = " ";
        System.out.println("1. Ordenar clients per edat");
        System.out.println("2. Ordenar clients per cognom i nom");
        System.out.println("3. Ordenar per mes reserves");
        int opcio;

        //Selects BD
        do {
            opcio = sc.nextInt();
            if (opcio == 1) {
                SQL = "SELECT * FROM clients order by data_naix asc";
            } else if (opcio == 2) {

                SQL = "SELECT * FROM clients order by nom asc, cognom asc";
            } else if (opcio == 3) {
                clients = consultaClientsMesReserves();
                return clients;
            }
        } while (opcio > 3);
        sc.nextLine();
        this.connexioBD = conn.getConnexioBD();

        PreparedStatement ps = connexioBD.prepareStatement(SQL);
        ps.executeQuery();
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            Client c2 = new Client();
            c2.afegirDadesClient(rs);
            clients.add(c2);

        }

        return clients;

    }

    private Client afegirDadesClient(ResultSet rs) throws SQLException {

        try {
            rs.getString("_suma");
            this.setQuantitatReserves(rs.getString("_suma"));
        } catch (Exception e) {

        }

        // Result Set BD

        this.setNom(rs.getString("nom"));
        this.setCognom(rs.getString("cognom"));
        this.setData_naix(rs.getDate("data_naix").toLocalDate());
        this.setLogin(rs.getString("login"));
        this.setSexe(rs.getString("sexe"));
        this.setDni(new DNI(rs.getString("dni")));
        this.setTelefon(new Telefon(rs.getString("telefon")));
        this.setCorreu(new Correu(rs.getString("correu")));
        this.setIBAN(new IBAN(rs.getString("IBAN")));
        this.setEdat(rs.getDate("data_naix").toLocalDate());
        return this;

    }

    // Setters and getters

    public void setDni(DNI dni) {
        this.dni = dni;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setQuantitatReserves(String quantitatReserves) {
        this.quantitatReserves = quantitatReserves;
    }

    public void setCognom(String cognom) {
        this.cognom = cognom;
    }

    public void setEdat(LocalDate data_naix) {
        LocalDate ahora = LocalDate.now();
        Period periodo = Period.between(data_naix, ahora);
        edat = periodo.getYears();

    }

    public void setData_naix(LocalDate data_naix) {
        this.data_naix = data_naix;
    }

    public void setTelefon(Telefon telefon2) {
        this.telefon = telefon2;
    }

    public void setCorreu(Correu correu) {
        this.Correu = correu;
    }

    public void setIBAN(IBAN IBAN) {
        this.IBAN = IBAN;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public void setComunicacio(String comunicacio) {
        this.comunicacio = comunicacio;
    }

    public Telefon getTelefon() {
        return telefon;
    }

}
