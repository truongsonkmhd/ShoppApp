package com.example.ShoppApp.sevice.impl;

import com.example.ShoppApp.common.UserStatus;
import com.example.ShoppApp.dto.request.UserPasswordRequestDTO;
import com.example.ShoppApp.dto.request.UserRequestDTO;
import com.example.ShoppApp.dto.request.UserUpdateRequestDTO;
import com.example.ShoppApp.dto.response.UserPageResponse;
import com.example.ShoppApp.dto.response.UserResponse;
import com.example.ShoppApp.exception.ResourceNotFoundException;
import com.example.ShoppApp.model.AddressEntity;
import com.example.ShoppApp.model.UserEntity;
import com.example.ShoppApp.repository.AddressRepository;
import com.example.ShoppApp.repository.UserRepository;
import com.example.ShoppApp.sevice.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j(topic = "USER-SERVICE")
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserPageResponse getAllUsersWithSortBy(String sortBy, int pageNo, int pageSize) {

        // xu ly truong hop FE muon bat dau voi page = 1
        if (pageNo > 0) {
            pageNo -= 1;
        }

        List<Sort.Order> sorts = new ArrayList<>();
        // nếu có giá trị
        if(StringUtils.hasLength(sortBy)){
            // firstName: asc|desc
            Pattern pattern = Pattern.compile("(\\w+?)(:)(.*)");
            Matcher matcher = pattern.matcher(sortBy);
            if (matcher.find()){
                if(matcher.group(3).equalsIgnoreCase("asc")){
                    sorts.add(new Sort.Order(Sort.Direction.ASC,matcher.group(1)));
                }else{
                    sorts.add(new Sort.Order(Sort.Direction.DESC,matcher.group(1)));
                }
            }

        }

        Pageable pageable = PageRequest.of(pageNo,pageSize, Sort.by(sorts));
        Page<UserEntity> users = userRepository.findAll(pageable);


        return getUserPageResponse(pageNo, pageSize, users);
    }

    @Override
    public UserPageResponse getAllUsersWithSortByMultipleColumns(int pageNo, int pageSize, List<String> sorts) {
        // xu ly truong hop FE muon bat dau voi page = 1
        if (pageNo > 0) {
            pageNo -= 1;
        }
        List<Sort.Order> orders = new ArrayList<>();

        for (String sortBy: sorts){

            Pattern pattern = Pattern.compile("(\\w+?)(:)(.*)");

            Matcher matcher = pattern.matcher(sortBy);
            if (matcher.find()){
                if(matcher.group(3).equalsIgnoreCase("asc")){
                    orders.add(new Sort.Order(Sort.Direction.ASC,matcher.group(1)));
                }else{
                    orders.add(new Sort.Order(Sort.Direction.DESC,matcher.group(1)));
                }
            }
        }


        Pageable pageable = PageRequest.of(pageNo,pageSize, Sort.by(orders));

        Page<UserEntity> users = userRepository.findAll(pageable);


        return getUserPageResponse(pageNo, pageSize, users);
    }

    @Override
    public UserPageResponse getAllUsersWithSortByColumnAndSearch(int pageNo, int pageSize, String search, String sortBy) {

    }

    /**
     * Convert UserEntities to user
     *
     * @param page
     * @param pageSize
     * @param userEntities
     * @return
     */
    private static UserPageResponse getUserPageResponse(int page, int pageSize, Page<UserEntity> userEntities) {
        log.info("Convert User Entity Page");
        List<UserResponse> userList = userEntities.stream().map(entity -> UserResponse.builder()
                .id(entity.getId())
                .fistName(entity.getFirstName())
                .lastName(entity.getLastName())
                .gender(entity.getGender())
                .birthday(entity.getBirthday())
                .userName(entity.getUsername())
                .phone(entity.getPhone())
                .email(entity.getEmail())
                .build()

        ).toList();

        UserPageResponse response = new UserPageResponse();
        response.setPageNo(page);
        response.setPageSize(pageSize);
        response.setTotalElements(userEntities.getTotalElements());
        response.setTotalPages(userEntities.getTotalPages());
        response.setUsers(userList);
        return response;
    }

    @Override
    public UserResponse findById(Long id) {

        log.info("Find user by id: {}", id);

        UserEntity userEntity = getUserEntity(id);

        return UserResponse.builder()
                .id(id)
                .fistName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .gender(userEntity.getGender())
                .birthday(userEntity.getBirthday())
                .userName(userEntity.getUsername())
                .phone(userEntity.getPhone())
                .email(userEntity.getEmail())
                .build();
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
    public long saveUser(UserRequestDTO req) {
        log.info("saving user: {}", req);
        UserEntity user = UserEntity.builder()
                .firstName(req.getFirstName())
                .lastName(req.getLastName())
                .gender(req.getGender())
                .birthday(req.getBirthday())
                .email(req.getEmail())
                .phone(req.getPhone())
                .username(req.getUserName())
                .type(req.getType())
                .status(UserStatus.NONE)
                .build();
        userRepository.save(user);
        // đoạn này data chưa được lưu vào data base
        if (user.getId() != null) {

            List<AddressEntity> addresses = new ArrayList<>();
            req.getAddresses().forEach(address -> {
                AddressEntity addressEntity = AddressEntity.builder()
                        .apartmentNumber(address.getApartmentNumber())
                        .floor(address.getFloor())
                        .building(address.getBuilding())
                        .streetNumber(address.getStreetNumber())
                        .street(address.getStreet())
                        .city(address.getCity())
                        .country(address.getCountry())
                        .addressType(address.getAddressType())
                        .userId(user.getId())
                        .build();

                addresses.add(addressEntity);
            });
            addressRepository.saveAll(addresses);
        }
        return user.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(long userId,UserUpdateRequestDTO req) {
        log.info("Updating user: {}", req);
        // Get user by id
        UserEntity user = getUserEntity(userId);
        user.setFirstName(req.getFirstName());
        user.setLastName(req.getLastName());
        user.setGender(req.getGender());
        user.setBirthday(req.getBirthday());

        if(!req.getEmail().equals(user.getEmail())){
            user.setEmail(req.getEmail());
        }

        user.setPhone(req.getPhone());
        user.setUsername(req.getUserName());

        userRepository.save(user);
        log.info("Updated user: {}", req);

        List<AddressEntity> addresses = new ArrayList<>();
        // save address
        req.getAddresses().forEach(address -> {
            AddressEntity addressEntity = addressRepository.findByUserIdAndAddressType(user.getId(), address.getAddressType());
            if (addressEntity == null) {
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

        addressRepository.saveAll(addresses);
        log.info("Updated address: {}", req);
    }

    @Override
    public void changePassword(UserPasswordRequestDTO req) {
        log.info("Changing password for user: {}", req);
        // Get user by id
        UserEntity user = getUserEntity(req.getId());
        if (req.getPassword().equals(req.getConfirmPassword())) {
            user.setPassword(passwordEncoder.encode(req.getPassword()));
        }

        userRepository.save(user);
        log.info("Changed password for user: {}", req);
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting user: {}", id);

        // Get user by id
        UserEntity user = getUserEntity(id);
        user.setStatus(UserStatus.INACTIVE);
        userRepository.save(user);
        log.info("Deleted user: {}", user);

    }

    /**
     * Get user by id
     *
     * @param id
     * @return
     */
    private UserEntity getUserEntity(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
