package lk.cmjd.service.custom.impl;

import java.util.ArrayList;

import lk.cmjd.dao.DaoFactory;
import lk.cmjd.dao.DaoFactory.DaoTypes;
import lk.cmjd.dao.custom.assignBranchDao;
import lk.cmjd.dto.assignBranchDto;
import lk.cmjd.entity.assignBranchEntity;
import lk.cmjd.service.custom.assignBranchService;

public class assignBranchServiceImpl implements assignBranchService {

    private assignBranchDao dao = (assignBranchDao) DaoFactory.getInstance().getDao(DaoTypes.ASSIGN_BRANCH);

    @Override
    public ArrayList<assignBranchDto> getAll() throws Exception {
        ArrayList<assignBranchDto> dtos = new ArrayList<>();
        ArrayList<assignBranchEntity> entities = dao.getAll();

        for (assignBranchEntity e : entities) {
            dtos.add(new assignBranchDto(e.getUserID(), e.getName(), e.getRole(), e.getBranchID()));
        }

        return dtos;
    }

    @Override
    public boolean assign(String UID, String BID) throws Exception {
        return dao.assign(UID, BID);
    }

}
