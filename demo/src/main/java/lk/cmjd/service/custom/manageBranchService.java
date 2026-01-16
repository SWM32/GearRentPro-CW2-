package lk.cmjd.service.custom;

import java.util.ArrayList;
import lk.cmjd.dto.branchDto;
import lk.cmjd.service.superService;

public interface manageBranchService extends superService {
    public ArrayList<branchDto> getAll() throws Exception;

    public boolean save(branchDto dto) throws Exception;
}
