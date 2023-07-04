package com.finalTest.library.user.Controller;

import com.finalTest.library.privileges.entity.Privilege;
import com.finalTest.library.privileges.entity.PrivilegeRepository;
import com.finalTest.library.privileges.service.PrivilegeService;
import com.finalTest.library.user.entity.User;
import com.finalTest.library.user.entity.UserRepository;
import com.finalTest.library.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private PrivilegeService privilegeService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/")
    public String getHome(){
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/register")
    public String showNewUserForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "register";
    }
    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("user") User user){
        try {
            String plainPassword = user.getPassword();
            String hashedPassword = bCryptPasswordEncoder.encode(plainPassword);
            user.setPassword(hashedPassword);
            user.setPrivilege(privilegeService.getPrivilegeByName());
            userService.saveUser(user);
            return "redirect:/";
        } catch (Exception e) {
            return "redirect:/register?error=Bad registration";
        }
    }
}
