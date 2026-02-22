package lk.cmjd.service.custom.impl;

import java.util.ArrayList;

import lk.cmjd.dao.DaoFactory;
import lk.cmjd.dao.DaoFactory.DaoTypes;
import lk.cmjd.dao.custom.branchReportDao;
import lk.cmjd.dto.branchReportDto;
import lk.cmjd.entity.branchReportEntity;
import lk.cmjd.service.custom.branchReportService;

public class branchReportServiceImpl implements branchReportService {

    private branchReportDao dao = (branchReportDao) DaoFactory.getInstance().getDao(DaoTypes.BRANCH_REPORT);

    @Override
    public ArrayList<branchReportDto> getAll() throws Exception {
        ArrayList<branchReportEntity> entities = dao.getAll();
        ArrayList<branchReportDto> dtos = new ArrayList<>();

        for (branchReportEntity e : entities) {
            dtos.add(new branchReportDto(e.getBranch_id(), e.getName(), e.getNoOfRentals(), e.getTotal_revenue(),
                    e.getTotal_late(), e.getTotal_damage()));
        }

        return dtos;
    }

}
