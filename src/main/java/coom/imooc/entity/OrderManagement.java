package coom.imooc.entity;

public class OrderManagement {
    //订单编号
    private String orderNumber;
    //订单总价
    private String totalOrderPrice;
    //订单状态
    private String orderStatus;
    //创建时间
    private String creationTime;
    //操作
    private String operating;

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getTotalOrderPrice() {
        return totalOrderPrice;
    }

    public void setTotalOrderPrice(String totalOrderPrice) {
        this.totalOrderPrice = totalOrderPrice;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getOperating() {
        return operating;
    }

    public void setOperating(String operating) {
        this.operating = operating;
    }

    @Override
    public String toString() {
        return "OrderManagement{" +
                "orderNumber='" + orderNumber + '\'' +
                ", totalOrderPrice='" + totalOrderPrice + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", creationTime='" + creationTime + '\'' +
                ", operating='" + operating + '\'' +
                '}';
    }
}
