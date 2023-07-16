package com.finalTest.BookShop.privileges.service;

import com.finalTest.BookShop.privileges.entity.Privilege;
import com.finalTest.BookShop.privileges.entity.PrivilegeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrivilegeService {
    @Autowired
    PrivilegeRepository privilegeRepository;

    public List<Privilege> getAll(){
        return privilegeRepository.findAll();
    }

    public Privilege getPrivilegeByName(String name){
        return privilegeRepository.findByName(name);
    }
}
