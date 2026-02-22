package lk.cmjd.service.custom;

import java.util.ArrayList;

import lk.cmjd.dto.branchReportDto;
import lk.cmjd.service.superService;

public interface branchReportService extends superService {
    public ArrayList<branchReportDto> getAll() throws Exception;
}
