package dao;

import jdbc.Connector;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAO {

    private Connector connector = new Connector();

    /*
    not tested
     */
    public void create(String sql) {

        try {
            connector.getStatement().executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /*
    its working
     */
    public ResultSet read(String sql) {

        ResultSet rs = null;
        Object object;

        try {
            rs = connector.getStatement().executeQuery(sql);

            if (rs.first()) {
                return rs;
            } else {
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
    not tested
     */
    public void update(String sql) {
        try {
            connector.getStatement().executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
    not tested
     */
    public void delete(String sql) {
        try {
            connector.getStatement().executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
