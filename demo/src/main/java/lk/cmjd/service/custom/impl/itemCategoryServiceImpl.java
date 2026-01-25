package lk.cmjd.service.custom.impl;

import java.util.ArrayList;

import lk.cmjd.dao.DaoFactory;
import lk.cmjd.dao.DaoFactory.DaoTypes;
import lk.cmjd.dao.custom.itemCategoryDao;
import lk.cmjd.dto.itemCategoryDto;
import lk.cmjd.entity.itemCategoryEntity;
import lk.cmjd.service.custom.itemCategoryService;

public class itemCategoryServiceImpl implements itemCategoryService {

    itemCategoryDao dao = (itemCategoryDao) DaoFactory.getInstance().getDao(DaoTypes.ITEM_CATEGORY);

    @Override
    public ArrayList<itemCategoryDto> getAll() throws Exception {
        ArrayList<itemCategoryDto> dtos = new ArrayList<>();
        ArrayList<itemCategoryEntity> entities = dao.getAll();

        for (itemCategoryEntity entity : entities) {
            dtos.add(new itemCategoryDto(entity.getCategoryID(), entity.getName(), entity.getDescription(),
                    entity.getBPF(), entity.getWM(), entity.getLRDR(), entity.getLFPD()));
        }

        return dtos;
    }

    @Override
    public boolean save(itemCategoryDto dto) throws Exception {
        itemCategoryEntity entity = new itemCategoryEntity(dto.getCategoryID(), dto.getName(), dto.getDescription(),
                dto.getBPF(), dto.getWM(), dto.getLRDR(), dto.getLFPD());

        return dao.save(entity);
    }

    @Override
    public itemCategoryDto search(String id) throws Exception {
        itemCategoryEntity entity = dao.search(id);

        if (entity != null) {
            return new itemCategoryDto(entity.getCategoryID(), entity.getName(), entity.getDescription(),
                    entity.getBPF(), entity.getWM(), entity.getLRDR(), entity.getLFPD());
        }

        return null;
    }

    @Override
    public boolean delete(String id) throws Exception {
        return dao.delete(id);
    }

    @Override
    public boolean update(itemCategoryDto dto) throws Exception {
        itemCategoryEntity entity = new itemCategoryEntity(dto.getCategoryID(), dto.getName(), dto.getDescription(),
                dto.getBPF(), dto.getWM(), dto.getLRDR(), dto.getLFPD());
        return dao.update(entity);
    }

}
