package lk.cmjd.dto.tm;

public class itemCategoryTM {
    private String CategoryID;
    private String name;
    private String description;
    private float BPF;
    private float WM;
    private float LRDR;
    private float LFPD;

    public itemCategoryTM() {
    }

    public itemCategoryTM(String categoryID, String name, String description, float BPF, float WM, float LRDR,
            float LFPD) {
        CategoryID = categoryID;
        this.name = name;
        this.description = description;
        this.BPF = BPF;
        this.WM = WM;
        this.LRDR = LRDR;
        this.LFPD = LFPD;
    }

    public String getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(String categoryID) {
        CategoryID = categoryID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getBPF() {
        return BPF;
    }

    public void setBPF(float BPF) {
        this.BPF = BPF;
    }

    public float getWM() {
        return WM;
    }

    public void setWM(float WM) {
        this.WM = WM;
    }

    public float getLRDR() {
        return LRDR;
    }

    public void setLRDR(float LRDR) {
        this.LRDR = LRDR;
    }

    public float getLFPD() {
        return LFPD;
    }

    public void setLFPD(float LFPD) {
        this.LFPD = LFPD;
    }

    @Override
    public String toString() {
        return "itemCategoryDto{" +
                "CategoryID='" + CategoryID + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", BPF=" + BPF +
                ", WM=" + WM +
                ", LRDR=" + LRDR +
                ", LFPD=" + LFPD +
                '}';
    }
}
