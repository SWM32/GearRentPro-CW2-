package lk.cmjd.dao.custom;

import lk.cmjd.dao.CRUDDao;
import lk.cmjd.entity.assignBranchEntity;

public interface assignBranchDao extends CRUDDao<assignBranchEntity, String> {
    public boolean assign(String UID, String BID) throws Exception;
}
