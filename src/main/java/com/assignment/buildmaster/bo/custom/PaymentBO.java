package com.assignment.buildmaster.bo.custom;

import com.assignment.buildmaster.bo.SuperBO;
import com.assignment.buildmaster.dto.PaymentDto;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface PaymentBO extends SuperBO {
    public String getNextPaymentId() throws SQLException;
    public ArrayList<String> getAllPaymentIds() throws SQLException;
    public ArrayList<String> findAllProjectIds() throws SQLException;
    public String findNameByProjectId(String Id) throws SQLException;
    public ArrayList<String> getAllPaymentIdsByProject(String Id) throws SQLException;
    public PaymentDto findByPaymentId(String paymentId) throws SQLException;
    public boolean savePayment(PaymentDto paymentDto) throws SQLException;
    public boolean updatePayment(PaymentDto paymentDto) throws SQLException;
    public boolean deletePayment(String paymentId) throws SQLException;
    public List<PaymentDto> getAllPayment() throws SQLException;
}
