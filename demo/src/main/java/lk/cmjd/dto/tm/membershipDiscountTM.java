package lk.cmjd.dto.tm;

public class membershipDiscountTM {
    private String tiername;
    private float discount;

    public membershipDiscountTM() {
    }

    public membershipDiscountTM(String tiername, float discount) {
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
