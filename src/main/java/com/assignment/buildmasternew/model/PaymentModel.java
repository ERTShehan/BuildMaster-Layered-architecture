package com.assignment.buildmasternew.model;

import com.assignment.buildmasternew.dto.PaymentDto;
import com.assignment.buildmasternew.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentModel {
    public String getNextPaymentId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select Payment_ID from Payment order by Payment_ID desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(2);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("PA%02d", newIdIndex);
        }
        return "PA01";
    }

    public ArrayList<String> getAllPaymentIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("select Payment_ID from Payment");
        ArrayList<String> paymentIds = new ArrayList<>();

        while (rst.next()){
            paymentIds.add(rst.getString(1));
        }

        return paymentIds;
    }

    public ArrayList<String> getAllProjectIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("select Project_ID from Project");
        ArrayList<String> projectIds = new ArrayList<>();

        while (rst.next()){
            projectIds.add(rst.getString(1));
        }

        return projectIds;
    }

    public String findProjectNameById(String projectId) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT Name FROM Project WHERE Project_ID=?", projectId);
        if (rst.next()) {
            return rst.getString(1);
        }
        return null;
    }

    public ArrayList<String> getAllPaymentIdsByProject(String projectId) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT Payment_ID FROM Payment WHERE Project_ID=?", projectId);
        ArrayList<String> paymentIds = new ArrayList<>();
        while (rst.next()) {
            paymentIds.add(rst.getString(1));
        }
        return paymentIds;
    }

    public PaymentDto findByPaymentId(String paymentId) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Payment WHERE Payment_ID=?", paymentId);
        if (rst.next()) {
            return new PaymentDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );
        }
        return null;
    }

    public boolean savePayment(PaymentDto paymentDto) throws SQLException {
        String sql = "INSERT INTO Payment (Payment_ID, Project_ID, Date, Type, Amount) VALUES (?, ?, ?, ?, ?)";
        return CrudUtil.execute(sql, paymentDto.getPaymentID(), paymentDto.getProjectID(), paymentDto.getDate(), paymentDto.getType(), paymentDto.getAmount());
    }

    public boolean updatePayment(PaymentDto paymentDto) throws SQLException {
        String sql = "UPDATE Payment SET Project_ID = ?, Date = ?, Type = ?, Amount = ? WHERE Payment_ID = ?";
        return CrudUtil.execute(sql, paymentDto.getProjectID(), paymentDto.getDate(), paymentDto.getType(), paymentDto.getAmount(), paymentDto.getPaymentID());
    }

    public boolean deletePayment(String paymentID) throws SQLException {
        String sql = "DELETE FROM Payment WHERE Payment_ID = ?";
        return CrudUtil.execute(sql, paymentID);
    }

    public List<PaymentDto> getAllPayments() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Payment order by Payment_ID asc");
        List<PaymentDto> paymentList = new ArrayList<>();

        while (resultSet.next()) {
            paymentList.add(new PaymentDto(
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
