package lk.cmjd.service.custom.impl;

import java.util.ArrayList;

import lk.cmjd.dao.DaoFactory;
import lk.cmjd.dao.DaoFactory.DaoTypes;
import lk.cmjd.dao.custom.manageReturnDao;
import lk.cmjd.dto.returnDto;
import lk.cmjd.entity.returnEntity;
import lk.cmjd.service.custom.manageReturnService;

public class manageReturnServiceImpl implements manageReturnService {

    private manageReturnDao dao = (manageReturnDao) DaoFactory.getInstance().getDao(DaoTypes.MANAGE_RETURN);

    @Override
    public ArrayList<returnDto> getAll() throws Exception {
        ArrayList<returnEntity> entities = dao.getAll();
        ArrayList<returnDto> dtos = new ArrayList<>();

        for (returnEntity e : entities) {
            dtos.add(new returnDto(e.getRental_Id(), e.getActual_return_date(), e.isDamaged(), e.getLate_fee(),
                    e.getDamage_description(), e.getDamage_charge()));
        }

        return dtos;
    }

    @Override
    public boolean save(returnDto dto) throws Exception {
        returnEntity entity = new returnEntity(dto.getRental_Id(), dto.getActual_return_date(), dto.isDamaged(),
                dto.getLate_fee(), dto.getDamage_description(), dto.getDamage_charge());

        return dao.save(entity);
    }

    @Override
    public boolean delete(String id) throws Exception {
        return dao.delete(id);
    }

    @Override
    public boolean update(returnDto dto) throws Exception {
        returnEntity entity = new returnEntity(dto.getRental_Id(), dto.getActual_return_date(), dto.isDamaged(),
                dto.getLate_fee(), dto.getDamage_description(), dto.getDamage_charge());

        return dao.update(entity);
    }

}
