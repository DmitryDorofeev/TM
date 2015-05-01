package server.db;

import shared.Task;
import shared.User;

import java.security.MessageDigest;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * Created by dmitry on 06.04.15.
 */
public class DataBaseService {

    private static DataBaseService instance = null;

    private DataBaseService() {}

    public Connection connect() {
        try {
            Locale.setDefault(Locale.ENGLISH);
            DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.33.30:1521:XE", "system", "60850142");
            return conn;
        } catch (Exception e) {
            System.out.print(e.getMessage());
            return null;
        }
    }

    public ResultSet getQueryResult(Connection conn, String query, String param) {
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, param);
            return st.executeQuery();
        }
        catch (SQLException e) {
            return null;
        }
    }

    public static DataBaseService getInstance() {
        if (instance == null) {
            instance = new DataBaseService();
        }
        return instance;
    }

    /**
     *
     * @param password
     * @param salt
     * @return encoded password
     */
    public String encode(String password, String salt) throws Exception {
        String toEncode = password + salt;
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(toEncode.getBytes());
        byte[] digest = md.digest();

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < digest.length; i++) {
            sb.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }

    public String generateSalt() {
        char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        String output = sb.toString();
        return output;
    }

    public boolean addUser(String email, String password) throws Exception {
        String salt = this.generateSalt();
        String hashedPassword = this.encode(password, salt);

        Connection conn = DataBaseService.getInstance().connect();
        PreparedStatement st = conn.prepareStatement("INSERT INTO users (email, password, salt) VALUES (?, ?, ?)");
        st.setString(1, email);
        st.setString(2, hashedPassword);
        st.setString(3, salt);
        st.execute();
        conn.close();
        return true;
    }

    public User getUser(String email) throws Exception {
        Connection conn = DataBaseService.getInstance().connect();
        PreparedStatement st = conn.prepareStatement("SELECT * FROM users WHERE email = ?");
        st.setString(1, email);
        ResultSet rs = st.executeQuery();
        rs.next();
        User user = new User(email);
        user.setPassword(rs.getString("password"));
        user.setSalt(rs.getString("salt"));
        conn.close();
        return user;
    }

    public List<Task> getTasksList(User user) throws Exception {
        Connection conn = DataBaseService.getInstance().connect();
        PreparedStatement st = conn.prepareStatement("SELECT * FROM tasks WHERE email = ?");
        st.setString(1, user.email);
        ResultSet rs = st.executeQuery();
        List<Task> tasksList = new ArrayList<Task>();
        while (rs.next()) {
            tasksList.add(new Task(rs.getString("title")));
        }
        conn.close();
        return tasksList;
    }

    public Task addTask(String title, User user) throws Exception {
        Connection conn = DataBaseService.getInstance().connect();
        PreparedStatement st = conn.prepareStatement("INSERT INTO tasks (id, title, email) VALUES (tasks_seq.nextval, ?, ?)");
        st.setString(1, title);
        st.setString(2, user.email);
        st.execute();
        conn.close();
        return new Task(title);
    }
}
