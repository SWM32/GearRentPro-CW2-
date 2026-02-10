package lk.cmjd.entity;

import java.time.LocalDate;

public class rentalEntity {
    private String rental_id;
    private String customer_id;
    private String equipment_id;
    private String branch_id;
    private LocalDate start_date;
    private LocalDate due_date;
    private LocalDate actual_return_date;
    private float total_rent;
    private float sdh;
    private float mdh;
    private float lrd;
    private float final_pay;
    private String payment_status;
    private String rental_status;

    public rentalEntity() {
    }

    public rentalEntity(String rental_id, String customer_id, String equipment_id, String branch_id,
            LocalDate start_date, LocalDate due_date, LocalDate actual_return_date, float total_rent, float sdh,
            float mdh, float lrd, float final_pay, String payment_status, String rental_status) {
        this.rental_id = rental_id;
        this.customer_id = customer_id;
        this.equipment_id = equipment_id;
        this.branch_id = branch_id;
        this.start_date = start_date;
        this.due_date = due_date;
        this.actual_return_date = actual_return_date;
        this.total_rent = total_rent;
        this.sdh = sdh;
        this.mdh = mdh;
        this.lrd = lrd;
        this.final_pay = final_pay;
        this.payment_status = payment_status;
        this.rental_status = rental_status;
    }

    public String getRental_id() {
        return rental_id;
    }

    public void setRental_id(String rental_id) {
        this.rental_id = rental_id;
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

    public LocalDate getStart_date() {
        return start_date;
    }

    public void setStart_date(LocalDate start_date) {
        this.start_date = start_date;
    }

    public LocalDate getDue_date() {
        return due_date;
    }

    public void setDue_date(LocalDate due_date) {
        this.due_date = due_date;
    }

    public LocalDate getActual_return_date() {
        return actual_return_date;
    }

    public void setActual_return_date(LocalDate actual_return_date) {
        this.actual_return_date = actual_return_date;
    }

    public float getTotal_rent() {
        return total_rent;
    }

    public void setTotal_rent(float total_rent) {
        this.total_rent = total_rent;
    }

    public float getSdh() {
        return sdh;
    }

    public void setSdh(float sdh) {
        this.sdh = sdh;
    }

    public float getMdh() {
        return mdh;
    }

    public void setMdh(float mdh) {
        this.mdh = mdh;
    }

    public float getLrd() {
        return lrd;
    }

    public void setLrd(float lrd) {
        this.lrd = lrd;
    }

    public float getFinal_pay() {
        return final_pay;
    }

    public void setFinal_pay(float final_pay) {
        this.final_pay = final_pay;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
    }

    public String getRental_status() {
        return rental_status;
    }

    public void setRental_status(String rental_status) {
        this.rental_status = rental_status;
    }

    @Override
    public String toString() {
        return "rentalDto{" +
                "rental_id='" + rental_id + '\'' +
                ", customer_id='" + customer_id + '\'' +
                ", equipment_id='" + equipment_id + '\'' +
                ", branch_id='" + branch_id + '\'' +
                ", start_date=" + start_date +
                ", due_date=" + due_date +
                ", actual_return_date=" + actual_return_date +
                ", total_rent=" + total_rent +
                ", sdh=" + sdh +
                ", mdh=" + mdh +
                ", lrd=" + lrd +
                ", final_pay=" + final_pay +
                ", payment_status='" + payment_status + '\'' +
                ", rental_status='" + rental_status + '\'' +
                '}';
    }
}
