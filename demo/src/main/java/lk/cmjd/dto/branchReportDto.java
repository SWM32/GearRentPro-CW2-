package lk.cmjd.dto;

public class branchReportDto {
    private String branch_id;
    private String name;
    private int noOfRentals;
    private double total_revenue;
    private double total_late;
    private double total_damage;

    public branchReportDto() {
    }

    public branchReportDto(String branch_id, String name, int noOfRentals,
            double total_revenue, double total_late, double total_damage) {
        this.branch_id = branch_id;
        this.name = name;
        this.noOfRentals = noOfRentals;
        this.total_revenue = total_revenue;
        this.total_late = total_late;
        this.total_damage = total_damage;
    }

    public String getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(String branch_id) {
        this.branch_id = branch_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNoOfRentals() {
        return noOfRentals;
    }

    public void setNoOfRentals(int noOfRentals) {
        this.noOfRentals = noOfRentals;
    }

    public double getTotal_revenue() {
        return total_revenue;
    }

    public void setTotal_revenue(double total_revenue) {
        this.total_revenue = total_revenue;
    }

    public double getTotal_late() {
        return total_late;
    }

    public void setTotal_late(double total_late) {
        this.total_late = total_late;
    }

    public double getTotal_damage() {
        return total_damage;
    }

    public void setTotal_damage(double total_damage) {
        this.total_damage = total_damage;
    }

    @Override
    public String toString() {
        return "branchReportDto{" +
                "branch_id='" + branch_id + '\'' +
                ", name='" + name + '\'' +
                ", noOfRentals=" + noOfRentals +
                ", total_revenue=" + total_revenue +
                ", total_late=" + total_late +
                ", total_damage=" + total_damage +
                '}';
    }
}