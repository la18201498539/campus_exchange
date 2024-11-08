package edu.bu.cs673.secondhand.service;

import edu.bu.cs673.secondhand.domain.Address;

import java.util.List;

/***
 Email: ybinman@bu.edu
 DateTime: 10/26/24-15:35
 *****/
public interface AddressService {

    /**
     *
     * @param userId
     * @return
     */
    List<Address> getAddressByUser(Long userId);

    /**
     *
     * @param id
     * @param userId
     * @return
     */
    Address getAddressById(Long id,Long userId);

    /**
     *
     * @param addressModel
     * @return
     */
    boolean addAddress(Address addressModel);

    /**
     *
     * @param addressModel
     * @return
     */
    boolean updateAddress(Address addressModel);

    /**
     *
     * @param addressModel
     * @return
     */
    boolean deleteAddress(Address addressModel);
}
