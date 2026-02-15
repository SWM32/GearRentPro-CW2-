package lk.cmjd.dao.custom.impl;

import java.sql.ResultSet;
import java.util.ArrayList;

import lk.cmjd.dao.CRUDUtil;
import lk.cmjd.dao.custom.overdueRentalsDao;
import lk.cmjd.entity.overdueEntity;

public class overdueRentalsDaoImpl implements overdueRentalsDao {

    @Override
    public boolean save(overdueEntity t) throws Exception {
        return false;
    }

    @Override
    public boolean update(overdueEntity t) throws Exception {
        return false;
    }

    @Override
    public boolean delete(String id) throws Exception {
        return false;
    }

    @Override
    public overdueEntity search(String id) throws Exception {
        return null;
    }

    @Override
    public ArrayList<overdueEntity> getAll() throws Exception {
        ArrayList<overdueEntity> entities = new ArrayList<>();

        String sql = "SELECT rental.customer_id, rental.branch_id, rental.equipment_id, rental.due_date, DATEDIFF(CURDATE(), rental.due_date) AS overdue_days, customer.contact, customer.email FROM rental, customer WHERE rental.customer_id = customer.customer_id AND CURDATE() > rental.due_date";
        ResultSet rst = CRUDUtil.executeQuery(sql);

        while (rst.next()) {
            entities.add(new overdueEntity(rst.getString("customer_id"), rst.getString("branch_id"),
                    rst.getString("equipment_id"), rst.getDate("due_date").toLocalDate(), rst.getInt("overdue_days"),
                    rst.getString("contact"), rst.getString("email")));
        }

        return entities;
    }

}
