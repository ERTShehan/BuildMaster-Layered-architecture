package com.assignment.buildmasternew.model;

import com.assignment.buildmasternew.util.CrudUtil;
import javafx.util.Pair;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserModel {
    public boolean verifyUser(String email, String password){
        try {
//            Connection connection = DBConnection.getInstance().getConnection();
//            PreparedStatement ps = connection.prepareStatement("SELECT * FROM User WHERE email=? AND password=?");
//            ps.setString(1, email);
//            ps.setString(2, password);
//            ResultSet rs = ps.executeQuery();

            ResultSet resultSet = CrudUtil.execute("SELECT * FROM User WHERE email=? AND password=?",email,password);

            if (resultSet.next()) {
                if (resultSet.getString("email").equals(email) && resultSet.getString("password").equals(password)) {
                    return true;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Pair <Boolean, String> checkGmail(String email){
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM User WHERE email=?",email);
            if (resultSet.next()){
                String result = resultSet.getString("email");
                return new Pair<>(true,result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Pair<>(false, null);
    }

    public boolean updatePassword(String email, String newPassword){
        try {
            boolean result = CrudUtil.execute("UPDATE User SET password=? WHERE email=?", newPassword, email);
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
