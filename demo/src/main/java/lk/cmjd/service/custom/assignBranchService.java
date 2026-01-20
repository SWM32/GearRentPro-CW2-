package lk.cmjd.service.custom;

import java.util.ArrayList;

import lk.cmjd.dto.assignBranchDto;
import lk.cmjd.service.superService;

public interface assignBranchService extends superService {
    public ArrayList<assignBranchDto> getAll() throws Exception;

    public boolean assign(String UID, String BID) throws Exception;
}
