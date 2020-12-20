package DataBase;

import GestRobot.Vertice;

import java.sql.*;
import java.util.*;

public class VerticeDAO implements Map<String,Vertice>{
    private static VerticeDAO singleton = null;

    private VerticeDAO() {

        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS vertices (" +
                    "Codigo varchar(10) NOT NULL PRIMARY KEY," +
                    "Designacao varchar(45) NOT NULL DEFAULT 'n/d'," +
                    "Ocupacao int(5) NOT NULL DEFAULT 0)";
            stm.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }


    public static VerticeDAO getInstance() {
        if (VerticeDAO.singleton == null) {
            VerticeDAO.singleton = new VerticeDAO();
        }
        return VerticeDAO.singleton;
    }



    public int size() {
        int i = 0;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT count(*) FROM vertices")) {
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


    public boolean isEmpty() {
        return this.size() == 0;
    }


    public boolean containsKey(Object key) {
        boolean r;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs =
                     stm.executeQuery("SELECT Codigo FROM vertices WHERE Codigo ='"+key.toString()+"'")) {
            r = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }


    public boolean containsValue(Object value) {
        Vertice a = (Vertice) value;
        return this.containsKey(a.getCodVertice());
    }


    public Vertice get(Object key) {
        Vertice a = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT * FROM vertices WHERE Codigo='"+key+"'")) {
            if (rs.next()) {  // A chave existe na tabela
                a = new Vertice(rs.getString("Codigo"), rs.getString("Designacao"), rs.getInt("Ocupacao"));
            }
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return a;
    }


    public Vertice put(String key, Vertice value) {
        Vertice res = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
                // Actualizar o aluno
                stm.executeUpdate(
                        "INSERT INTO vertices VALUES ('" + value.getCodVertice() + "', '" + value.getDesignacao() + "', '" + value.getOcupacao() + "') " +
                                "ON DUPLICATE KEY UPDATE Codigo=VALUES(Codigo), Designacao=VALUES(Designacao), Ocupacao=VALUES(Ocupacao)");

        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }


    public Vertice remove(Object key) {
        Vertice t = this.get(key);
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("DELETE FROM vertices WHERE Codigo='"+key+"'");
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return t;
    }


    public void putAll(Map<? extends String, ? extends Vertice> vertices) {
        for(Vertice t : vertices.values()) {
            this.put(t.getCodVertice(), t);
        }
    }


    public void clear() {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("UPDATE paletes SET Localizacao= NULL");
            stm.executeUpdate("UPDATE robots SET Localizacao= NULL");
            stm.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");
            stm.executeUpdate("TRUNCATE arestasRotas");
            stm.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");
            stm.executeUpdate("TRUNCATE rotas");
            stm.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");
            stm.executeUpdate("TRUNCATE arestas");
            stm.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");
            stm.executeUpdate("TRUNCATE vertices");
            stm.executeUpdate("SET FOREIGN_KEY_CHECKS = 1");
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }

    }

    public Set<String> keySet() {
        Set<String> col = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT Codigo FROM vertices")) {
            while (rs.next()) {
                col.add(rs.getString("Codigo"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return col;
    }

    public Collection<Vertice> values() {
        Collection<Vertice> col = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT Codigo FROM vertices")) {
            while (rs.next()) {
                col.add(this.get(rs.getString("Codigo")));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return col;
    }


    public Set<Map.Entry<String, Vertice>> entrySet() {
        Map.Entry<String,Vertice> entry;
        HashSet<Map.Entry<String, Vertice>> col = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT Codigo FROM vertices")) {
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