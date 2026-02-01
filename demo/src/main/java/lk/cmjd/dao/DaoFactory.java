package lk.cmjd.dao;

import lk.cmjd.dao.custom.impl.assignBranchDaoImpl;
import lk.cmjd.dao.custom.impl.itemCategoryDaoImpl;
import lk.cmjd.dao.custom.impl.loginDaoImpl;
import lk.cmjd.dao.custom.impl.manageBranchDaoImpl;
import lk.cmjd.dao.custom.impl.manageCustomerDaoImpl;
import lk.cmjd.dao.custom.impl.manageEquipmentDaoImpl;
import lk.cmjd.dao.custom.impl.manageReservationDaoImpl;
import lk.cmjd.dao.custom.impl.membershipDiscountDaoImpl;
import lk.cmjd.dao.custom.impl.signUpDaoImpl;

public class DaoFactory {
    private static DaoFactory daoFactory;

    private DaoFactory() {
    }

    public static DaoFactory getInstance() {
        if (daoFactory == null) {
            daoFactory = new DaoFactory();
        }

        return daoFactory;
    }

    public static enum DaoTypes {
        LOGIN, SIGNUP, MANAGE_BRANCH, ASSIGN_BRANCH, MEMBERSHIP_DISCOUNT, ITEM_CATEGORY, MANAGE_EQUIPMENT,
        MANAGE_CUSTOMER, MANAGE_RESERVATION
    }

    public SuperDao getDao(DaoTypes type) {
        switch (type) {
            case LOGIN:
                return new loginDaoImpl();
            case SIGNUP:
                return new signUpDaoImpl();
            case MANAGE_BRANCH:
                return new manageBranchDaoImpl();
            case ASSIGN_BRANCH:
                return new assignBranchDaoImpl();
            case MEMBERSHIP_DISCOUNT:
                return new membershipDiscountDaoImpl();
            case ITEM_CATEGORY:
                return new itemCategoryDaoImpl();
            case MANAGE_EQUIPMENT:
                return new manageEquipmentDaoImpl();
            case MANAGE_CUSTOMER:
                return new manageCustomerDaoImpl();
            case MANAGE_RESERVATION:
                return new manageReservationDaoImpl();
            default:
                throw new AssertionError();
        }
    }

}
