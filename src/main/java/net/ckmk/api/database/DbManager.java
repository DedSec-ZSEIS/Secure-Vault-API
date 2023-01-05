package net.ckmk.api.database;

import net.ckmk.api.prototypes.User;
import net.ckmk.api.responses.GenerateUserResponse;
import net.ckmk.api.responses.LoginResponse;

import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

public class DbManager {

    private Connection conn;

    private void connect() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        this.conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/securevault", "root", "");
    }

    public User getUser(String email){
        User user = null;
        try {
            connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users where email=\"" + email + "\";");
            if (rs.next()){
                user = new User(rs.getInt("userId"), rs.getString("email"), rs.getString("uat"), rs.getBoolean("hasAdminPrivileges"), rs.getString("status"),
                        rs.getString("fullName"), rs.getInt("dbSpaceTaken"));
            }
            rs.close();
            stmt.close();
            close();
        } catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }

    public ArrayList<User> getUsers(ArrayList<Integer> ids){
        ArrayList<User> users = new ArrayList<>();
        try {
            connect();
            Statement stmt = conn.createStatement();
            String fieldName = "userId";
            StringBuilder cond = new StringBuilder();
            int i = 0;
            for (Integer a : ids){
                i++;
                if (i < ids.size()){
                    cond.append(fieldName).append(" = ").append(a).append(" OR ");
                } else cond.append(fieldName).append(" = ").append(a);
            }
            ResultSet rs = stmt.executeQuery("SELECT * FROM users where " + cond + ";");
            while (rs.next()) {
                users.add(new User(rs.getInt("userId"), rs.getString("email"), rs.getString("uat"), rs.getBoolean("hasAdminPrivileges"), rs.getString("status"),
                        rs.getString("fullName"), rs.getInt("dbSpaceTaken")));
            }
            rs.close();
            stmt.close();
            close();
        } catch (Exception e){
            e.printStackTrace();
        }
        return users;
    }

    public ArrayList<User> getUsers(){
        ArrayList<User> users = new ArrayList<>();
        try {
            connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users;");
            while (rs.next()) {
                users.add(new User(rs.getInt("userId"), rs.getString("email"), rs.getString("uat"), rs.getBoolean("hasAdminPrivileges"), rs.getString("status"),
                        rs.getString("fullName"), rs.getInt("dbSpaceTaken")));
            }
            rs.close();
            stmt.close();
            close();
        } catch (Exception e){
            e.printStackTrace();
        }
        return users;
    }

    public boolean validateUser(String email, String pass, String fullName){
        try {
            connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT status FROM users where email=\"" + email + "\";");
            if (rs.next()){
                if (rs.getString("status").equals("pending")){
                    stmt.execute("UPDATE users set pass=\"" + pass + "\", fullName=\"" + fullName + "\", status=\"accepted\", uat=\"" + UUID.randomUUID() + "\", dbSpaceTaken=0 where email=\"" + email + "\";");
                    rs.close();
                    stmt.close();
                    close();
                    return true;
                }
            }
            rs.close();
            stmt.close();
            close();
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public String getEmail(String uat){
        try {
            connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT email FROM users where uat=\"" + uat + "\";");
            if (rs.next()){
                String s = rs.getString("email");
                rs.close();
                stmt.close();
                close();
                return s;
            }
            rs.close();
            stmt.close();
            close();
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean validateGenerationLink(String uat){
        try {
            connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT uat, status FROM users where uat=\"" + uat + "\";");
            if (rs.next()){
                if (rs.getString("status").equals("pending")){
                    rs.close();
                    stmt.close();
                    close();
                    return true;
                }
            }
            rs.close();
            stmt.close();
            close();
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public GenerateUserResponse generateUser(String email, boolean isAdmin){
        try{
            connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users where email=\"" + email + "\";");
            String token = UUID.randomUUID().toString();

            int a;
            if (isAdmin){
                a = 1;
            } else a = 0;

            if (!rs.next()){
                stmt.execute("INSERT into users (email, uat, hasAdminPrivileges, status) VALUES (\"" + email + "\", \"" + token + "\", " + a + ", \"pending\");");
                rs.close();
                stmt.close();
                close();
                return new GenerateUserResponse(token, false, true);
            }
            rs.close();
            stmt.close();
            close();
        } catch (Exception e){
            e.printStackTrace();
        }
        return new GenerateUserResponse(null, true, false);
    }

    public boolean validateToken(String email, String uat){
        try {
            connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT uat, status FROM users where email=\"" + email + "\";");
            rs.next();
            if (rs.getString("uat").equals(uat)){
                if (rs.getString("status").equals("accepted")){
                    rs.close();
                    stmt.close();
                    close();
                    return true;
                }
            }
            rs.close();
            stmt.close();
            close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean validateCreationToken(String email, String uat){
        try {
            connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT uat FROM users where email=\"" + email + "\";");
            rs.next();
            if (rs.getString("uat").equals(uat)){
                rs.close();
                stmt.close();
                close();
                return true;
            }
            rs.close();
            stmt.close();
            close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean validateTokenAdmin(String email, String uat){
        try {
            connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT uat, hasAdminPrivileges, status FROM users where email=\"" + email + "\";");
            if (rs.next()){
                if (rs.getString("uat").equals(uat)){
                    if (rs.getBoolean("hasAdminPrivileges")){
                        if (rs.getString("status").equals("accepted")){
                            rs.close();
                            stmt.close();
                            close();
                            return true;
                        }
                    }
                }
            }
            rs.close();
            stmt.close();
            close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public LoginResponse login(String email, String password){
        try {
            connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT pass from users where email=\"" + email + "\";");
            if (rs.next()){
                if (rs.getString("pass").equals(password)){
                    String uat = UUID.randomUUID().toString();
                    stmt.execute("UPDATE users set uat = \"" + uat + "\" where email=\"" + email + "\";");
                    rs.close();
                    stmt.close();
                    close();
                    User user = getUser(email);
                    return new LoginResponse(user.getEmail(), true, user.getUat(), user.isAdmin(), user.isAllowed(), true);
                }
                return new LoginResponse(null, true,null, false, "disallowed", false);
            }
            return new LoginResponse(null, false,null, false, "disallowed", false);
        } catch (Exception e) {
            return new LoginResponse(null, false,null, false, "disallowed", false);
        }
    }

    public void removeUser(String email){
        try {
            connect();
            Statement stmt = conn.createStatement();
            stmt.execute("DELETE from users where email=\"" + email + "\" limit 1;");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean isDbEnabled(){
        try {
            connect();
            close();
            return true;
        } catch (Exception e){
            System.out.println("[DbError] Database is unavailable!");
            return false;
        }
    }

    private void close(){
        try {
            this.conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
