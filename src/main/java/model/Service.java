package model;

import java.util.List;

public class Service {
    private Integer id;
    private Integer categoryId;
    private String nameUA;
    private String nameEN;
    private Integer priceUAH;
    private Integer priceUSD;
    private List<Master> mastersList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getNameUA() {
        return nameUA;
    }

    public void setNameUA(String nameUA) {
        this.nameUA = nameUA;
    }

    public String getNameEN() {
        return nameEN;
    }

    public void setNameEN(String nameEN) {
        this.nameEN = nameEN;
    }

    public Integer getPriceUAH() {
        return priceUAH;
    }

    public void setPriceUAH(Integer priceUAH) {
        this.priceUAH = priceUAH;
    }

    public Integer getPriceUSD() {
        return priceUSD;
    }

    public void setPriceUSD(Integer priceUSD) {
        this.priceUSD = priceUSD;
    }

    public List<Master> getMastersList() {
        return mastersList;
    }

    public void setMastersList(List<Master> mastersList) {
        this.mastersList = mastersList;
    }
}
