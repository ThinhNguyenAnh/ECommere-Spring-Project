package com.employee.employeewebapp;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import com.employee.role.RoleRepository;
import com.employee.role.Roles;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository repo;

    @Test
    public void testCreateRole(){
        Roles user = new Roles("USER");
        Roles admin = new Roles("ADMIN");

        repo.save(user);
        repo.save(admin);

        List<Roles> listRoles = repo.findAll();

        assertThat(listRoles.size()).isEqualTo(2);
    }
}
