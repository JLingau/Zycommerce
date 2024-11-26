package utils;

import org.sql2o.Sql2o;

public class DBManager {
    private static Sql2o db;

    static {
        String dbUser = "root";
        String dbPassword = "";
        String dbIP = "127.0.0.1";
        String dbPort = "3306";
        String dbName = "zycommerce";
        String dbUrl = "jdbc:mysql://" + dbIP + ":" + dbPort + "/" + dbName;
        db = new Sql2o(dbUrl, dbUser, dbPassword);
    }

    public Sql2o getDb() {
        return db;
    }
}
