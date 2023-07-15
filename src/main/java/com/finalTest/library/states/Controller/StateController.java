package com.finalTest.library.states.Controller;

import com.finalTest.library.languages.entity.Language;
import com.finalTest.library.states.entity.State;
import com.finalTest.library.states.entity.StateRepository;
import com.finalTest.library.states.service.StateService;
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
