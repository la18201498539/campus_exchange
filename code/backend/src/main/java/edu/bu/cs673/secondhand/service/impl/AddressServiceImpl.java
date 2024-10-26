package edu.bu.cs673.secondhand.service.impl;

import edu.bu.cs673.secondhand.domain.Address;
import edu.bu.cs673.secondhand.domain.AddressExample;
import edu.bu.cs673.secondhand.mapper.AddressMapper;
import edu.bu.cs673.secondhand.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/***
 Email: ybinman@bu.edu
 DateTime: 10/26/24-15:36
 *****/
@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    AddressMapper addressMapper;

    @Override
    public List<Address> getAddressByUser(Long userId) {
        AddressExample addressExample = new AddressExample();
        AddressExample.Criteria criteria = addressExample.createCriteria();
        criteria.andUserIdEqualTo(userId);
        return addressMapper.selectByExample(addressExample);
    }

    @Override
    public Address getAddressById(Long id, Long userId) {
        Address address = addressMapper.selectByPrimaryKey(id);
        if(userId.equals(address.getUserId())){
            return address;
        }
        return null;
    }

    @Override
    public boolean addAddress(Address addressModel) {
        if(addressModel.getDefaultFlag()){
            Address a = new Address();
            a.setDefaultFlag(false);
            a.setUserId(addressModel.getUserId());
            addressMapper.updateByUserIdSelective(a);
        }else {
            List<Address> list=addressMapper.getDefaultAddress(addressModel.getUserId());
            if(null==list||0==list.size()){
                addressModel.setDefaultFlag(true);
            }
        }
        return addressMapper.insert(addressModel)==1;
    }

    @Override
    public boolean updateAddress(Address addressModel) {
        if(addressModel.getDefaultFlag()){
            Address a = new Address();
            a.setDefaultFlag(false);
            a.setUserId(addressModel.getUserId());
            addressMapper.updateByUserIdSelective(a);
        }else{
            AddressExample addressExample = new AddressExample();
            AddressExample.Criteria criteria = addressExample.createCriteria();
            criteria.andUserIdEqualTo(addressModel.getUserId());
            List<Address> list=addressMapper.selectByExample(addressExample);
            for(Address a:list){
                if(a.getDefaultFlag()&& a.getId().equals(addressModel.getId())){
                    Address a1 = new Address();
                    a1.setId(list.get(0).getId());
                    a1.setDefaultFlag(true);
                    return addressMapper.updateByPrimaryKeySelective(addressModel)==1&&
                            addressMapper.updateByPrimaryKeySelective(a1)==1;
                }
            }
        }
        return addressMapper.updateByPrimaryKeySelective(addressModel)==1;
    }

    @Override
    public boolean deleteAddress(Address addressModel) {
        AddressExample addressExample = new AddressExample();
        AddressExample.Criteria criteria = addressExample.createCriteria();
        criteria.andUserIdEqualTo(addressModel.getUserId());
        criteria.andIdEqualTo(addressModel.getId());
        return addressMapper.deleteByExample(addressExample)==1;
    }
}
