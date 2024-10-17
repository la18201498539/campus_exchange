package edu.bu.cs673.secondhand.utils;

import edu.bu.cs673.secondhand.service.OrderService;

import java.util.concurrent.DelayQueue;
import java.util.logging.Logger;

/***
 Email: ybinman@bu.edu
 DateTime: 10/12/24-23:11
 *****/
public class OrderTaskHandler {

    private static final Logger logger = Logger.getLogger(OrderTaskHandler.class.getName());

    public static OrderService orderService=null;

    private static DelayQueue<OrderTask> delayQueue = new DelayQueue<>();

    public static void run(){
        new Thread(() -> {
            logger.info("Success !");
            while (true) {
                if(orderService!=null&&delayQueue.size() >0){
                    OrderTask orderTask = delayQueue.poll();
                    if (orderTask != null) {
                        if(orderService.updateOrder(orderTask.getOrderModel())){
                            logger.info("Cancel Order successfully："+orderTask.getOrderModel());
                        }else {
                            logger.info("Cancel Order："+orderTask.getOrderModel());
                        }

                    }
                }
            }
        }).start();

    }

    public static void addOrder(OrderTask o){
        logger.info("add Task："+o);
        delayQueue.put(o);
    }
}
