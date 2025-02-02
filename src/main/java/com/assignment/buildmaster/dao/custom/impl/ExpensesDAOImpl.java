package com.assignment.buildmaster.dao.custom.impl;

import com.assignment.buildmaster.dao.custom.ExpensesDAO;
import com.assignment.buildmaster.dto.ExpensesDto;
import com.assignment.buildmaster.dao.SQLUtil;
import com.assignment.buildmaster.entity.Expenses;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExpensesDAOImpl implements ExpensesDAO {
    public String getNextId() throws SQLException {
        ResultSet rst = SQLUtil.execute("select Expense_ID from Expenses order by Expense_ID desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(2);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("EX%02d", newIdIndex);
        }
        return "EX01";
    }

    public ArrayList<String> getAllIds() throws SQLException {
        ResultSet rst = SQLUtil.execute("select Expense_ID from Expenses");
        ArrayList<String> expensesIds = new ArrayList<>();

        while (rst.next()) {
            expensesIds.add(rst.getString(1));
        }
        return expensesIds;
    }

    public Expenses findById(String selectedExpenseId) throws SQLException {
        ResultSet rst = SQLUtil.execute("select * from Expenses where Expense_ID=?", selectedExpenseId);
        if (rst.next()){
            return new Expenses(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6)
            );
        }
        return null;
    }

    public boolean save(Expenses entity) throws SQLException {
        return SQLUtil.execute(
                "insert into Expenses values (?,?,?,?,?,?)",
                entity.getExpenseId(),
                entity.getType(),
                entity.getAmount(),
                entity.getDate(),
                entity.getProjectId(),
                entity.getEmployeeId()
        );
    }

    public boolean update(Expenses entity) throws SQLException {
        return SQLUtil.execute(
                "update Expenses set Type=?, Amount=?, Date=?, Project_ID=?, Employee_ID=? where Expense_ID=?",
                entity.getType(),
                entity.getAmount(),
                entity.getDate(),
                entity.getProjectId(),
                entity.getEmployeeId(),
                entity.getExpenseId()
        );
    }

    public boolean delete(String expenseId) throws SQLException {
//        String sql = "DELETE FROM Expenses WHERE Expense_ID=?";
//        return CrudUtil.execute(sql, expenseId);

        return SQLUtil.execute(
                "DELETE FROM Expenses WHERE Expense_ID=?", expenseId
        );
    }

    public List<Expenses> getAll() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM Expenses");
        List<Expenses> expensesList = new ArrayList<>();
        while (resultSet.next()) {
            expensesList.add(new Expenses(
                    resultSet.getString("Expense_ID"),
                    resultSet.getString("Type"),
                    resultSet.getDouble("Amount"),
                    resultSet.getString("Date"),
                    resultSet.getString("Project_ID"),
                    resultSet.getString("Employee_ID")
            ));
        }
        return expensesList;
    }

}
