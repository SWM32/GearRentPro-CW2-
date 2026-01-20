package lk.cmjd.dao.custom.impl;

import java.sql.ResultSet;
import java.util.ArrayList;

import lk.cmjd.dao.CRUDUtil;
import lk.cmjd.dao.custom.assignBranchDao;
import lk.cmjd.entity.assignBranchEntity;

public class assignBranchDaoImpl implements assignBranchDao {

    @Override
    public boolean save(assignBranchEntity t) throws Exception {
        return false;
    }

    @Override
    public boolean update(assignBranchEntity t) throws Exception {
        return false;
    }

    @Override
    public boolean delete(String id) throws Exception {
        return false;
    }

    @Override
    public assignBranchEntity search(String id) throws Exception {
        return null;
    }

    @Override
    public ArrayList<assignBranchEntity> getAll() throws Exception {
        ArrayList<assignBranchEntity> entities = new ArrayList<>();

        String sql = "SELECT * FROM user WHERE role = 'MANAGER'";
        ResultSet rst = CRUDUtil.executeQuery(sql);

        while (rst.next()) {
            entities.add(
                    new assignBranchEntity(rst.getString("user_id"), rst.getString("username"), rst.getString("role"),
                            rst.getString("branch_id")));
        }
        return entities;
    }

    public boolean assign(String UID, String BID) throws Exception {
        String sql = "UPDATE user SET branch_id = ? WHERE user_id = ?";
        return CRUDUtil.executeUpdate(sql, BID, UID);
    }

}
