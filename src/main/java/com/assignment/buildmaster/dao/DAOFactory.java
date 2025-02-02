package com.assignment.buildmaster.dao;

import com.assignment.buildmaster.dao.custom.impl.*;


public class DAOFactory {
    private static DAOFactory daoFactory;
    private DAOFactory() {

    }

    public static DAOFactory getInstance() {
        return daoFactory==null?daoFactory=new DAOFactory():daoFactory;
    }

    public enum DAOType {
        CLIENT, EMPLOYEE, EXPENSES, MACHINE, PAYMENT, PROJECT, SUPPLIER
    }

    public SuperDAO getDAO(DAOType type) {
        switch (type) {
            case CLIENT:
                return new ClientDAOImpl();
            case EMPLOYEE:
                return new EmployeeDAOImpl();
            case EXPENSES:
                return new ExpensesDAOImpl();
            case MACHINE:
                return new MachineDAOImpl();
//            case MATERIALBUY:
//                return new MaterialBuyDAOImpl();
//            case MATERIALS:
//                return new MaterialsDAOImpl();
//            case MATERIALUSAGE:
//                return new MaterialUsageDAOImpl();
            case PAYMENT:
                return new PaymentDAOImpl();
            case PROJECT:
                return new ProjectDAOImpl();
            case SUPPLIER:
                return new SupplierDAOImpl();
//            case USER:
//                return new UserDAOImpl();
//            case QUERY:
//                return new QueryDAOImpl();
            default:
                return null;
        }
    }

}
