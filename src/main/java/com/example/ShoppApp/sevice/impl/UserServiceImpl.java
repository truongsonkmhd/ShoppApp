package com.example.ShoppApp.sevice.impl;

import com.example.ShoppApp.common.UserStatus;
import com.example.ShoppApp.controller.request.UserCreationRequest;
import com.example.ShoppApp.controller.request.UserPasswordRequest;
import com.example.ShoppApp.controller.request.UserUpdateRequest;
import com.example.ShoppApp.exception.ResourceNotFoundException;
import com.example.ShoppApp.model.AddressEntity;
import com.example.ShoppApp.model.UserEntity;
import com.example.ShoppApp.model.UserResponse;
import com.example.ShoppApp.repository.AddressRepository;
import com.example.ShoppApp.repository.UserRepository;
import com.example.ShoppApp.sevice.UserService;
import io.swagger.v3.oas.annotations.servers.Server;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j(topic = "USER-SERVICE")
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserResponse> findAll() {
        return null;
    }

    @Override
    public UserResponse findById(Long id) {
        return null;
    }

    @Override
    public UserResponse findByUsername(String username) {
        return null;
    }

    @Override
    public UserResponse findByEmail(String email) {
        return null;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    // nếu gặp vấn đề gì trong qua trình lưu address thì sẽ rollback tất cả user
    public long save(UserCreationRequest req) {
        log.info("saving user: {}" , req);
        UserEntity user = new UserEntity();
        user.setFirstName(req.getFirstName());
        user.setLastName(req.getLastName());
        user.setGender(req.getGender());
        user.setBirthday(req.getBirthday());
        user.setEmail(req.getEmail());
        user.setPhone(req.getPhone());
        user.setUsername(req.getUserName());
        user.setType(req.getType());
        user.setStatus(UserStatus.NONE);
        userRepository.save(user);
        // đoạn này data chưa được lưu vào data base
        if(user.getId() != null){

            List<AddressEntity> addresses = new ArrayList<>();
            req.getAddresses().forEach(address->{
                AddressEntity addressEntity = new AddressEntity();
                addressEntity.setApartmentNumber(address.getApartmentNumber());
                addressEntity.setFloor(address.getFloor());
                addressEntity.setBuilding(address.getBuilding());
                addressEntity.setStreetNumber(address.getStreetNumber());
                addressEntity.setStreet(address.getStreet());
                addressEntity.setCity(address.getCity());
                addressEntity.setCountry(address.getCountry());
                addressEntity.setAddressType(address.getAddressType());
                addressEntity.setUserId(user.getId());
                addresses.add(addressEntity);
            });
            addressRepository.saveAll(addresses);
        }
        return user.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(UserUpdateRequest req) {
        log.info("Updating user: {}", req);
        // Get user by id
        UserEntity user = getUserEntity(req.getId());
        user.setFirstName(req.getFirstName());
        user.setLastName(req.getLastName());
        user.setGender(req.getGender());
        user.setBirthday(req.getBirthday());
        user.setEmail(req.getEmail());
        user.setPhone(req.getPhone());
        user.setUsername(req.getUserName());

        userRepository.save(user);
        log.info("Updated user: {}", req);

        List<AddressEntity> addresses = new ArrayList<>();
        // save address
        req.getAddresses().forEach(address->{
            AddressEntity addressEntity = addressRepository.findByUserIdAndAddressType(user.getId(), address.getAddressType());
            if(addressEntity == null){
                addressEntity = new AddressEntity();
            }
            addressEntity.setApartmentNumber(address.getApartmentNumber());
            addressEntity.setFloor(address.getFloor());
            addressEntity.setBuilding(address.getBuilding());
            addressEntity.setStreetNumber(address.getStreetNumber());
            addressEntity.setStreet(address.getStreet());
            addressEntity.setCity(address.getCity());
            addressEntity.setCountry(address.getCountry());
            addressEntity.setAddressType(address.getAddressType());
            addressEntity.setUserId(user.getId());
            addresses.add(addressEntity);
        });
        //save address
        addressRepository.saveAll(addresses);
        log.info("Updated address: {}", req);
        // set data

        // sava to data base
    }

    @Override
    public void changePassword(UserPasswordRequest req) {
        log.info("Changing password for user: {}" , req);
        // Get user by id
        UserEntity user = getUserEntity(req.getId());
        if(req.getPassword().equals(req.getConfirmPassword())){
            user.setPassword(passwordEncoder.encode(req.getPassword()));
        }

        userRepository.save(user);
        log.info("Changed password for user: {}" , req);
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting user: {}" , id);

        // Get user by id
        UserEntity user = getUserEntity(id);
        user.setStatus(UserStatus.INACTIVE);
        userRepository.save(user);
        log.info("Deleted user: {}" , user);

    }

    /**
     * Get user by id
     * @param id
     * @return
     */
    private UserEntity getUserEntity(Long id){
        return userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User not found"));
    }
}
