package DataBase;

import GestPaletes.Palete;
import GestPaletes.QRCode;

import java.sql.*;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class QRCodeDAO implements Map<String, QRCode> {

    private static QRCodeDAO singleton = null;

    private static final String USERNAME = "g19";
    private static final String PASSWORD = "G19.1234567";
    private static final String CREDENTIALS = "?user="+USERNAME+"&password="+PASSWORD;
    private static final String DATABASE = "localhost:3306/SistemaArmazem";

    private QRCodeDAO() {

        try (Connection conn =
                     DriverManager.getConnection("jdbc:mysql://"+DATABASE+CREDENTIALS);
             Statement stm = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS qrCodes (" +
                    "Codigo varchar(10) NOT NULL PRIMARY KEY," +
                    "Produto varchar(45) NOT NULL)";
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
        try (Connection conn =
                     DriverManager.getConnection("jdbc:mysql://"+DATABASE+CREDENTIALS);
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
        try (Connection conn =
                     DriverManager.getConnection("jdbc:mysql://"+DATABASE+CREDENTIALS);
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

    @Override
    public boolean containsValue(Object value) {
        QRCode a = (QRCode) value;
        return this.containsKey(a.getCodQR());
    }

    @Override
    public QRCode get(Object key) {
        return null;
    }

    @Override
    public QRCode put(String key, QRCode value) {
        QRCode res = null;
        try (Connection conn =
                     DriverManager.getConnection("jdbc:mysql://"+DATABASE+CREDENTIALS);
             Statement stm = conn.createStatement()) {

            stm.executeUpdate(
                    "INSERT INTO qrcodes VALUES ('"+value.getCodQR()+"', '"+value.getProduto() +"') " +
                            "ON DUPLICATE KEY UPDATE Produto=VALUES(Produto)");


        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }

    @Override
    public QRCode remove(Object key) {
        QRCode t = this.get(key);
        try (Connection conn =
                     DriverManager.getConnection("jdbc:mysql://"+DATABASE+CREDENTIALS);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("DELETE FROM qrCodes WHERE Codigo='"+key+"'");
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return t;
    }

    @Override
    public void putAll(Map<? extends String, ? extends QRCode> qrcodes) {
        for(QRCode t : qrcodes.values()) {
            this.put(t.getCodQR(), t);
        }

    }

    @Override
    public void clear() {
        try (Connection conn =
                     DriverManager.getConnection("jdbc:mysql://"+DATABASE+CREDENTIALS);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("TRUNCATE qrCodes");
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }

    }

    @Override
    public Set<String> keySet() {
        throw new NullPointerException("Not implemented!");
    }

    @Override
    public Collection<QRCode> values() {
        return null;
    }

    @Override
    public Set<Entry<String, QRCode>> entrySet() {
        throw new NullPointerException("public Set<Map.Entry<String,QRCode>> entrySet() not implemented!");
    }

}