package edu.bu.cs673.secondhand.utils;

import com.fasterxml.jackson.annotation.JsonFormat;
import edu.bu.cs673.secondhand.domain.Order;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;


/***
 Email: ybinman@bu.edu
 DateTime: 10/12/24-23:05
 *****/
public class OrderTask implements Delayed {

    @JsonFormat(locale = "en", timezone = "UTC-5", pattern = "yyyy-MM-dd HH:mm:ss")
    private long time;

    private Order orderModel;

    public OrderTask(){

    }

    public OrderTask(Order orderModel, long time) {
        this.orderModel = orderModel;
        this.time = System.currentTimeMillis()+1000*time;
    }

    @Override
    public long getDelay(java.util.concurrent.TimeUnit unit) {
        return time - System.currentTimeMillis();
    }

    @Override
    public int compareTo(Delayed o) {
        OrderTask Order = (OrderTask) o;
        long diff = this.time - Order.time;
        if (diff <= 0) {
            return -1;
        } else {
            return 1;
        }
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public Order getOrderModel() {
        return orderModel;
    }

    public void setOrderModel(Order orderModel) {
        this.orderModel = orderModel;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"time\":")
                .append(time);
        sb.append(",\"orderModel\":")
                .append(orderModel);
        sb.append('}');
        return sb.toString();
    }

}
