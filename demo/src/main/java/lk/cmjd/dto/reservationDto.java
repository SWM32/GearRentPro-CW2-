package lk.cmjd.dto;

import java.time.LocalDate;

public class reservationDto {
    private String reservation_id;
    private String customer_id;
    private String equipment_id;
    private String branch_id;
    private LocalDate revervation_date;
    private LocalDate start_date;
    private LocalDate end_date;
    private String status;

    public reservationDto() {
    }

    public reservationDto(String reservation_id, String customer_id, String equipment_id, String branch_id,
            LocalDate revervation_date, LocalDate start_date, LocalDate end_date, String status) {
        this.reservation_id = reservation_id;
        this.customer_id = customer_id;
        this.equipment_id = equipment_id;
        this.branch_id = branch_id;
        this.revervation_date = revervation_date;
        this.start_date = start_date;
        this.end_date = end_date;
        this.status = status;
    }

    public String getReservation_id() {
        return reservation_id;
    }

    public void setReservation_id(String reservation_id) {
        this.reservation_id = reservation_id;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getEquipment_id() {
        return equipment_id;
    }

    public void setEquipment_id(String equipment_id) {
        this.equipment_id = equipment_id;
    }

    public String getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(String branch_id) {
        this.branch_id = branch_id;
    }

    public LocalDate getRevervation_date() {
        return revervation_date;
    }

    public void setRevervation_date(LocalDate revervation_date) {
        this.revervation_date = revervation_date;
    }

    public LocalDate getStart_date() {
        return start_date;
    }

    public void setStart_date(LocalDate start_date) {
        this.start_date = start_date;
    }

    public LocalDate getEnd_date() {
        return end_date;
    }

    public void setEnd_date(LocalDate end_date) {
        this.end_date = end_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "reservationDto [reservation_id=" + reservation_id + ", customer_id=" + customer_id
                + ", equipment_id=" + equipment_id + ", branch_id=" + branch_id + ", revervation_date="
                + revervation_date + ", start_date=" + start_date + ", end_date=" + end_date + ", status=" + status
                + "]";
    }
}
