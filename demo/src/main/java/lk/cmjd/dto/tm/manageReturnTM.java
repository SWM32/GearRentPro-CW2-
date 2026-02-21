package lk.cmjd.dto.tm;

import java.time.LocalDate;

public class manageReturnTM {
    private String rental_Id;
    private LocalDate actual_return_date;
    private boolean damaged;
    private float late_fee;
    private String damage_description;
    private float damage_charge;

    public manageReturnTM() {
    }

    public manageReturnTM(String rental_Id, LocalDate actual_return_date, boolean damaged,
            float late_fee, String damage_description, float damage_charge) {
        this.rental_Id = rental_Id;
        this.actual_return_date = actual_return_date;
        this.damaged = damaged;
        this.late_fee = late_fee;
        this.damage_description = damage_description;
        this.damage_charge = damage_charge;
    }

    public String getRental_Id() {
        return rental_Id;
    }

    public void setRental_Id(String rental_Id) {
        this.rental_Id = rental_Id;
    }

    public LocalDate getActual_return_date() {
        return actual_return_date;
    }

    public void setActual_return_date(LocalDate actual_return_date) {
        this.actual_return_date = actual_return_date;
    }

    public boolean isDamaged() {
        return damaged;
    }

    public void setDamaged(boolean damaged) {
        this.damaged = damaged;
    }

    public float getLate_fee() {
        return late_fee;
    }

    public void setLate_fee(float late_fee) {
        this.late_fee = late_fee;
    }

    public String getDamage_description() {
        return damage_description;
    }

    public void setDamage_description(String damage_description) {
        this.damage_description = damage_description;
    }

    public float getDamage_charge() {
        return damage_charge;
    }

    public void setDamage_charge(float damage_charge) {
        this.damage_charge = damage_charge;
    }

    @Override
    public String toString() {
        return "returnDto{" +
                "rental_Id='" + rental_Id + '\'' +
                ", actual_return_date=" + actual_return_date +
                ", isDamaged=" + damaged +
                ", late_fee=" + late_fee +
                ", damage_description='" + damage_description + '\'' +
                ", damage_charge=" + damage_charge +
                '}';
    }
}