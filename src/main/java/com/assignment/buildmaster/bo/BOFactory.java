package com.assignment.buildmaster.bo;


import com.assignment.buildmaster.bo.custom.impl.*;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory() {

    }

    public static BOFactory getInstance() {
        return boFactory==null?boFactory=new BOFactory():boFactory;
    }

    public enum BOType {
        CLIENT, EMPLOYEE, EXPENSES, MACHINE, MATERIAL, PAYMENT, PROJECT, SUPPLIER
    }

    public SuperBO getBO(BOType type) {
        switch (type) {
            case CLIENT:
                return new ClientBOImpl();
            case EMPLOYEE:
                return new EmployeeBOImpl();
            case EXPENSES:
                return new ExpensesBOImpl();
            case MACHINE:
                return new MachineBOImpl();
//            case MATERIALBUY:
//                return new MaterialBuyBOImpl();
            case MATERIAL:
                return new MaterialBOImpl();
//            case MATERIALUSAGE:
//                return new MaterialUsageBOImpl();
            case PAYMENT:
                return new PaymentBOImpl();
            case PROJECT:
                return new ProjectBOImpl();
            case SUPPLIER:
                return new SupplierBOImpl();
//            case USER:
//                return new UserBOImpl();
            default:
                return null;
        }
    }
}
