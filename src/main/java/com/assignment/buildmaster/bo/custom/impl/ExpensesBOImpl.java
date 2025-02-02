package com.assignment.buildmaster.bo.custom.impl;

import com.assignment.buildmaster.bo.custom.ExpensesBO;
import com.assignment.buildmaster.dao.DAOFactory;
import com.assignment.buildmaster.dao.custom.impl.ExpensesDAOImpl;
import com.assignment.buildmaster.dto.ExpensesDto;
import com.assignment.buildmaster.entity.Expenses;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExpensesBOImpl implements ExpensesBO {
    ExpensesDAOImpl expensesDAO = (ExpensesDAOImpl) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.EXPENSES);

    public String getNextExpensesId() throws SQLException {
        return expensesDAO.getNextId();
    }

    public ArrayList<String> getAllExpensesIds() throws SQLException {
        return expensesDAO.getAllIds();
    }

    public ExpensesDto findByExpensesId(String selectedExpenseId) throws SQLException {
        Expenses expenses = expensesDAO.findById(selectedExpenseId);
        return new ExpensesDto(expenses.getExpenseId(), expenses.getType(), expenses.getAmount(), expenses.getDate(), expenses.getProjectId(), expenses.getEmployeeId());
    }

    public boolean saveExpenses(ExpensesDto expensesDto) throws SQLException {
        return expensesDAO.save(new Expenses(expensesDto.getExpenseId(), expensesDto.getType(), expensesDto.getAmount(), expensesDto.getDate(), expensesDto.getProjectId(), expensesDto.getEmployeeId()));
    }

    public boolean updateExpenses(ExpensesDto expensesDto) throws SQLException {
        return expensesDAO.update(new Expenses(expensesDto.getExpenseId(), expensesDto.getType(), expensesDto.getAmount(), expensesDto.getDate(), expensesDto.getProjectId(), expensesDto.getEmployeeId()));
    }

    public boolean deleteExpenses(String expenseId) throws SQLException {
        return expensesDAO.delete(expenseId);
    }

    public List<ExpensesDto> getAllExpenses() throws SQLException {
        List<Expenses> allExpenses = expensesDAO.getAll();
        List<ExpensesDto> expensesList = new ArrayList<>();
        for (Expenses expenses : allExpenses) {
            expensesList.add(new ExpensesDto(expenses.getExpenseId(), expenses.getType(), expenses.getAmount(), expenses.getDate(), expenses.getProjectId(), expenses.getEmployeeId()));
        }
        return expensesList;
    }

//    public int getCount() throws SQLException {
//        return 0;
//    }

//    public String getEmployeeName(String Id) throws SQLException {
//        return "";
//    }
//
//    public String getEmployeeRole(String Id) throws SQLException {
//        return "";
//    }

//    public String getUnit(String Id) throws SQLException {
//        return "";
//    }

//    public ArrayList<String> getAllEmployeeIds() throws SQLException {
//        return null;
//    }
//
//    public ArrayList<String> getAllProjectIds() throws SQLException {
//        return null;
//    }

//    public String findNameById(String Id) throws SQLException {
//        return "";
//    }
//
//    public ArrayList<String> getAllIdsBy(String Id) throws SQLException {
//        return null;
//    }
}
