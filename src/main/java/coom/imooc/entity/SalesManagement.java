package coom.imooc.entity;

public class SalesManagement {
    //商品名称
    public String productName;
    //商品图片的地址
    public String pictureAddress;
    //商品销售状态
    public String salesStatus;
    //商品的序号
    public String serialNumber;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPictureAddress() {
        return pictureAddress;
    }

    public void setPictureAddress(String pictureAddress) {
        this.pictureAddress = pictureAddress;
    }

    public String getSalesStatus() {
        return salesStatus;
    }

    public void setSalesStatus(String salesStatus) {
        this.salesStatus = salesStatus;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    @Override
    public String toString() {
        return "SalesManagement{" +
                "productName='" + productName + '\'' +
                ", pictureAddress='" + pictureAddress + '\'' +
                ", salesStatus='" + salesStatus + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                '}';
    }
}
