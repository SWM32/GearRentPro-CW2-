package lk.cmjd.entity;

public class membershipDiscountEntity {
    private String tierId;
    private String tiername;
    private float discount;
    private float maxDep;

    public membershipDiscountEntity() {
    }

    public membershipDiscountEntity(String tierId, String tiername, float discount, float maxDep) {
        this.tierId = tierId;
        this.tiername = tiername;
        this.discount = discount;
        this.maxDep = maxDep;
    }

    public String getTierId() {
        return tierId;
    }

    public void setTierId(String tierId) {
        this.tierId = tierId;
    }

    public String getTiername() {
        return tiername;
    }

    public void setTiername(String tiername) {
        this.tiername = tiername;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public float getMaxDep() {
        return maxDep;
    }

    public void setMaxDep(float maxDep) {
        this.maxDep = maxDep;
    }

    @Override
    public String toString() {
        return "membershipDiscountDto{" +
                "tierId='" + tierId + '\'' +
                ", tiername='" + tiername + '\'' +
                ", discount=" + discount + '\'' +
                ", maxDep=" + maxDep +
                '}';
    }
}
