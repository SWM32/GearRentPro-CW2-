package lk.cmjd.dao.custom.impl;

import java.sql.ResultSet;
import java.util.ArrayList;

import lk.cmjd.dao.CRUDUtil;
import lk.cmjd.dao.custom.branchReportDao;
import lk.cmjd.entity.branchReportEntity;

public class branchReportDaoImpl implements branchReportDao {

    @Override
    public boolean save(branchReportEntity t) throws Exception {
        return false;
    }

    @Override
    public boolean update(branchReportEntity t) throws Exception {
        return false;
    }

    @Override
    public boolean delete(String id) throws Exception {
        return false;
    }

    @Override
    public branchReportEntity search(String id) throws Exception {
        return null;
    }

    @Override
    public ArrayList<branchReportEntity> getAll() throws Exception {
        ArrayList<branchReportEntity> entities = new ArrayList<>();

        String sql = "SELECT renret.branch_id, name, COUNT(rental_id) AS NoOfRent, Round(SUM(final_payable_amount), 3) AS TotalRev, Round(SUM(late_fee), 3) AS TotalLate, Round(SUM(damage_charge), 3) AS TotalDamage FROM (SELECT ret.rental_id, branch_id, final_payable_amount, late_fee, damage_charge FROM rental ren INNER JOIN rental_return ret ON ren.rental_id = ret.rental_id) AS renret INNER JOIN branch ON renret.branch_id = branch.branch_id GROUP BY renret.branch_id";
        ResultSet rst = CRUDUtil.executeQuery(sql);

        while (rst.next()) {
            entities.add(
                    new branchReportEntity(rst.getString("branch_id"), rst.getString("name"), rst.getInt("NoOfRent"),
                            rst.getDouble("TotalRev"), rst.getDouble("TotalLate"), rst.getDouble("TotalDamage")));
        }

        return entities;
    }

}
