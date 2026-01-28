package lk.cmjd.dao.custom.impl;

import java.sql.ResultSet;
import java.util.ArrayList;

import lk.cmjd.dao.CRUDUtil;
import lk.cmjd.dao.custom.manageCustomerDao;
import lk.cmjd.entity.customerEntity;

public class manageCustomerDaoImpl implements manageCustomerDao {

    @Override
    public boolean save(customerEntity t) throws Exception {
        String sql = "INSERT INTO customer VALUES(?,?,?,?,?,?,?)";
        return CRUDUtil.executeUpdate(sql, t.getCusId(), t.getName(), t.getNic_pass(), t.getContact(),
                t.getEmail(), t.getAddress(), t.getMid());
    }

    @Override
    public boolean update(customerEntity t) throws Exception {
        String sql = "UPDATE customer SET name = ?, nic_passport = ?, contact = ?, email = ?, address = ?, membership_tier_id = ? WHERE customer_id = ?";
        return CRUDUtil.executeUpdate(sql, t.getName(), t.getNic_pass(), t.getContact(), t.getEmail(),
                t.getAddress(), t.getMid(), t.getCusId());
    }

    @Override
    public boolean delete(String id) throws Exception {
        String sql = "DELETE FROM customer WHERE customer_id = ?";
        return CRUDUtil.executeUpdate(sql, id);
    }

    @Override
    public customerEntity search(String id) throws Exception {
        String sql = "SELECT * FROM customer WHERE customer_id = ?";
        ResultSet rst = CRUDUtil.executeQuery(sql, id);

        if (rst.next()) {
            return new customerEntity(rst.getString("customer_id"), rst.getString("name"),
                    rst.getString("nic_passport"), rst.getString("contact"), rst.getString("email"),
                    rst.getString("address"), rst.getString("membership_tier_id"));
        }

        return null;
    }

    @Override
    public ArrayList<customerEntity> getAll() throws Exception {
        ArrayList<customerEntity> customerEntities = new ArrayList<>();

        String sql = "SELECT * FROM customer";
        ResultSet rst = CRUDUtil.executeQuery(sql);

        while (rst.next()) {
            customerEntities.add(new customerEntity(rst.getString("customer_id"), rst.getString("name"),
                    rst.getString("nic_passport"), rst.getString("contact"), rst.getString("email"),
                    rst.getString("address"), rst.getString("membership_tier_id")));
        }

        return customerEntities;
    }

}
