package lk.cmjd.util;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import lk.cmjd.dao.DaoFactory;
import lk.cmjd.dao.DaoFactory.DaoTypes;
import lk.cmjd.dao.custom.manageRentalDao;
import lk.cmjd.dao.custom.manageReservationDao;
import lk.cmjd.entity.rentalEntity;
import lk.cmjd.entity.reservationEntity;

public class rentalUtil {
    private static manageReservationDao resDao = (manageReservationDao) DaoFactory.getInstance()
            .getDao(DaoTypes.MANAGE_RESERVATION);

    private static manageRentalDao renDao = (manageRentalDao) DaoFactory.getInstance()
            .getDao(DaoTypes.MANAGE_RENTAL);

    public static void startDateValidation(DatePicker dateStart) {
        LocalDate today = LocalDate.now();
        LocalDate new_start = dateStart.getValue();

        if (new_start == null) {
            return;
        }

        if (!new_start.isAfter(today)) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Invalid Starting Date");
            alert.show();

            dateStart.setValue(null);
        }
    }

    public static void endDateValidation(LocalDate new_start, DatePicker dateEnd) {
        LocalDate new_end = dateEnd.getValue();

        if (new_start == null || new_end == null) {
            return;
        }

        if (new_end.compareTo(new_start) >= 0) {
            long daysBetween = ChronoUnit.DAYS.between(new_start, new_end);
            if (daysBetween > 30) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Rental Gap should be <= 30 Days");
                alert.show();

                dateEnd.setValue(null);
            }
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Invalid End Date");
            alert.show();

            dateEnd.setValue(null);
        }
    }

    public static boolean ReserDateOverlapValidation(LocalDate new_start, LocalDate new_end, String res_eq_id) {
        try {
            ArrayList<reservationEntity> entities = resDao.getAll();

            for (reservationEntity res : entities) {
                LocalDate exist_start = res.getStart_date();
                LocalDate exist_end = res.getEnd_date();

                if (res.getEquipment_id().equals(res_eq_id)) {
                    if (new_start.isBefore(exist_start)) {
                        if (new_end.isAfter(exist_start)) {
                            Alert alert = new Alert(AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setContentText(
                                    "Overlap with Existing reservation (" + res.getReservation_id() + ")");
                            alert.show();

                            return false;
                        }
                    } else if (new_start.isAfter(exist_start)) {
                        if (exist_end.isAfter(new_start)) {
                            Alert alert = new Alert(AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setContentText(
                                    "Overlap with Existing reservation (" + res.getReservation_id() + ")");
                            alert.show();

                            return false;
                        }
                    } else if (new_start.compareTo(exist_start) == 0) {
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setContentText(
                                "Overlap with Existing reservation (" + res.getReservation_id() + ")");
                        alert.show();
                        return false;
                    }
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public static boolean RentalDateOverlapValidation(LocalDate new_start, LocalDate new_end, String res_eq_id) {
        try {
            ArrayList<rentalEntity> entities = renDao.getAll();

            for (rentalEntity ren : entities) {
                LocalDate exist_start = ren.getStart_date();
                LocalDate exist_end = ren.getDue_date();

                if (ren.getEquipment_id().equals(res_eq_id)) {
                    if (new_start.isBefore(exist_start)) {
                        if (new_end.isAfter(exist_start)) {
                            Alert alert = new Alert(AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setContentText("Overlap with Existing rental ("
                                    + ren.getRental_id() + ")");
                            alert.show();
                            return false;
                        }
                    } else if (new_start.isAfter(exist_start)) {
                        if (exist_end.isAfter(new_start)) {
                            Alert alert = new Alert(AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setContentText("Overlap with Existing rental ("
                                    + ren.getRental_id() + ")");
                            alert.show();
                            return false;
                        }
                    } else if (new_start.compareTo(exist_start) == 0) {
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setContentText("Overlap with Existing rental ("
                                + ren.getRental_id() + ")");
                        alert.show();
                        return false;
                    }
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
}
