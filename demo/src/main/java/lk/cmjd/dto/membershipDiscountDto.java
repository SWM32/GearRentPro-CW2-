package lk.cmjd.dto;

public class membershipDiscountDto {
    private String tierId;
    private String tiername;
    private float discount;

    public membershipDiscountDto() {
    }

    public membershipDiscountDto(String tierId, String tiername, float discount) {
        this.tierId = tierId;
        this.tiername = tiername;
        this.discount = discount;
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

    @Override
    public String toString() {
        return "membershipDiscountDto{" +
                "tierId='" + tierId + '\'' +
                ", tiername='" + tiername + '\'' +
                ", discount=" + discount +
                '}';
    }
}
