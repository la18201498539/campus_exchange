package edu.bu.cs673.secondhand.service.impl;

import edu.bu.cs673.secondhand.domain.IdleItem;
import edu.bu.cs673.secondhand.domain.IdleItemExample;
import edu.bu.cs673.secondhand.domain.Order;
import edu.bu.cs673.secondhand.domain.OrderExample;
import edu.bu.cs673.secondhand.mapper.IdleItemMapper;
import edu.bu.cs673.secondhand.mapper.OrderMapper;
import edu.bu.cs673.secondhand.service.OrderService;
import edu.bu.cs673.secondhand.utils.OrderTask;
import edu.bu.cs673.secondhand.utils.OrderTaskHandler;
import edu.bu.cs673.secondhand.vo.PageVo;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

/***
 Email: ybinman@bu.edu
 DateTime: 10/12/24-22:49
 *****/
@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = Logger.getLogger(OrderServiceImpl.class.getName());

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private IdleItemMapper idleItemMapper;

    private static HashMap<Integer, ReentrantLock> lockMap=new HashMap<>();
    static {
        ReentrantLock lock=new ReentrantLock(true);
        for(int i=0;i<100;i++){
            lockMap.put(i,new ReentrantLock(true));
        }
    }

    @Override
    public boolean addOrder(Order order) {
        IdleItem idleItem = idleItemMapper.selectByPrimaryKey(order.getIdleId());
        logger.info("Item Status: " + String.valueOf(idleItem.getIdleStatus()));
        if(idleItem.getIdleStatus()!=1){
            return false;
        }
        idleItem.setId(order.getIdleId());
        idleItem.setUserId(idleItem.getUserId());
        idleItem.setIdleStatus((byte)2);

        int key= (int) (order.getIdleId()%100);
        ReentrantLock lock=lockMap.get(key);
        boolean flag;
        try {
            lock.lock();
            flag=addOrderHelp(idleItem,order);
        }finally {
            lock.unlock();
        }
        return flag;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean addOrderHelp(IdleItem idleItem,Order orderModel){
        IdleItem idleItemModel=idleItemMapper.selectByPrimaryKey(orderModel.getIdleId());
        if(idleItemModel.getIdleStatus()!=1){
            return false;
        }
        if(idleItemMapper.updateByPrimaryKeySelective(idleItem)==1){
            if(orderMapper.insert(orderModel)==1){
                orderModel.setOrderStatus((byte) 4);
                //cancel order if unpaid after 30 minites
                OrderTaskHandler.addOrder(new OrderTask(orderModel,30*60));
                return true;
            }else {
                new RuntimeException();
            }
        }
        return false;
    }

    @Override
    public Order getOrder(Long id) {
        Order orderModel = orderMapper.selectByPrimaryKey(id);
        if(orderModel==null){
            return null;
        }
        orderModel.setIdleItem(idleItemMapper.selectByPrimaryKey(orderModel.getIdleId()));
        return orderModel;
    }

    @Override
    public PageVo<Order> findOrderByNumber(String searchValue, int page, int nums) {
        RowBounds rowBounds = new RowBounds((page - 1) * nums, nums);
        OrderExample orderExample = new OrderExample();
        OrderExample.Criteria criteria = orderExample.createCriteria();
        criteria.andOrderNumberEqualTo(searchValue);
        List<Order> list = orderMapper.selectByExampleWithRowbounds(orderExample, rowBounds);

        if(list.size()>0){
            List<Long> idleIdList=new ArrayList<>();
            for(Order i:list){
                idleIdList.add(i.getIdleId());
            }

            IdleItemExample idleItemExample = new IdleItemExample();
            IdleItemExample.Criteria criteria1 = idleItemExample.createCriteria();
            criteria1.andIdIn(idleIdList);
            List<IdleItem> idleItemModelList=idleItemMapper.selectByExample(idleItemExample);
            Map<Long,IdleItem> map = new HashMap<>();
            for(IdleItem idle:idleItemModelList){
                map.put(idle.getId(),idle);
            }
            for(Order i:list){
                i.setIdleItem(map.get(i.getIdleId()));
            }
        }

        return new PageVo<Order>(list,1);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateOrder(Order orderModel) {
        orderModel.setOrderNumber(null);
        orderModel.setUserId(null);
        orderModel.setIdleId(null);
        orderModel.setCreateTime(null);
        if(orderModel.getOrderStatus()==4){
            //TODO
            Order o=orderMapper.selectByPrimaryKey(orderModel.getId());
            if(o.getOrderStatus()!=0){
                return false;
            }
            IdleItem idleItemModel=idleItemMapper.selectByPrimaryKey(o.getIdleId());
            if(idleItemModel.getIdleStatus() == 2){
                IdleItem idleItem=new IdleItem();
                idleItem.setId(o.getIdleId());
                idleItem.setUserId(idleItemModel.getUserId());
                idleItem.setIdleStatus((byte)1);
                if(orderMapper.updateByPrimaryKeySelective(orderModel)==1){
                    if(idleItemMapper.updateByPrimaryKeySelective(idleItem)==1){
                        return true;
                    }else {
                        new RuntimeException();
                    }
                }
                return false;
            }else{
                if(orderMapper.updateByPrimaryKeySelective(orderModel)==1){
                    return true;
                }else {
                    new RuntimeException();
                }
            }
        }
        return orderMapper.updateByPrimaryKeySelective(orderModel)==1;
    }

    @Override
    public List<Order> getMyOrder(Long userId) {
        OrderExample orderExample = new OrderExample();
        OrderExample.Criteria criteria = orderExample.createCriteria();
        criteria.andUserIdEqualTo(userId);
        List<Order> list=orderMapper.selectByExample(orderExample);
        if(list.size()>0){
            List<Long> idleIdList=new ArrayList<>();
            for(Order i:list){
                idleIdList.add(i.getIdleId());
            }

            IdleItemExample idleItemExample = new IdleItemExample();
            IdleItemExample.Criteria criteria1 = idleItemExample.createCriteria();
            criteria1.andIdIn(idleIdList);
            List<IdleItem> idleItemModelList=idleItemMapper.selectByExample(idleItemExample);
            Map<Long,IdleItem> map=new HashMap<>();
            for(IdleItem idle:idleItemModelList){
                map.put(idle.getId(),idle);
            }
            for(Order i:list){
                i.setIdleItem(map.get(i.getIdleId()));
            }
        }
        return list;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<Order> getMySoldIdle(Long userId) {
        IdleItemExample idleItemExample = new IdleItemExample();
        IdleItemExample.Criteria criteria = idleItemExample.createCriteria();
        criteria.andUserIdEqualTo(userId);
        List<IdleItem> list=idleItemMapper.selectByExample(idleItemExample);
        List<Order> orderList=null;
        if(list.size()>0){
            List<Long> idleIdList=new ArrayList<>();
            for(IdleItem i:list){
                idleIdList.add(i.getId());
            }

            OrderExample orderExample = new OrderExample();
            OrderExample.Criteria criteria1 = orderExample.createCriteria();
            criteria1.andIdleIdIn(idleIdList);
            orderList=orderMapper.selectByExample(orderExample);
            Map<Long,IdleItem> map=new HashMap<>();
            for(IdleItem idle:list){
                map.put(idle.getId(),idle);
            }
            for(Order o:orderList){
                o.setIdleItem(map.get(o.getIdleId()));
            }
        }
        return orderList;
    }

    @Override
    public PageVo<Order> getAllOrder(int page, int nums) {
        RowBounds rowBounds = new RowBounds((page - 1) * nums, nums);
        OrderExample orderExample = new OrderExample();
        OrderExample.Criteria criteria1 = orderExample.createCriteria();
        List<Order> list=orderMapper.selectByExampleWithRowbounds(orderExample, rowBounds);
        if(list.size()>0){
            List<Long> idleIdList=new ArrayList<>();
            for(Order i:list){
                idleIdList.add(i.getIdleId());
            }

            IdleItemExample idleItemExample = new IdleItemExample();
            IdleItemExample.Criteria criteria = idleItemExample.createCriteria();
            criteria.andIdIn(idleIdList);
            List<IdleItem> idleItemModelList=idleItemMapper.selectByExample(idleItemExample);
            Map<Long,IdleItem> map=new HashMap<>();
            for(IdleItem idle:idleItemModelList){
                map.put(idle.getId(),idle);
            }
            for(Order i:list){
                i.setIdleItem(map.get(i.getIdleId()));
            }
        }
        long count=orderMapper.countByExample(orderExample);
        return new PageVo<>(list,count);
    }

    @Override
    public boolean deleteOrder(long id) {
        return orderMapper.deleteByPrimaryKey(id)==1;
    }
}
