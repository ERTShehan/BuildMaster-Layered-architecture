package com.assignment.buildmaster.dao.custom.impl;

import com.assignment.buildmaster.dao.SQLUtil;
import javafx.util.Pair;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOImpl {
    public boolean verifyUser(String email, String password){
        try {
//            Connection connection = DBConnection.getInstance().getConnection();
//            PreparedStatement ps = connection.prepareStatement("SELECT * FROM User WHERE email=? AND password=?");
//            ps.setString(1, email);
//            ps.setString(2, password);
//            ResultSet rs = ps.executeQuery();

            ResultSet resultSet = SQLUtil.execute("SELECT * FROM User WHERE email=? AND password=?",email,password);

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
            ResultSet resultSet = SQLUtil.execute("SELECT * FROM User WHERE email=?",email);
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
            boolean result = SQLUtil.execute("UPDATE User SET password=? WHERE email=?", newPassword, email);
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
