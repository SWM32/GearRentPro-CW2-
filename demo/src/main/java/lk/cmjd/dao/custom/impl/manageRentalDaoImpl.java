package lk.cmjd.dao.custom.impl;

import java.sql.ResultSet;
import java.util.ArrayList;

import lk.cmjd.dao.CRUDUtil;
import lk.cmjd.dao.custom.manageRentalDao;
import lk.cmjd.entity.rentalEntity;

public class manageRentalDaoImpl implements manageRentalDao {

    @Override
    public boolean save(rentalEntity t) throws Exception {
        String sql = "INSERT INTO rental VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        return CRUDUtil.executeUpdate(sql, t.getRental_id(), t.getCustomer_id(), t.getEquipment_id(),
                t.getBranch_id(), t.getStart_date(), t.getDue_date(), t.getActual_return_date(),
                t.getTotal_rent(), t.getSdh(), t.getMdh(), t.getLrd(), t.getFinal_pay(),
                t.getPayment_status(), t.getRental_status());
    }

    @Override
    public boolean update(rentalEntity t) throws Exception {
        String sql = "UPDATE rental SET customer_id = ?, equipment_id = ?, branch_id = ?, start_date = ?, due_date = ?, actual_return_date = ?, total_rental_cost = ?, security_deposit_held = ?, membership_discount_amount = ?, long_rental_discount_amount = ?, final_payable_amount = ?, payment_status = ?, rental_status = ? WHERE rental_id = ?";
        return CRUDUtil.executeUpdate(sql, t.getCustomer_id(), t.getEquipment_id(), t.getBranch_id(),
                t.getStart_date(), t.getDue_date(), t.getActual_return_date(), t.getTotal_rent(),
                t.getSdh(), t.getMdh(), t.getLrd(), t.getFinal_pay(), t.getPayment_status(),
                t.getRental_status(), t.getRental_id());
    }

    @Override
    public boolean delete(String id) throws Exception {
        String sql = "DELETE FROM rental WHERE rental_id = ?";
        return CRUDUtil.executeUpdate(sql, id);
    }

    @Override
    public rentalEntity search(String id) throws Exception {
        String sql = "SELECT * FROM rental WHERE rental_id = ?";
        ResultSet rst = CRUDUtil.executeQuery(sql, id);

        if (rst.next()) {
            return new rentalEntity(rst.getString("rental_id"), rst.getString("customer_id"),
                    rst.getString("equipment_id"), rst.getString("branch_id"),
                    rst.getDate("start_date").toLocalDate(), rst.getDate("due_date").toLocalDate(),
                    rst.getDate("actual_return_date").toLocalDate(), rst.getFloat("total_rental_cost"),
                    rst.getFloat("security_deposit_held"), rst.getFloat("membership_discount_amount"),
                    rst.getFloat("long_rental_discount_amount"), rst.getFloat("final_payable_amount"),
                    rst.getString("payment_status"), rst.getString("rental_status"));
        }

        return null;
    }

    @Override
    public ArrayList<rentalEntity> getAll() throws Exception {
        ArrayList<rentalEntity> rentalEntities = new ArrayList<>();

        String sql = "SELECT * FROM rental";
        ResultSet rst = CRUDUtil.executeQuery(sql);

        while (rst.next()) {
            if (rst.getDate("actual_return_date") == null) {
                rentalEntities.add(new rentalEntity(rst.getString("rental_id"), rst.getString("customer_id"),
                        rst.getString("equipment_id"), rst.getString("branch_id"),
                        rst.getDate("start_date").toLocalDate(), rst.getDate("due_date").toLocalDate(),
                        null, rst.getFloat("total_rental_cost"),
                        rst.getFloat("security_deposit_held"), rst.getFloat("membership_discount_amount"),
                        rst.getFloat("long_rental_discount_amount"), rst.getFloat("final_payable_amount"),
                        rst.getString("payment_status"), rst.getString("rental_status")));
            } else {
                rentalEntities.add(new rentalEntity(rst.getString("rental_id"), rst.getString("customer_id"),
                        rst.getString("equipment_id"), rst.getString("branch_id"),
                        rst.getDate("start_date").toLocalDate(), rst.getDate("due_date").toLocalDate(),
                        rst.getDate("actual_return_date").toLocalDate(), rst.getFloat("total_rental_cost"),
                        rst.getFloat("security_deposit_held"), rst.getFloat("membership_discount_amount"),
                        rst.getFloat("long_rental_discount_amount"), rst.getFloat("final_payable_amount"),
                        rst.getString("payment_status"), rst.getString("rental_status")));
            }

        }

        return rentalEntities;
    }

    @Override
    public String getLastID() throws Exception {
        String sql = "SELECT rental_id FROM rental ORDER BY LENGTH(rental_id) DESC, rental_id DESC LIMIT 1";
        ResultSet rst = CRUDUtil.executeQuery(sql);

        if (rst.next()) {
            return rst.getString("rental_id");
        }

        return null;
    }

}
