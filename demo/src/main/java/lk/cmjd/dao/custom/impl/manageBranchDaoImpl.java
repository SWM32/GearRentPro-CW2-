package lk.cmjd.dao.custom.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import lk.cmjd.dao.CRUDUtil;
import lk.cmjd.dao.custom.manageBranchDao;
import lk.cmjd.entity.branchEntity;

public class manageBranchDaoImpl implements manageBranchDao {

    @Override
    public boolean save(branchEntity t) throws Exception {

        String sql = "INSERT INTO branch VALUES(?,?,?,?)";
        return CRUDUtil.executeUpdate(sql, t.getBranchID(), t.getName(), t.getAddress(), t.getContact());

    }

    @Override
    public boolean update(branchEntity t) throws Exception {
        return false;
    }

    @Override
    public boolean delete(String id) throws Exception {
        return false;
    }

    @Override
    public branchEntity search(String id) throws Exception {
        return null;
    }

    @Override
    public ArrayList<branchEntity> getAll() throws Exception {

        ArrayList<branchEntity> branchEntities = new ArrayList<>();

        String sql = "SELECT * FROM branch";
        ResultSet rst = CRUDUtil.executeQuery(sql);

        while (rst.next()) {
            branchEntities.add(new branchEntity(rst.getString("branch_id"), rst.getString("name"),
                    rst.getString("address"), rst.getString("contact")));
        }

        return branchEntities;

    }

}
