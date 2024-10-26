package edu.bu.cs673.secondhand.service;

import edu.bu.cs673.secondhand.domain.OrderAddress;

/***
 Email: ybinman@bu.edu
 DateTime: 10/26/24-16:24
 *****/
public interface OrderAddressService {
    /**
     *
     * @param orderAddressModel
     * @return
     */
    boolean addOrderAddress(OrderAddress orderAddressModel);

    /**
     *
     * @param orderAddressModel
     * @return
     */
    boolean updateOrderAddress(OrderAddress orderAddressModel);

    /**
     *
     * @param orderId
     * @return
     */
    OrderAddress getOrderAddress(Long orderId);
}
