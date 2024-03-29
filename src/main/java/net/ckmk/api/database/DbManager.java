package net.ckmk.api.database;

import net.ckmk.api.prototypes.FileEntity;
import net.ckmk.api.prototypes.User;
import net.ckmk.api.responses.GenerateUserResponse;
import net.ckmk.api.responses.LoginResponse;

import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

public class DbManager {

    private Connection conn;
    private String url;
    private String username;
    private String password;

    private void connect() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        this.conn = DriverManager.getConnection(url, username, password);
    }

    public void removeFile(int id){
        try {
            connect();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM files where fileId=?");
            stmt.setInt(1, id);
            stmt.execute();
            stmt.close();
            close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public ArrayList<FileEntity> getFiles(String email){
        ArrayList<FileEntity> files = new ArrayList<>();
        try {
            connect();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM files where ownerId=?");
            stmt.setInt(1, getUser(email).getId());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                files.add(new FileEntity(rs.getInt("fileId"), getUser(rs.getInt("ownerId")), rs.getString("fileName"),
                        rs.getString("filePath"), rs.getLong("fileSize"), rs.getString("createdAt")));
            }
            rs.close();
            stmt.close();
            close();
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("[Error] A possible solution is to limit the amount of request sent at the same time.");
        }
        return files;
    }
    public ArrayList<FileEntity> getFiles(String email, ArrayList<Integer> ids){
        ArrayList<FileEntity> files = new ArrayList<>();
        try {
            connect();
            String fieldName = "fileId";
            StringBuilder cond = new StringBuilder();
            int i = 0;
            for (Integer a : ids){
                i++;
                if (i < ids.size()){
                    cond.append(fieldName).append(" = ").append(a).append(" OR ");
                } else cond.append(fieldName).append(" = ").append(a);
            }
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM files where ownerId=? and " + cond);
            stmt.setInt(1, getUser(email).getId());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                files.add(new FileEntity(rs.getInt("fileId"), getUser(rs.getInt("ownerId")), rs.getString("fileName"),
                        rs.getString("filePath"), rs.getLong("fileSize"), rs.getString("createdAt")));
            }
            rs.close();
            stmt.close();
            close();
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("[Error] A possible solution is to limit the amount of request sent at the same time.");
        }
        return files;
    }
    public void saveFile(String email, String path, String fileName, long fileSize){
        try {
            connect();
            String query = "Insert into files (`ownerId`, `fileName`, `filePath`, `fileSize`, `createdAt`) values (?, ?, ?, ?, now())";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, getUser(email).getId());
            stmt.setString(2, fileName);
            stmt.setString(3, path);
            stmt.setLong(4, fileSize);
            stmt.execute();
            close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public FileEntity getFileData(String email, String fileName){
        try {
            connect();
            String query = "Select * from files where ownerId=? and fileName=?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, getUser(email).getId());
            stmt.setString(2, fileName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                return new FileEntity(rs.getInt("fileId"), getUser(rs.getInt("ownerId")), rs.getString("fileName"), rs.getString("filePath"), rs.getLong("fileSize"), rs.getString("createdAt"));
            }
            close();
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public FileEntity getFileData(int id){
        try {
            connect();
            String query = "Select * from files where fileId=?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                return new FileEntity(id, getUser(rs.getInt("ownerId")), rs.getString("fileName"), rs.getString("filePath"), rs.getLong("fileSize"), rs.getString("createdAt"));
            }
            close();
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public void resetPass(String email, String newPass){
        try {
            connect();
            String query = "UPDATE users set pass=? where email=?;";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, newPass);
            stmt.setString(2, email);
            stmt.execute();
            stmt.close();
            close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void removeUsers(ArrayList<Integer> ids){
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
            stmt.execute("DELETE from users where " + cond + " limit " + ids.size() + ";");
            stmt.close();
            close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public User getUser(String email){
        User user = null;
        try {
            connect();
            String query = "SELECT * FROM users where email=?;";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1,email);
            ResultSet rs = stmt.executeQuery();
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

    public User getUser(int id){
        User user = null;
        try {
            connect();
            String query = "SELECT * FROM users where userId=?;";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
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
            String query = "SELECT status FROM users where email=?;";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                if (rs.getString("status").equals("pending")){
                    query = "UPDATE users set pass=?, fullName=?, status='accepted', uat=?, dbSpaceTaken=0 where email=?;";
                    stmt = conn.prepareStatement(query);
                    stmt.setString(1, pass);
                    stmt.setString(2, fullName);
                    stmt.setString(3, UUID.randomUUID().toString());
                    stmt.setString(4, email);
                    stmt.execute();
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
            String query = "SELECT email FROM users where uat=?;";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1,uat);
            ResultSet rs = stmt.executeQuery();
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
            String query = "SELECT uat, status FROM users where uat=?;";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1,uat);
            ResultSet rs = stmt.executeQuery();
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
            String query = "SELECT * FROM users where email=?;";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            String token = UUID.randomUUID().toString();

            int a;
            if (isAdmin){
                a = 1;
            } else a = 0;

            if (!rs.next()){
                query = "INSERT into users (email, uat, hasAdminPrivileges, status) VALUES (?, ?, ?, 'pending');";
                stmt = conn.prepareStatement(query);
                stmt.setString(1, email);
                stmt.setString(2, token);
                stmt.setInt(3, a);
                stmt.execute();
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
            String query = "SELECT uat, status FROM users where email=?;";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                if (rs.getString("uat").equals(uat)) {
                    if (rs.getString("status").equals("accepted")) {
                        rs.close();
                        stmt.close();
                        close();
                        return true;
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

    public boolean validateCreationToken(String email, String uat){
        try {
            connect();
            String query = "SELECT uat FROM users where email=?;";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
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
            String query = "SELECT uat, hasAdminPrivileges, status FROM users where email=?;";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
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
            String query = "SELECT pass from users where email=?;";

            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){

                if (rs.getString("pass").equals(password)){
                    String uat = UUID.randomUUID().toString();
                    query = "UPDATE users set uat = ? where email=?;";
                    stmt = conn.prepareStatement(query);
                    stmt.setString(1, uat);
                    stmt.setString(2, email);
                    stmt.execute();
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
            String query = "DELETE from users where email=? limit 1;";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            stmt.execute();
            stmt.close();
            close();
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
