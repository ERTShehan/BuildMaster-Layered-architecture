package com.assignment.buildmaster.dao.custom.impl;

import com.assignment.buildmaster.dao.custom.PaymentDAO;
import com.assignment.buildmaster.dto.PaymentDto;
import com.assignment.buildmaster.dao.SQLUtil;
import com.assignment.buildmaster.entity.Payment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAOImpl implements PaymentDAO {
    public String getNextId() throws SQLException {
        ResultSet rst = SQLUtil.execute("select Payment_ID from Payment order by Payment_ID desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(2);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("PA%02d", newIdIndex);
        }
        return "PA01";
    }

    public ArrayList<String> getAllIds() throws SQLException {
        ResultSet rst = SQLUtil.execute("select Payment_ID from Payment");
        ArrayList<String> paymentIds = new ArrayList<>();

        while (rst.next()){
            paymentIds.add(rst.getString(1));
        }

        return paymentIds;
    }

    public ArrayList<String> findAllIds() throws SQLException {
        ResultSet rst = SQLUtil.execute("select Project_ID from Project");
        ArrayList<String> projectIds = new ArrayList<>();

        while (rst.next()){
            projectIds.add(rst.getString(1));
        }

        return projectIds;
    }

    public String findNameById(String Id) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT Name FROM Project WHERE Project_ID=?", Id);
        if (rst.next()) {
            return rst.getString(1);
        }
        return null;
    }

    public ArrayList<String> getAllIdsBy(String Id) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT Payment_ID FROM Payment WHERE Project_ID=?", Id);
        ArrayList<String> paymentIds = new ArrayList<>();
        while (rst.next()) {
            paymentIds.add(rst.getString(1));
        }
        return paymentIds;
    }

    public Payment findById(String paymentId) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM Payment WHERE Payment_ID=?", paymentId);
        if (rst.next()) {
            return new Payment(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );
        }
        return null;
    }

    public boolean save(Payment entity) throws SQLException {
        String sql = "INSERT INTO Payment (Payment_ID, Project_ID, Date, Type, Amount) VALUES (?, ?, ?, ?, ?)";
        return SQLUtil.execute(sql, entity.getPaymentID(), entity.getProjectID(), entity.getDate(), entity.getType(), entity.getAmount());
    }

    public boolean update(Payment entity) throws SQLException {
        String sql = "UPDATE Payment SET Project_ID = ?, Date = ?, Type = ?, Amount = ? WHERE Payment_ID = ?";
        return SQLUtil.execute(sql, entity.getProjectID(), entity.getDate(), entity.getType(), entity.getAmount(), entity.getPaymentID());
    }

    public boolean delete(String paymentID) throws SQLException {
        String sql = "DELETE FROM Payment WHERE Payment_ID = ?";
        return SQLUtil.execute(sql, paymentID);
    }

    public List<Payment> getAll() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM Payment order by Payment_ID asc");
        List<Payment> paymentList = new ArrayList<>();

        while (resultSet.next()) {
            paymentList.add(new Payment(
                    resultSet.getString("Payment_ID"),
                    resultSet.getString("Project_ID"),
                    resultSet.getString("Date"),
                    resultSet.getString("Type"),
                    resultSet.getString("Amount")
            ));
        }
        return paymentList;
    }

}
