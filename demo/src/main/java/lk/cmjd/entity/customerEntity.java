package lk.cmjd.entity;

public class customerEntity {
    private String cusId;
    private String name;
    private String nic_pass;
    private String contact;
    private String email;
    private String address;
    private String mid;
    private float dep;

    public customerEntity() {
    }

    public customerEntity(String cusId, String name, String nic_pass, String contact, String email, String address,
            String mid, float dep) {
        this.cusId = cusId;
        this.name = name;
        this.nic_pass = nic_pass;
        this.contact = contact;
        this.email = email;
        this.address = address;
        this.mid = mid;
        this.dep = dep;
    }

    public String getCusId() {
        return cusId;
    }

    public void setCusId(String cusId) {
        this.cusId = cusId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNic_pass() {
        return nic_pass;
    }

    public void setNic_pass(String nic_pass) {
        this.nic_pass = nic_pass;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public float getDep() {
        return dep;
    }

    public void setDep(float dep) {
        this.dep = dep;
    }

    @Override
    public String toString() {
        return "customerDto [cusId=" + cusId + ", name=" + name + ", nic_pass=" + nic_pass + ", contact=" + contact
                + ", email=" + email + ", address=" + address + ", mid=" + mid + ", dep=" + dep + "]";
    }
}
