package com.finalTest.library.user.Controller;

import com.finalTest.library.books.entity.Book;
import com.finalTest.library.books.service.BookService;
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
    public String getHome(Model model){
        List<Book> bookList = bookService.getAllBooks();
        Random randomBook = new Random();
        List<Book> randomBooks = new ArrayList<>();
        int randomBookIndex0;
        int randomBookIndex1;
        int randomBookIndex2;
        do {
                randomBookIndex0 = randomBook.nextInt(bookList.size() - 1);
                randomBookIndex1 = randomBook.nextInt(bookList.size() - 1);
                randomBookIndex2 = randomBook.nextInt(bookList.size() - 1);
            System.out.println(randomBookIndex0 + " " +randomBookIndex1 + " " + randomBookIndex2);
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
            String plainPassword = user.getPassword();
            String hashedPassword = bCryptPasswordEncoder.encode(plainPassword);
            user.setPassword(hashedPassword);
            user.setPrivilege(privilegeService.getPrivilegeByName());
            userService.saveUser(user);
            return "redirect:/";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "redirect:/register?error=Bad registration";
        }
    }
    @GetMapping("/addRemoveUser")
    public String addRemoveUser(Model model){
        List<User> users = userService.getAllUsers();
        model.addAttribute("user", users);
        return "addRemoveUser";
    }
    @PostMapping("/saveAdminUser")
    public String saveAdminUser(@ModelAttribute("user") User user){
        try {
            String plainPassword = user.getPassword();
            String hashedPassword = bCryptPasswordEncoder.encode(plainPassword);
            user.setPassword(hashedPassword);
            user.setPrivilege(privilegeService.getPrivilegeByName());
            userService.saveUser(user);
            return "redirect:/addRemoveUser";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "redirect:/addRemoveUser?error=Bad registration";
        }
    }
    @GetMapping("/addUser")
    public String addUser(Model model, Model privilegeModel){
        User user = new User();
        List<Privilege> privileges = privilegeRepository.findAll();
        privilegeModel.addAttribute("privilege", privileges);
        model.addAttribute("user", user);
        return "addUser";
    }

    @GetMapping("/updateUser")
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
            String[] arrayOfString = userRole.split("i");
            System.out.println(userRole);
            privilege.setName(arrayOfString[1]);
            privilege.setId((long) Integer.parseInt(arrayOfString[0]));
            user.setPrivilege(privilege);
            user.setName(name);
            user.setSurname(surname);
            user.setUsername(username);
            user.setEmail(email);
            userService.saveUser(user);
            return "redirect:/addRemoveUser";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "redirect:/addRemoveUser?error=Bad update";
        }
    }
}
