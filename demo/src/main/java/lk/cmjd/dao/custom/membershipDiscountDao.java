package lk.cmjd.dao.custom;

import lk.cmjd.dao.CRUDDao;
import lk.cmjd.entity.membershipDiscountEntity;

public interface membershipDiscountDao extends CRUDDao<membershipDiscountEntity, String> {
    public boolean assign(String name, Float discount, Float maxDep) throws Exception;
}
