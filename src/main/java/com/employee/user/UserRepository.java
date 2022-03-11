package com.employee.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User,Integer> {
    public Long countById(Integer id);

    @Query("SELECT u FROM User u WHERE CONCAT(u.email,u.firstName,u.lastName) LIKE %?1%")
    public Page<User> findAll (String keyword , Pageable pageable);

    User findByEmail(String username);

     User findByResetPasswordToken(String token);


}
