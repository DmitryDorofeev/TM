package server.db;

import shared.Task;
import shared.User;

import java.security.MessageDigest;
import java.sql.*;
import java.util.*;

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
        char[] chars = "abcdefghijklmnopqrstuvwxyz1234567890".toCharArray();
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
        PreparedStatement pst = conn.prepareStatement("UPDATE tasks SET creation_date = CURRENT_TIMESTAMP WHERE email = ? and timespan = 'day' and to_char(CURRENT_TIMESTAMP,'YYYY-MM-DD') > to_char(creation_date,'YYYY-MM-DD') and opened = 1");
        pst.setString(1, user.email);
        pst.execute();
        PreparedStatement st = conn.prepareStatement("SELECT * FROM tasks WHERE email = ? and timespan = 'day' and to_char(CURRENT_TIMESTAMP,'YYYY-MM-DD') = to_char(creation_date,'YYYY-MM-DD')");
        st.setString(1, user.email);
        ResultSet rs = st.executeQuery();
        List<Task> tasksList = new ArrayList<Task>();
        while (rs.next()) {
            Timestamp time = rs.getTimestamp("creation_date");
            Task task = new Task(rs.getString("title"), rs.getInt("id"), rs.getBoolean("opened"), time.getTime());
            tasksList.add(task);
        }
        conn.close();
        return tasksList;
    }

    public List<Task> getLongTasksList(User user, String time) throws Exception {
        Connection conn = DataBaseService.getInstance().connect();
        PreparedStatement st = conn.prepareStatement("SELECT * FROM tasks WHERE email = ? and timespan = ?");
        st.setString(1, user.email);
        st.setString(2, time);
        ResultSet rs = st.executeQuery();
        List<Task> tasksList = new ArrayList<Task>();
        while (rs.next()) {
            Task task = new Task(rs.getString("title"), rs.getInt("id"), rs.getBoolean("opened"));
            tasksList.add(task);
        }
        conn.close();
        return tasksList;
    }

    public Task addTask(String title, User user) throws Exception {
        Connection conn = DataBaseService.getInstance().connect();
        PreparedStatement st = conn.prepareStatement("INSERT INTO tasks (id, title, email, timespan, opened) VALUES (tasks_seq.nextval, ?, ?, 'day', 1)");
        st.setString(1, title);
        st.setString(2, user.email);
        st.execute();
        Statement lastSt = conn.createStatement();
        ResultSet lastRs = lastSt.executeQuery("SELECT tasks_seq.currval FROM DUAL");
        int id = 0;
        if (lastRs.next()) {
            id = lastRs.getInt(1);
        }
        conn.close();
        return new Task(title, id);
    }

    public void closeTask(int taskId) throws Exception {
        Connection conn = DataBaseService.getInstance().connect();
        PreparedStatement st = conn.prepareStatement("UPDATE tasks SET opened = 0, update_date = CURRENT_TIMESTAMP WHERE id = ?");
        st.setInt(1, taskId);
        st.execute();
        conn.close();
    }

    public Map<String, Integer> getTasksByYear(User user, String year) throws Exception {
        Connection conn = DataBaseService.getInstance().connect();
        PreparedStatement st = conn.prepareStatement("SELECT COUNT(update_date) as tasks_count, to_char(MIN(update_date), 'YYYY-MM-DD') as ud FROM tasks WHERE email = ? and EXTRACT(YEAR FROM update_date) = ? GROUP BY EXTRACT(DAY FROM update_date)");
        st.setString(1, user.email);
        st.setString(2, year);
        ResultSet rs = st.executeQuery();
        Map<String, Integer> tasksMap = new HashMap<String, Integer>();
        while (rs.next()) {
            tasksMap.put(rs.getString("ud"), rs.getInt("tasks_count"));
        }
        conn.close();
        return tasksMap;
    }
}
