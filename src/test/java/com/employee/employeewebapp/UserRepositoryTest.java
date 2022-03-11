package com.employee.employeewebapp;

import com.employee.role.RoleRepository;
import com.employee.role.Roles;
import com.employee.user.User;
import com.employee.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepo;

    @Test
    public void testAddNew(){
        Roles roleUser = roleRepo.findByName("USER");

        User user = new User();
        user.setEmail("Alexdd@fpt.edu.vn");
        user.setFirstName("Alexs");
        user.setLastName("Stevensond");
        user.setPassword("1234");

        user.addRole(roleUser);

        User savedUser = userRepo.save(user);

        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getRoles().size()).isEqualTo(1);
    }
}
