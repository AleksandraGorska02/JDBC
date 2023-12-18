/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package org.example;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alxandra
 */
public class Lab_JDBC {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {

        Connection conn = null;
        String connectionString =
                ""+
                        "";
        Properties connectionProps = new Properties();
        connectionProps.put("user", "user");
        connectionProps.put("password", "password");
        try {
            conn = DriverManager.getConnection(connectionString,
                    connectionProps);
            System.out.println("Połączono z bazą danych");
        } catch (SQLException ex) {
            Logger.getLogger(Lab_JDBC.class.getName()).log(Level.SEVERE,
                    "Nie udało się połączyć z bazą danych", ex);
            System.exit(-1);
        }



        try {

            conn.close();
        } catch (SQLException e) {
            Logger.getLogger(Lab_JDBC.class.getName()).log(Level.SEVERE,null, e);
        }
        System.out.println("Rozłączono z bazą danych");


    }
     void zadanie1(Connection conn) throws SQLException {
         try (Statement stmt = conn.createStatement();
              ResultSet rs = stmt.executeQuery(
                      "SELECT id_prac, RPAD(nazwisko, 15) AS nazwisko, id_zesp FROM pracownicy")) {

             int liczbaPracownikow = 0;
             Map<String, String> pracownicyIZespoly = new HashMap<>();

             while (rs.next()) {
                 String nazwisko = rs.getString("nazwisko");
                 String idZespolu = rs.getString("id_zesp");

                 pracownicyIZespoly.put(nazwisko, idZespolu);

                 liczbaPracownikow++;
             }

             System.out.println("Zatrudniono " + liczbaPracownikow + " pracowników, w tym:");

             Map<String, Integer> liczbaPracownikowWZespole = new HashMap<>();

             for (Map.Entry<String, String> entry : pracownicyIZespoly.entrySet()) {
                 String zespol = entry.getValue();

                 liczbaPracownikowWZespole.put(zespol, liczbaPracownikowWZespole.getOrDefault(zespol, 0) + 1);


             }

             for (Map.Entry<String, Integer> entry : liczbaPracownikowWZespole.entrySet()) {
                 String zespol = entry.getKey();
                 int liczbaPracownikowWZespole1 = entry.getValue();

                 System.out.println(liczbaPracownikowWZespole1 + "  w zespole " + zespol);
             }

         } catch (SQLException ex) {
             System.out.println("Błąd wykonania polecenia: " + ex.getMessage());
         }
    }
    void zadanie2(Connection conn) throws SQLException {  try (Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                                                               ResultSet rs = stmt.executeQuery(
                                                                       "select id_prac, nazwisko, placa_pod,etat from pracownicy where etat='ASYSTENT' ORDER BY placa_pod DESC"
                                                               );)
    {
        while (rs.next()) {
            System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " +
                    rs.getFloat(3)+ " " + rs.getString(4));
        }
        rs.previous();
        System.out.println("ostatni asystent: " + rs.getInt(1) + " " + rs.getString(2) + " " +
                rs.getFloat(3)+ " " + rs.getString(4));
        rs.previous();
        rs.previous();
        System.out.println("trzeci najmniej zarabiajacy: " + rs.getInt(1) + " " + rs.getString(2) + " " +
                rs.getFloat(3)+ " " + rs.getString(4));

        rs.next();
        System.out.println("przedostatni asystent: " + rs.getInt(1) + " " + rs.getString(2) + " " +
                rs.getFloat(3)+ " " + rs.getString(4));

    } catch (SQLException ex) {
        System.out.println("Błąd wykonania polecenia: " + ex.getMessage());
    }}

    void zadanie3(Connection conn) throws SQLException {


        int[] zwolnienia = {150, 200, 230};
        String[] zatrudnienia = {"Kandefer", "Rygiel", "Boczar"};

        for (int i = 0; i < zwolnienia.length; i++) {
            int idZwolnienia = zwolnienia[i];

            try (PreparedStatement pstmt = conn.prepareStatement("DELETE FROM pracownicy WHERE id_prac = ?")) {
                pstmt.setInt(1, idZwolnienia);
                pstmt.executeUpdate();
                System.out.println("Zwolniono pracownika o ID: " + idZwolnienia);


            } catch (SQLException ex) {
                System.out.println("Błąd zwalniania pracownika: " + ex.getMessage());
            }
        }

        int startingId = 240;
        int step = 10;

        for (int i = 0; i < zatrudnienia.length; i++) {
            int idNowegoPracownika = startingId + (i * step);
            String nowyPracownik = zatrudnienia[i];

            try (PreparedStatement pstmt = conn.prepareStatement("INSERT INTO pracownicy (id_prac, nazwisko) VALUES (?, ?)")) {
                pstmt.setInt(1, idNowegoPracownika);
                pstmt.setString(2, nowyPracownik);
                pstmt.executeUpdate();
                System.out.println("Zatrudniono nowego pracownika: " + nowyPracownik + " z ID: " + idNowegoPracownika);
            } catch (SQLException ex) {
                System.out.println("Błąd zatrudniania pracownika: " + ex.getMessage());
            }
        }





    }

    void zadanie4(Connection conn) throws SQLException {     conn.setAutoCommit(false);
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "select nazwa from etaty");) {
            while (rs.next()) {
                System.out.println(rs.getString(1) );
            }
        }
        catch (SQLException ex) {
            System.out.println("Błąd wykonania polecenia 1: " + ex.getMessage());
        }
        System.out.println("-----------------");
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("INSERT INTO etaty(nazwa) " +
                    "VALUES('nowy etat')");
        } catch (SQLException ex) {
            System.out.println("Błąd wykonania polecenia 2: " + ex.getMessage());
        }
        System.out.println("-----------------");
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "select nazwa from etaty");) {
            while (rs.next()) {
                System.out.println(rs.getString(1) );
            }
        }
        catch (SQLException ex) {
            System.out.println("Błąd wykonania polecenia 3: " + ex.getMessage());
        }
        System.out.println("-----------------");
        conn.rollback();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "select nazwa from etaty");) {
            while (rs.next()) {
                System.out.println(rs.getString(1) );
            }
        }
        catch (SQLException ex) {
            System.out.println("Błąd wykonania polecenia 4: " + ex.getMessage());
        }
        System.out.println("-----------------");
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("INSERT INTO etaty(nazwa) " +
                    "VALUES('nowy etat2')");
        } catch (SQLException ex) {
            System.out.println("Błąd wykonania polecenia 5: " + ex.getMessage());
        }

        conn.commit();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "select nazwa from etaty");) {
            while (rs.next()) {
                System.out.println(rs.getString(1) );
            }
        }
        catch (SQLException ex) {
            System.out.println("Błąd wykonania polecenia 6: " + ex.getMessage());
        }

    }
    void zadanie5(Connection conn) throws SQLException {        String [] nazwiska={"Woźniak", "Dąbrowski", "Kozłowski"};
        int [] place={1300, 1700, 1500};
        String []etaty={"ASYSTENT", "PROFESOR", "ADIUNKT"};


        for (int i = 0; i < nazwiska.length; i++) {
            String nazwisko = nazwiska[i];
            int placa = place[i];
            String etat = etaty[i];
            try (PreparedStatement pstmt = conn.prepareStatement("INSERT INTO pracownicy (id_prac,nazwisko, placa_pod, etat) VALUES (?,?, ?, ?)")) {
                pstmt.setInt(1, i+1);
                pstmt.setString(2, nazwisko);
                pstmt.setInt(3, placa);
                pstmt.setString(4, etat);
                pstmt.executeUpdate();
                System.out.println("Dodano pracownika: " + nazwiska[i] + " z pensją: " + place[i] + " i etatem: " + etaty[i]);
            } catch (SQLException ex) {
                System.out.println("Błąd dodawania pracownika: " + ex.getMessage());
            }


        }
        try (PreparedStatement pstmt = conn.prepareStatement("SELECT  nazwisko, placa_pod, etat FROM pracownicy");
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                System.out.println(rs.getString("nazwisko") + " " + rs.getInt("placa_pod") + " " + rs.getString("etat"));
            }
        }
        catch (SQLException ex) {
            System.out.println("Błąd wykonania polecenia 1: " + ex.getMessage());
        }}

    void zadanie6(Connection conn) throws SQLException { try (PreparedStatement pstmt = conn.prepareStatement(
            "INSERT INTO Pracownicy (id_prac,nazwisko, placa_pod) VALUES (?, ?, ?)")) {
        long start = System.nanoTime();
        for (int i = 500; i < 2500; i++) {

            pstmt.setInt(1, i);
            pstmt.setString(2, "Pracownik" + i);
            pstmt.setInt(3, 2000 + i);


            pstmt.executeUpdate();
        }
        long czas = System.nanoTime() - start;
        System.out.println("Czas wykonania 1: " + czas + " ns");
    }
    catch (SQLException ex) {
        System.out.println("Błąd dodawania pracownika: " + ex.getMessage());
    }

        try (PreparedStatement pstmt = conn.prepareStatement(
                "INSERT INTO Pracownicy (id_prac,nazwisko, placa_pod) VALUES (?, ?, ?)")) {
            long start = System.nanoTime();
            for (int i = 2600; i < 4600; i++) {
                pstmt.setInt(1, i);
                pstmt.setString(2, "Pracownik" + i);
                pstmt.setInt(3, 2000 + i);

                pstmt.addBatch();
            }   pstmt.executeBatch();
            long czas = System.nanoTime() - start;
            System.out.println("Czas wykonania 2: " + czas + " ns");
        }
        catch (SQLException ex) {
            System.out.println("Błąd dodawania pracownika: " + ex.getMessage());
        }


    }
    void zadanie7(Connection conn) throws SQLException {
        try (CallableStatement stmt = conn.prepareCall("{ ? = call ZmienWielkoscLiter(?, ?) }")) {

            stmt.setInt(2, 100);

            stmt.registerOutParameter(3, Types.VARCHAR);
            stmt.registerOutParameter(1, Types.NUMERIC);

            stmt.execute();

            int wynik = stmt.getInt(1);
            String noweNazwisko = stmt.getString(3);

            if (wynik == 1) {
                System.out.println("Pomyślnie zmieniono nazwisko. Nowe nazwisko: " + noweNazwisko);
            } else {
                System.out.println("Błąd: Niepoprawny identyfikator pracownika.");
            }
        }


        catch (SQLException ex) {
            System.out.println("Błąd " + ex.getMessage());
        }




    }
}
