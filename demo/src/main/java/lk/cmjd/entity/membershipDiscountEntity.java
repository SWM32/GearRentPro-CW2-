package lk.cmjd.entity;

public class membershipDiscountEntity {
    private String tiername;
    private float discount;

    public membershipDiscountEntity() {
    }

    public membershipDiscountEntity(String tiername, float discount) {
        this.tiername = tiername;
        this.discount = discount;
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

    @Override
    public String toString() {
        return "membershipDiscountDto{" +
                "tiername='" + tiername + '\'' +
                ", discount=" + discount +
                '}';
    }
}
