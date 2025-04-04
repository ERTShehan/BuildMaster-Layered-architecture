package com.assignment.buildmaster.dao.custom;

import com.assignment.buildmaster.dao.CrudDAO;
import com.assignment.buildmaster.entity.User;
import javafx.util.Pair;

public interface UserDAO extends CrudDAO<User> {
    boolean verifyUser(String email, String password);
    Pair<Boolean, String> checkGmail(String email);
    boolean updatePassword(String email, String newPassword);
}