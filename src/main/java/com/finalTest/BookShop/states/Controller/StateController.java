package com.finalTest.BookShop.states.Controller;

import com.finalTest.BookShop.states.entity.State;
import com.finalTest.BookShop.states.entity.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class StateController {
    @Autowired
    StateRepository stateRepository;

    @PostMapping("/admin/saveState")
    public String saveLanguage(@ModelAttribute(value = "state") State state){
        stateRepository.save(state);
        return "redirect:/admin/advanced";
    }
}
