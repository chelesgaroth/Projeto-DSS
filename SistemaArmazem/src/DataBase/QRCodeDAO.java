package DataBase;

import GestPaletes.QRCode;

import java.sql.*;
import java.util.*;

public class QRCodeDAO implements Map<String, QRCode> {

    private static QRCodeDAO singleton = null;


    private QRCodeDAO() {

        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS qrCodes (" +
                    "Codigo varchar(10) NOT NULL PRIMARY KEY," +
                    "Produto varchar(45) NOT NULL DEFAULT 'n/d')";
            stm.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }


    public static QRCodeDAO getInstance() {
        if (QRCodeDAO.singleton == null) {
            QRCodeDAO.singleton = new QRCodeDAO();
        }
        return QRCodeDAO.singleton;
    }



    public int size() {
        int i = 0;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT count(*) FROM qrCodes")) {
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
                     stm.executeQuery("SELECT Codigo FROM qrCodes WHERE Codigo ='"+key.toString()+"'")) {
            r = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }


    public boolean containsValue(Object value) {
        QRCode a = (QRCode) value;
        return this.containsKey(a.getCodQR());
    }


    public QRCode get(Object key) {
        QRCode a = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT * FROM qrCodes WHERE Codigo='"+key+"'")) {
            if (rs.next()) {  // A chave existe na tabela
                a = new QRCode(rs.getString("Codigo"), rs.getString("Produto"));
            }
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return a;
    }


    public QRCode put(String key, QRCode value) {
        QRCode res = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {

            // Actualizar o aluno
            stm.executeUpdate(
                    "INSERT INTO qrCodes VALUES ('"+value.getCodQR()+"', '"+value.getProduto()+"') " +
                            "ON DUPLICATE KEY UPDATE Codigo=VALUES(Codigo), Produto=VALUES(Produto)");
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }


    public QRCode remove(Object key) {
        QRCode t = this.get(key);
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("DELETE FROM qrCodes WHERE Codigo='"+key+"'");
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return t;
    }


    public void putAll(Map<? extends String, ? extends QRCode> qrcodes) {
        for(QRCode t : qrcodes.values()) {
            this.put(t.getCodQR(), t);
        }

    }


    public void clear() {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("UPDATE paletes SET QRCode=NULL");
            stm.executeUpdate("TRUNCATE qrCodes");
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
             ResultSet rs = stm.executeQuery("SELECT Codigo FROM qrCodes")) {
            while (rs.next()) {
                col.add(rs.getString("Codigo"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return col;
    }

    public Collection<QRCode> values() {
        Collection<QRCode> col = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT Codigo FROM qrCodes")) {
            while (rs.next()) {
                col.add(this.get(rs.getString("Codigo")));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return col;
    }


    public Set<Entry<String, QRCode>> entrySet() {
        Map.Entry<String,QRCode> entry;
        HashSet<Entry<String, QRCode>> col = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT Codigo FROM qrCodes")) {
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