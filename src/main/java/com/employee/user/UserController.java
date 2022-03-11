package com.employee.user;

import com.employee.role.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserController {


    @Autowired
    private UserService service;

    @GetMapping("/users")
    public String showUserList(Model model){
        return viewPage(model, 1,"id","asc",null);
    }

    @GetMapping("/users/new")
    public String showUserForm(Model model){
        model.addAttribute("user",new User());
        List<Roles> listRoles = service.listRoles();
        model.addAttribute("listRoles",listRoles);
        model.addAttribute("pageTitle","Add New User");
        return "user_form";
    }

    @PostMapping("/users/save")
    public String savedUser (User user, RedirectAttributes ra){
        service.save(user);
        ra.addFlashAttribute("message","The user has been saved successfully");
        return "redirect:/users";
    }

    @PostMapping("/process_register")
    public String processRegister(User user,RedirectAttributes ra) {
        if(service.findByEmail(user.getEmail())!=null){
            return "redirect:/register?failed";
        }
        user.setEnabled(true);
        service.save(user);

        return "redirect:/register?success";
    }



    @GetMapping("/users/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id , Model model,RedirectAttributes ra) throws UserNotFoundException {
        try {
            User user = service.get(id);
            List<Roles> listRoles = service.listRoles();
            model.addAttribute("user",user);
            model.addAttribute("pageTitle","Edit User (ID: "+id+" )");
            model.addAttribute("listRoles",listRoles);
            model.addAttribute("check", true);
            return "user_form";
        }catch (UserNotFoundException e){
            e.printStackTrace();
            return "redirect:/users";
        }
    }


    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id ,RedirectAttributes ra) throws UserNotFoundException {
        try {
            service.delete(id);
        }catch (UserNotFoundException e){
            ra.addFlashAttribute("message",e.getMessage());
        }
            return "redirect:/users";
    }

    @RequestMapping("/page/{pageNum}")
    public String viewPage(Model model,
                           @PathVariable(name = "pageNum") int pageNum,
                           @Param("sortField") String sortField,
                           @Param("sortDir") String sortDir,
                           @Param(value="keyword") String keyword
                          ) {

        Page<User> page = service.listAll(pageNum,sortField,sortDir,keyword);



        List<User> listUsers = page.getContent();

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");


        model.addAttribute("listUsers", listUsers);

        model.addAttribute("keyword",keyword);

        return "users";
    }




}
