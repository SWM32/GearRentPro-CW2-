package lk.cmjd.service.custom.impl;

import java.util.ArrayList;

import lk.cmjd.dao.DaoFactory;
import lk.cmjd.dao.DaoFactory.DaoTypes;
import lk.cmjd.dao.custom.membershipDiscountDao;
import lk.cmjd.dto.membershipDiscountDto;
import lk.cmjd.entity.membershipDiscountEntity;
import lk.cmjd.service.custom.membershipDiscountService;

public class membershipDiscountServiceImpl implements membershipDiscountService {

    private membershipDiscountDao dao = (membershipDiscountDao) DaoFactory.getInstance()
            .getDao(DaoTypes.MEMBERSHIP_DISCOUNT);

    @Override
    public ArrayList<membershipDiscountDto> getAll() throws Exception {
        ArrayList<membershipDiscountDto> dtos = new ArrayList<>();
        ArrayList<membershipDiscountEntity> entities = dao.getAll();

        for (membershipDiscountEntity e : entities) {
            dtos.add(new membershipDiscountDto(e.getTiername(), e.getDiscount()));
        }

        return dtos;
    }

    @Override
    public boolean assign(String name, float discount) throws Exception {
        return dao.assign(name, discount);
    }

}
