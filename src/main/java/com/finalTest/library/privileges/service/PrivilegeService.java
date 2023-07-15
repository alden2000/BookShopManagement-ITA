package com.finalTest.library.privileges.service;

import com.finalTest.library.privileges.entity.Privilege;
import com.finalTest.library.privileges.entity.PrivilegeRepository;
import com.finalTest.library.user.entity.User;
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
