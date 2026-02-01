package lk.cmjd.service.custom;

import java.util.ArrayList;
import lk.cmjd.dto.reservationDto;
import lk.cmjd.service.superService;

public interface manageReservationService extends superService {
    public ArrayList<reservationDto> getAll() throws Exception;

    public boolean save(reservationDto dto) throws Exception;

    public reservationDto search(String id) throws Exception;

    public boolean delete(String id) throws Exception;

    public boolean update(reservationDto dto) throws Exception;
}
