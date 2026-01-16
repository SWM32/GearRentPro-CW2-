package lk.cmjd.service;

import lk.cmjd.service.custom.impl.loginServiceImpl;
import lk.cmjd.service.custom.impl.manageBranchServiceImpl;
import lk.cmjd.service.custom.impl.signUpServiceImpl;

public class serviceFactory {
    private static serviceFactory serviceFactory;

    private serviceFactory() {
    }

    public static serviceFactory getInstance() {
        if (serviceFactory == null) {
            serviceFactory = new serviceFactory();
        }
        return serviceFactory;
    }

    public static enum serviceType {
        MANAGE_BRANCH, SIGNUP, LOGIN
    }

    public superService getService(serviceType type) {
        switch (type) {
            case SIGNUP:
                return new signUpServiceImpl();
            case LOGIN:
                return new loginServiceImpl();
            case MANAGE_BRANCH:
                return new manageBranchServiceImpl();
            default:
                throw new AssertionError();
        }
    }
}
