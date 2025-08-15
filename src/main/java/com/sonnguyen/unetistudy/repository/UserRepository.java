package com.example.ShoppApp.repository;

import com.example.ShoppApp.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    @Query(value = "select u from User u where u.status = 'ACTIVE'  " +
            "and lower(u.firstName) like :keyword" +
            " or lower(u.lastName)  like :keyword" +
            " or lower(u.username) like :keyword" +
            " or lower(u.phone) like :keyword" +
            " or lower(u.email) like :keyword")
    Page<User> searchByKeyWord(String keyword , Pageable pageable);

    User findByUsername(String username);

    User findByEmail(String email);
}
