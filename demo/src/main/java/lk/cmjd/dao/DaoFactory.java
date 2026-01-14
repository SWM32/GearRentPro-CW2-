package lk.cmjd.dao;

import lk.cmjd.dao.custom.impl.loginDaoImpl;
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
        LOGIN, SIGNUP
    }

    public SuperDao getDao(DaoTypes type) {
        switch (type) {
            case LOGIN:
                return new loginDaoImpl();
            case SIGNUP:
                return new signUpDaoImpl();
            default:
                throw new AssertionError();
        }
    }

}
