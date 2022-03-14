import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.sql.CallableStatement;

public class Activitat {

    private static ConnectBD conn = new ConnectBD();

    private String nom;

    private double aforament;

    private String inscrits;

    private Connection connexioBD;

    public ArrayList<Activitat> mostrarActivitats() throws SQLException {
        ArrayList<Activitat> activitats = new ArrayList<>();

        this.connexioBD = conn.getConnexioBD();

        CallableStatement SQL = connexioBD.prepareCall("{call consultarActivitatsMesReservades()}");
        ResultSet rs = SQL.executeQuery();

        while (rs.next()) {
            Activitat c2 = new Activitat();
            c2.afegirDadesActivitat(rs);
            activitats.add(c2);
        }

        return activitats;
    }

    private Activitat afegirDadesActivitat(ResultSet rs) throws SQLException {
        this.setNom(rs.getString("_activitats"));
        this.setAforament(rs.getInt("_aforament"));
        this.setInscrits(rs.getString("_count"));
        return this;

    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setAforament(double aforament) {
        this.aforament = aforament;
        String.format("%.01f", this.aforament);
    }

    public void setInscrits(String inscrits) {
        this.inscrits = inscrits;
    }

    @Override
    public String toString() {
        return "nom= " + nom + " aforament=" + aforament + "%" + ", inscrits=" + inscrits + "";
    }

}
