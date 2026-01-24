package lk.cmjd.service.custom;

import java.util.ArrayList;
import lk.cmjd.dto.membershipDiscountDto;
import lk.cmjd.service.superService;

public interface membershipDiscountService extends superService {
    public ArrayList<membershipDiscountDto> getAll() throws Exception;

    public boolean assign(String name, float discount) throws Exception;
}
