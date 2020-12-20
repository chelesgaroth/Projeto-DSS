package DataBase;

import GestRobot.Aresta;
import GestRobot.Vertice;


import java.sql.*;
import java.util.*;

public class ArestaDAO implements Map<String,Aresta>{
    private static ArestaDAO singleton = null;

    private ArestaDAO() {

        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS arestas (" +
                    "Codigo varchar(10) NOT NULL PRIMARY KEY," +
                    "Distancia float(10) NOT NULL DEFAULT 0," +
                    "VerticeInicial varchar(10) DEFAULT 'n/d', foreign key(VerticeInicial) references vertices(Codigo),"+
                    "VerticeFinal varchar(10) DEFAULT 'n/d', foreign key(VerticeFinal) references vertices(Codigo))";
            stm.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }


    public static ArestaDAO getInstance() {
        if (ArestaDAO.singleton == null) {
            ArestaDAO.singleton = new ArestaDAO();
        }
        return ArestaDAO.singleton;
    }



    public int size() {
        int i = 0;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT count(*) FROM arestas")) {
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
                     stm.executeQuery("SELECT Codigo FROM arestas WHERE Codigo ='"+key.toString()+"'")) {
            r = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }


    public boolean containsValue(Object value) {
        Aresta a = (Aresta) value;
        return this.containsKey(a.getCodAresta());
    }

    public Aresta get(Object key) {
        Aresta a = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT * FROM arestas WHERE Codigo='"+key+"'")) {
            if (rs.next()) {  // A chave existe na tabela
                Vertice vI = getVertice(rs.getString("VerticeInicial"));
                Vertice vF = getVertice(rs.getString("VerticeFinal"));
                a = new Aresta(vF,vI ,rs.getString("Codigo"), rs.getFloat("Distancia"));
            }
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return a;
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


    public Aresta put(String key, Aresta value) {
        Aresta res = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            if(this.containsKey(key)){
                res = this.get(key);
            }
            else {
                // Actualizar o aluno
                stm.executeUpdate(
                        "INSERT INTO arestas VALUES ('" + value.getCodAresta() + "', '" + value.getDist() + "', '" + value.getVerticeInicial().getCodVertice() + "', '" + value.getVerticeFinal().getCodVertice() + "', NULL) " +
                                "ON DUPLICATE KEY UPDATE Distancia=VALUES(Distancia), VerticeInicial=VALUES(VerticeInicial), VerticeFinal=VALUES(VerticeFinal)");
            }
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }


    public Aresta remove(Object key) {
        Aresta t = this.get(key);
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("DELETE FROM arestas WHERE Codigo='"+key+"'");
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return t;
    }


    public void putAll(Map<? extends String, ? extends Aresta> vertices) {
        for(Aresta t : vertices.values()) {
            this.put(t.getCodAresta(), t);
        }
    }


    public void clear() {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            stm.execute("UPDATE robots SET Rota=NULL");
            stm.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");
            stm.executeUpdate("TRUNCATE rotas");
            stm.executeUpdate("TRUNCATE arestasRotas");
            stm.executeUpdate("TRUNCATE arestas");
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
             ResultSet rs = stm.executeQuery("SELECT Codigo FROM arestas")) {
            while (rs.next()) {
                col.add(rs.getString("Codigo"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return col;
    }

    public Collection<Aresta> values() {
        Collection<Aresta> col = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT Codigo FROM arestas")) {
            while (rs.next()) {
                col.add(this.get(rs.getString("Codigo")));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return col;
    }


    public Set<Map.Entry<String, Aresta>> entrySet() {
        Map.Entry<String,Aresta> entry;
        HashSet<Map.Entry<String, Aresta>> col = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT Codigo FROM arestas")) {
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