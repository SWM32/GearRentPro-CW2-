package lk.cmjd.service;

import lk.cmjd.service.custom.impl.loginServiceImpl;
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
        BRANCH, SIGNUP, LOGIN
    }

    public superService getService(serviceType type) {
        switch (type) {
            case SIGNUP:
                return new signUpServiceImpl();
            case LOGIN:
                return new loginServiceImpl();
            default:
                throw new AssertionError();
        }
    }
}
