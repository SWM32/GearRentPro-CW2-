package lk.cmjd.service.custom.impl;

import java.util.ArrayList;

import lk.cmjd.dao.DaoFactory;
import lk.cmjd.dao.DaoFactory.DaoTypes;
import lk.cmjd.dao.custom.manageBranchDao;
import lk.cmjd.dto.branchDto;
import lk.cmjd.entity.branchEntity;
import lk.cmjd.service.custom.manageBranchService;

public class manageBranchServiceImpl implements manageBranchService {

    manageBranchDao dao = (manageBranchDao) DaoFactory.getInstance().getDao(DaoTypes.MANAGE_BRANCH);

    @Override
    public ArrayList<branchDto> getAll() throws Exception {
        ArrayList<branchDto> dtos = new ArrayList<>();
        ArrayList<branchEntity> entities = dao.getAll();

        for (branchEntity entity : entities) {
            dtos.add(new branchDto(entity.getBranchID(), entity.getName(), entity.getAddress(), entity.getContact()));
        }

        return dtos;

    }

    @Override
    public boolean save(branchDto dto) throws Exception {
        branchEntity entity = new branchEntity(dto.getBranchID(), dto.getName(), dto.getAddress(), dto.getContact());

        return dao.save(entity);
    }

    @Override
    public branchDto search(String id) throws Exception {
        branchEntity entity = dao.search(id);

        if (entity != null) {
            return new branchDto(entity.getBranchID(), entity.getName(), entity.getAddress(), entity.getContact());
        }

        return null;
    }

    @Override
    public boolean delete(String id) throws Exception {
        return dao.delete(id);
    }

    @Override
    public boolean update(branchDto dto) throws Exception {
        branchEntity entity = new branchEntity(dto.getBranchID(), dto.getName(), dto.getAddress(), dto.getContact());
        return dao.update(entity);
    }

}
