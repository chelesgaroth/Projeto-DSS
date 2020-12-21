package DataBase;

import GestPaletes.Palete;
import GestRobot.Vertice;
import GestPaletes.QRCode;


import java.sql.*;
import java.util.*;

public class PaleteDAO implements Map<String, Palete> {
    private static PaleteDAO singleton = null;


    private PaleteDAO() {

        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS vertices (" +
                    "Codigo varchar(10) NOT NULL PRIMARY KEY," +
                    "Designacao varchar(45) NOT NULL DEFAULT 'n/d'," +
                    "Ocupacao int(5) NOT NULL DEFAULT 0)";
            stm.executeUpdate(sql);

            sql = "CREATE TABLE IF NOT EXISTS paletes (" +
                    "Codigo varchar(10) NOT NULL PRIMARY KEY," +
                    "QRCode varchar(10) NOT NULL, foreign key(QRCode) references qrCodes(Codigo)," +
                    "InRobot int(1) NOT NULL DEFAULT 0," +
                    "Localizacao varchar (10)  DEFAULT 'Zona D',foreign key(Localizacao) references vertices(Codigo))";
            stm.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    /**
     * Implementação do padrão Singleton
     *
     * @return devolve a instância única desta classe
     */
    public static PaleteDAO getInstance() {
        if (PaleteDAO.singleton == null) {
            PaleteDAO.singleton = new PaleteDAO();
        }
        return PaleteDAO.singleton;
    }

    @Override
    public int size() {
        int i = 0;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT count(*) FROM paletes")) {
            if(rs.next()) {
                i = rs.getInt(1);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return i;
    }

    @Override
    public boolean isEmpty()  { return this.size() == 0; }

    @Override
    public boolean containsKey(Object key) {
        boolean r;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs =
                     stm.executeQuery("SELECT Codigo FROM paletes WHERE Codigo ='"+ key.toString()+"'")) {
            r = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }


    @Override
    public boolean containsValue(Object value) {
        Palete a = (Palete) value;
        return this.containsKey(a.getCodPalete());
    }

    public Vertice getVertice(String cod) throws SQLException {
        Vertice v = null;
        try(Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
            Statement stm = conn.createStatement();
            ResultSet reV = stm.executeQuery("SELECT * FROM vertices WHERE Codigo='"+cod+ "'")) {
            if (reV.next()) {  // A chave existe na tabela
                v = new Vertice(reV.getString("Codigo"), reV.getString("Designacao"), reV.getInt("Ocupacao"));
            }
        }
        return v;
    }
    @Override
    public Palete get(Object key) {
        Palete p = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT * FROM paletes WHERE Codigo='"+key+"'")) {
            if (rs.next()) {
                // Reconstruir Vertice
                Vertice v = null;
                String sql = "SELECT * FROM vertices WHERE Codigo='"+rs.getString("Localizacao")+"'";
                v = getVertice(rs.getString("Localizacao"));
                //Reconstruir QRCode
                QRCode q = null;
                String sql1 = "SELECT * FROM qrCodes WHERE Codigo='"+rs.getString("QRCode")+"'";

                try (Connection conn2 = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
                     Statement stm2 = conn2.createStatement();
                     ResultSet rsb = stm2.executeQuery(sql1)) {
                    if (rsb.next()) {
                        q = new QRCode(rs.getString("Codigo"),
                                rsb.getString("Produto"));
                    } else {
                        //BD inconsistente!! QRCode não existe - tratar com excepções.
                    }
                }

                // Reconstruir a turma com os dados obtidos da BD
                p = new Palete(q,rs.getString("Codigo"),rs.getInt("InRobot") ,v);
            }
        }
        catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return p;
    }



    @Override
    public Palete put(String s, Palete palete) {
        Palete res = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
                stm.executeUpdate(
                        "INSERT INTO paletes VALUES ('" + palete.getCodPalete() + "','" + palete.getCodQR().getCodQR() + "','" + palete.getInRobot() + "','" + palete.getLocalizacao().getCodVertice() + "') " +
                                "ON DUPLICATE KEY UPDATE InRobot=VALUES(InRobot) , Localizacao=VALUES(Localizacao)");

        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }


    @Override
    public Palete remove(Object key) {
        Palete t = this.get(key);
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("DELETE FROM paletes WHERE Codigo='"+key+"'");
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return t;
    }


    @Override
    public void putAll(Map<? extends String, ? extends Palete> paletes) {
        for(Palete p : paletes.values()) {
            this.put(p.getCodPalete(), p);
        }
    }

    @Override
    public void clear() {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");
            stm.executeUpdate("TRUNCATE arestasRotas");
            stm.executeUpdate("TRUNCATE rotas");
            stm.executeUpdate("TRUNCATE paletes");
            stm.executeUpdate("SET FOREIGN_KEY_CHECKS = 1");
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }

    }

    @Override
    public Set<String> keySet() {
        Set<String> col = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT Codigo FROM paletes")) {
            while (rs.next()) {
                col.add(rs.getString("Codigo"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return col;
    }


    @Override
    public Collection<Palete> values() {
        Collection<Palete> col = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT Codigo FROM paletes")) {
            while (rs.next()) {
                col.add(this.get(rs.getString("Codigo")));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return col;
    }

    @Override
    public Set<Entry<String, Palete>> entrySet() {
        Map.Entry<String,Palete> entry;
        HashSet<Entry<String, Palete>> col = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT Codigo FROM paletes")) {
            while (rs.next()) {
                entry =  new AbstractMap.SimpleEntry<>(rs.getString("Codigo"),this.get(rs.getString("Codigo")));
                col.add(entry);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return col;
    }

}