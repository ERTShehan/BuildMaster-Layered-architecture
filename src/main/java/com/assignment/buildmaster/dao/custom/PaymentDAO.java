package com.assignment.buildmaster.dao.custom;

import com.assignment.buildmaster.dao.CrudDAO;
import com.assignment.buildmaster.entity.Payment;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface PaymentDAO extends CrudDAO<Payment> {
    ArrayList<String> findAllIds() throws SQLException;
    String findNameById(String Id) throws SQLException;
    ArrayList<String> getAllIdsBy(String Id) throws SQLException;
}
