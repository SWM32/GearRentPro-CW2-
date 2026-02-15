package lk.cmjd.service.custom;

import java.util.ArrayList;

import lk.cmjd.dto.overdueDto;
import lk.cmjd.service.superService;

public interface overdueRentalsService extends superService {
    public ArrayList<overdueDto> getAll() throws Exception;
}
