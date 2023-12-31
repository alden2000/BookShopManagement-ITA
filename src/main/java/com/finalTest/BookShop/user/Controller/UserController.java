package com.finalTest.BookShop.user.Controller;

import com.finalTest.BookShop.books.entity.Book;
import com.finalTest.BookShop.books.service.BookService;
import com.finalTest.BookShop.privileges.entity.Privilege;
import com.finalTest.BookShop.privileges.entity.PrivilegeRepository;
import com.finalTest.BookShop.privileges.service.PrivilegeService;
import com.finalTest.BookShop.user.entity.User;
import com.finalTest.BookShop.user.entity.UserRepository;
import com.finalTest.BookShop.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
public class UserController {
    @Autowired
    BookService bookService;
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
    public String goHome(Model model){
        List<Book> bookList = bookService.getAllBooks();
        Random randomBook = new Random();
        List<Book> randomBooks = new ArrayList<>();
        if(bookList.size()<3){
            return "index";
        }
        int randomBookIndex0;
        int randomBookIndex1;
        int randomBookIndex2;
        do {
                randomBookIndex0 = randomBook.nextInt(bookList.size());
                randomBookIndex1 = randomBook.nextInt(bookList.size());
                randomBookIndex2 = randomBook.nextInt(bookList.size());
//            System.out.println(randomBookIndex0 + " " +randomBookIndex1 + " " + randomBookIndex2);
            }while (randomBookIndex0==randomBookIndex1 || randomBookIndex1==randomBookIndex2 || randomBookIndex0==randomBookIndex2);
        randomBooks.add(bookList.get(randomBookIndex0));
        randomBooks.add(bookList.get(randomBookIndex1));
        randomBooks.add(bookList.get(randomBookIndex2));
        model.addAttribute("randomBooks", randomBooks);
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
            List<User> userList = userService.getAllUsers();
            for (User user1 : userList) {
                if (user1.getUsername().equals(user.getUsername())) return "redirect:/register?error=Username already exists";
            }
            String plainPassword = user.getPassword();
            String hashedPassword = bCryptPasswordEncoder.encode(plainPassword);
            user.setPassword(hashedPassword);
            user.setPrivilege(privilegeService.getPrivilegeByName("USER"));
            userService.saveUser(user);
            return "redirect:/";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "redirect:/register?error=Bad registration";
        }
    }
    @GetMapping("/admin/addRemoveUser")
    public String addRemoveUser(Model model){
        List<User> users = userService.getAllUsers();
        model.addAttribute("user", users);
        List<Privilege> privileges = privilegeRepository.findAll();
        model.addAttribute("privilege", privileges);
        return "addRemoveUser";
    }
    @PostMapping("/admin/saveAdminUser")
    public String saveAdminUser(@ModelAttribute("user") User user){
        try {
            String plainPassword = user.getPassword();
            String hashedPassword = bCryptPasswordEncoder.encode(plainPassword);
            user.setPassword(hashedPassword);
            List<User> userList = userService.getAllUsers();
            for (User user1 : userList) {
                if (user1.getUsername().equals(user.getUsername())) return "redirect:/admin/addUser?error=Username already exists";
            }
            userService.saveUser(user);
            return "redirect:/admin/addRemoveUser";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "redirect:/admin/addRemoveUser?error=Bad registration";
        }
    }
    @GetMapping("/admin/addUser")
    public String addUser(Model model, Model privilegeModel){
        User user = new User();
        List<Privilege> privileges = privilegeRepository.findAll();
        privilegeModel.addAttribute("privilege", privileges);
        model.addAttribute("user", user);
        return "addUser";
    }

    @GetMapping("/admin/updateUser")
    public String updateUser(@ModelAttribute(value = "userRoleId") Long privilegeId,
                             @ModelAttribute(value = "name") String name,
                             @ModelAttribute(value = "surname") String surname,
                             @ModelAttribute(value = "username") String username,
                             @ModelAttribute(value = "email") String email,
                             @ModelAttribute(value = "userRole") String userRole,
                             @ModelAttribute(value = "userId") Long userId) {
        try {
            User user = userRepository.getReferenceById(userId);
            Privilege privilege = privilegeRepository.getReferenceById(privilegeId);
            System.out.println(userRole + " " + privilegeId);
            privilege.setId(privilegeId);
            user.setPrivilege(privilege);
            user.setName(name);
            user.setSurname(surname);
            user.setUsername(username);
            user.setEmail(email);
            userService.saveUser(user);
            return "redirect:/admin/addRemoveUser";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "redirect:/admin/addRemoveUser?error=Bad update";
        }
    }

    @GetMapping("/aboutUs")
    public String aboutUs(){
        return "aboutUs";
    }
}
