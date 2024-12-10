package com.assignment.buildmasternew.model;

import com.assignment.buildmasternew.dto.ClientDto;
import com.assignment.buildmasternew.dto.ExpensesDto;
import com.assignment.buildmasternew.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExpensesModel {
    public String getNextExpensesId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select Expense_ID from Expenses order by Expense_ID desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(2);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("EX%02d", newIdIndex);
        }
        return "EX01";
    }

    public ArrayList<String> getAllExpensesIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("select Expense_ID from Expenses");
        ArrayList<String> expensesIds = new ArrayList<>();

        while (rst.next()) {
            expensesIds.add(rst.getString(1));
        }
        return expensesIds;
    }

    public ExpensesDto findById(String selectedExpenseId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from Expenses where Expense_ID=?", selectedExpenseId);
        if (rst.next()){
            return new ExpensesDto(
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

    public boolean saveExpenses(ExpensesDto expensesDto) throws SQLException {
        return CrudUtil.execute(
                "insert into Expenses values (?,?,?,?,?,?)",
                expensesDto.getExpenseId(),
                expensesDto.getType(),
                expensesDto.getAmount(),
                expensesDto.getDate(),
                expensesDto.getProjectId(),
                expensesDto.getEmployeeId()
        );
    }

    public boolean updateExpenses(ExpensesDto expensesDto) throws SQLException {
        return CrudUtil.execute(
                "update Expenses set Type=?, Amount=?, Date=?, Project_ID=?, Employee_ID=? where Expense_ID=?",
                expensesDto.getType(),
                expensesDto.getAmount(),
                expensesDto.getDate(),
                expensesDto.getProjectId(),
                expensesDto.getEmployeeId(),
                expensesDto.getExpenseId()
        );
    }

    public boolean deleteExpenses(String expenseId) throws SQLException {
//        String sql = "DELETE FROM Expenses WHERE Expense_ID=?";
//        return CrudUtil.execute(sql, expenseId);

        return CrudUtil.execute(
                "DELETE FROM Expenses WHERE Expense_ID=?", expenseId
        );
    }

    public List<ExpensesDto> getAllExpenses() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Expenses");
        List<ExpensesDto> expensesList = new ArrayList<>();
        while (resultSet.next()) {
            expensesList.add(new ExpensesDto(
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
