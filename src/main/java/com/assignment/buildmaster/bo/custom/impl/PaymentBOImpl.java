package com.assignment.buildmaster.bo.custom.impl;

import com.assignment.buildmaster.bo.custom.PaymentBO;
import com.assignment.buildmaster.dao.DAOFactory;
import com.assignment.buildmaster.dao.custom.impl.PaymentDAOImpl;
import com.assignment.buildmaster.dto.PaymentDto;
import com.assignment.buildmaster.entity.Payment;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentBOImpl implements PaymentBO {
    PaymentDAOImpl paymentDAO = (PaymentDAOImpl) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PAYMENT);

    public String getNextPaymentId() throws SQLException {
        return paymentDAO.getNextId();
    }

    public ArrayList<String> getAllPaymentIds() throws SQLException {
        return paymentDAO.getAllIds();
    }

    public ArrayList<String> findAllProjectIds() throws SQLException {
        return paymentDAO.findAllIds();
    }

    public String findNameByProjectId(String Id) throws SQLException {
        return paymentDAO.findNameById(Id);
    }

    public ArrayList<String> getAllPaymentIdsByProject(String Id) throws SQLException {
        ArrayList<String> payment = paymentDAO.getAllIdsBy(Id);
        return payment;
    }

    public PaymentDto findByPaymentId(String paymentId) throws SQLException {
        Payment payment = paymentDAO.findById(paymentId);
        return new PaymentDto(payment.getPaymentID(), payment.getProjectID(), payment.getDate(), payment.getType(), payment.getAmount());
    }

    public boolean savePayment(PaymentDto paymentDto) throws SQLException {
        return paymentDAO.save(new Payment(paymentDto.getPaymentID(), paymentDto.getProjectID(), paymentDto.getDate(), paymentDto.getType(), paymentDto.getAmount()));
    }

    public boolean updatePayment(PaymentDto paymentDto) throws SQLException {
        return paymentDAO.update(new Payment(paymentDto.getPaymentID(), paymentDto.getProjectID(), paymentDto.getDate(), paymentDto.getType(), paymentDto.getAmount()));
    }

    public boolean deletePayment(String paymentId) throws SQLException {
        return paymentDAO.delete(paymentId);
    }

    public List<PaymentDto> getAllPayment() throws SQLException {
        List<Payment> payments = paymentDAO.getAll();
        List<PaymentDto> paymentList = new ArrayList<>();
        for (Payment payment : payments) {
            paymentList.add(new PaymentDto(payment.getPaymentID(), payment.getProjectID(), payment.getDate(), payment.getType(), payment.getAmount()));
        }
        return paymentList;
    }

}
