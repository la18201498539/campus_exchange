package edu.bu.cs673.secondhand.service;

import edu.bu.cs673.secondhand.domain.Order;
import edu.bu.cs673.secondhand.vo.PageVo;

import java.util.List;

/***
 Email: ybinman@bu.edu
 DateTime: 10/12/24-22:44
 *****/
public interface OrderService {
    /**
     * create an Order
     * @param order
     * @return
     */
    boolean addOrder(Order order);

    /**
     * order Detail
     * @param id
     * @return
     */
    Order getOrder(Long id);


    /**
     * get Order by orderNumber with pagenation
     * @param searchValue
     * @param page
     * @param nums
     * @return
     */
    PageVo<Order> findOrderByNumber(String searchValue, int page, int nums);



    /**
     * update Order
     * @param orderModel
     * @return
     */
    boolean updateOrder(Order orderModel);

    /**
     * get orderList by userId
     * @param userId
     * @return
     */
    List<Order> getMyOrder(Long userId);

    /**
     * get orderList of Sell
     * @param userId
     * @return
     */
    List<Order> getMySoldIdle(Long userId);

    /**
     *  list orders
     * @param page
     * @param nums
     * @return
     */
    PageVo<Order> getAllOrder(int page, int nums);

    /**
     * Delete Order by ID
     * @param id
     * @return
     */
    boolean deleteOrder(long id);
}
