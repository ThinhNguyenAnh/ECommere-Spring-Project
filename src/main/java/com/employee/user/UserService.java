package com.employee.user;

import com.employee.role.RoleRepository;
import com.employee.role.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository repo;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private BCryptPasswordEncoder encoder;

    public List<User> listAll() {
        return (List<User>) repo.findAll();
    }

    public void save(User user) {
        Roles roleUser = roleRepo.findByName("USER");

        boolean isUpdatingUser = (user.getId() != null);
        if (isUpdatingUser) {
            User existingUser = repo.findById(user.getId()).get();
            if (user.getPassword().isEmpty()) {
                user.setPassword(existingUser.getPassword());
            } else {
                user.setPassword(encoder.encode(user.getPassword()));
            }
        } else {
            user.setPassword(encoder.encode(user.getPassword()));
            user.addRole(roleUser);
        }
        repo.save(user);
    }

    public User get(Integer id) throws UserNotFoundException {
        Optional<User> result = repo.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        throw new UserNotFoundException("Could not find any users with ID " + id);
    }

    public void delete(Integer id) throws UserNotFoundException {
        Long count = repo.countById(id);
        if (count == null || count == 0) {
            throw new UserNotFoundException("Could not find any users with ID " + id);
        }
        repo.deleteById(id);
    }

    public Page<User> listAll(int pageNum, String sortField, String sortDir, String keyword) {
        int pageSize = 5;
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize,
                sortDir.equals("asc") ? Sort.by(sortField).ascending()
                        : Sort.by(sortField).descending()
        );
        if (keyword != null) {
            return this.repo.findAll(keyword, pageable);
        }

        return repo.findAll(pageable);
    }

    public List<Roles> listRoles() {
        return roleRepo.findAll();
    }

    public void processOAuthPostLogin(String email) {
        User existUser = repo.findByEmail(email);
        Roles roleUser = roleRepo.findByName("USER");
        if (existUser == null) {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.addRole(roleUser);
            newUser.setLastName("");
            newUser.setFirstName("");
            newUser.setPassword("");
            newUser.setProvider(Provider.GOOGLE);
            newUser.setEnabled(true);

            repo.save(newUser);
        }

    }

    public User findByEmail(String email) {
        return repo.findByEmail(email);
    }


    //Reset password
    public void updateResetToken(String token, String email) throws UserNotFoundException {
        User user = repo.findByEmail(email);
        if (user != null) {
            user.setResetPasswordToken(token);
            repo.save(user);
        } else {
            throw new UserNotFoundException("Could not find any user with this email " + email);
        }
    }

    public User getByResetPasswordToken(String token) {
        return repo.findByResetPasswordToken(token);
    }

    public void updatePassword(User user, String newPassword) {
        user.setPassword(encoder.encode(newPassword));

        user.setResetPasswordToken(null);
        repo.save(user);
    }




}
