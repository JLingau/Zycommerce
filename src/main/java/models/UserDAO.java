package models;

import org.apache.commons.codec.digest.DigestUtils;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import utils.DBManager;

public class UserDAO {
    private DBManager db = new DBManager();
    private Sql2o sql2o;

    public UserDAO() {
        this.sql2o = db.getDb();
    }

    public void addUser(UserModel user) {
        String sql = "INSERT INTO users (username, password, privilege, status, fullname, email, phone) " +
                "VALUES (:username, :password, :privilege, :status, :fullname, :email, :phone)";

        try (Connection con = sql2o.open()) {
            con.createQuery(sql).addParameter("username", user.getUsername())
                    .addParameter("password", user.getPassword())
                    .addParameter("privilege", user.getPrivilege())
                    .addParameter("status", user.getStatus())
                    .addParameter("fullname", user.getFullname())
                    .addParameter("email", user.getEmail())
                    .addParameter("phone", user.getPhone())
                    .executeUpdate();
        }
    }

    public UserModel getUser(UserModel user) {
        String sql = "SELECT * FROM users WHERE username = :username";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).addParameter("username", user.getUsername()).executeAndFetchFirst(UserModel.class);
        }
    }
}
