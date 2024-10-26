package edu.bu.cs673.secondhand.service.impl;

import edu.bu.cs673.secondhand.domain.OrderAddress;
import edu.bu.cs673.secondhand.domain.OrderAddressExample;
import edu.bu.cs673.secondhand.mapper.OrderAddressMapper;
import edu.bu.cs673.secondhand.service.OrderAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/***
 Email: ybinman@bu.edu
 DateTime: 10/26/24-16:25
 *****/
@Service
public class OrderAddressServiceImpl implements OrderAddressService {

    @Autowired
    OrderAddressMapper orderAddressMapper;

    @Override
    public boolean addOrderAddress(OrderAddress orderAddressModel) {
        return orderAddressMapper.insert(orderAddressModel)==1;
    }

    @Override
    public boolean updateOrderAddress(OrderAddress orderAddressModel) {
        orderAddressModel.setOrderId(null);
        return orderAddressMapper.updateByPrimaryKeySelective(orderAddressModel)==1;
    }

    @Override
    public OrderAddress getOrderAddress(Long orderId) {
        OrderAddressExample orderAddressExample = new OrderAddressExample();
        OrderAddressExample.Criteria criteria = orderAddressExample.createCriteria();
        criteria.andOrderIdEqualTo(orderId);
        return orderAddressMapper.selectByExample(orderAddressExample).get(0);
    }
}
