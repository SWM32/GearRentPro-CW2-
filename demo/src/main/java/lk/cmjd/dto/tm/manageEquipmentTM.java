package lk.cmjd.dto.tm;

public class manageEquipmentTM {
    private String equipment_id;
    private String branch_id;
    private String category_id;
    private String brand;
    private String model;
    private int year;
    private float bdp;
    private float sda;
    private String status;

    public manageEquipmentTM() {
    }

    public manageEquipmentTM(String equipment_id, String branch_id, String category_id, String brand, String model,
            int year, float bdp, float sda, String status) {
        this.equipment_id = equipment_id;
        this.branch_id = branch_id;
        this.category_id = category_id;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.bdp = bdp;
        this.sda = sda;
        this.status = status;
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

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public float getBdp() {
        return bdp;
    }

    public void setBdp(float bdp) {
        this.bdp = bdp;
    }

    public float getSda() {
        return sda;
    }

    public void setSda(float sda) {
        this.sda = sda;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "equipmentDto [equipment_id=" + equipment_id + ", branch_id=" + branch_id + ", category_id="
                + category_id + ", brand=" + brand + ", model=" + model + ", year=" + year + ", bdp=" + bdp
                + ", sda=" + sda + ", status=" + status + "]";
    }
}
