package models;

public class OrderHistory {
    private String id;
    private String time;
    private String status;
    private String total;

    public OrderHistory() {

    }

    public OrderHistory(String id, String time, String status, String total) {
        this.id = id;
        this.time = time;
        this.status = status;
        this.total = total;
    }
}
