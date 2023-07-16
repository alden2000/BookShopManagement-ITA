package com.finalTest.BookShop.states.service;

import com.finalTest.BookShop.states.entity.State;
import com.finalTest.BookShop.states.entity.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StateService {
    @Autowired
    StateRepository stateRepository;

    public List<State> getAllStates(){
        return stateRepository.findAll();
    }
}
