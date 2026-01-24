package lk.cmjd.service;

import lk.cmjd.service.custom.impl.assignBranchServiceImpl;
import lk.cmjd.service.custom.impl.loginServiceImpl;
import lk.cmjd.service.custom.impl.manageBranchServiceImpl;
import lk.cmjd.service.custom.impl.membershipDiscountServiceImpl;
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
        MANAGE_BRANCH, SIGNUP, LOGIN, ASSIGN_BRANCH, MEMBERSHIP_DISCOUNT
    }

    public superService getService(serviceType type) {
        switch (type) {
            case SIGNUP:
                return new signUpServiceImpl();
            case LOGIN:
                return new loginServiceImpl();
            case MANAGE_BRANCH:
                return new manageBranchServiceImpl();
            case ASSIGN_BRANCH:
                return new assignBranchServiceImpl();
            case MEMBERSHIP_DISCOUNT:
                return new membershipDiscountServiceImpl();
            default:
                throw new AssertionError();
        }
    }
}
