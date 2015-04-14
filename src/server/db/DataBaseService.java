package server.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Locale;

/**
 * Created by dmitry on 06.04.15.
 */
public class DataBaseService {

    public void connect() {
        try {
            System.out.println("Try to connect ");
            Locale.setDefault(Locale.ENGLISH);
            DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.33.30:1521:XE", "system", "60850142");
            System.out.print("Connect OKK ");
            conn.close();
            return;
        } catch (Exception e) {
            System.out.print("fuck!!! ");
            System.out.print(e.getMessage());
            return;
        }
    }
}
