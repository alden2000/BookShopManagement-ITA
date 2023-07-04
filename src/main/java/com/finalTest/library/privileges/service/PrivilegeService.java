package com.finalTest.library.privileges.service;

import com.finalTest.library.privileges.entity.Privilege;
import com.finalTest.library.privileges.entity.PrivilegeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrivilegeService {
    @Autowired
    PrivilegeRepository privilegeRepository;

    public Privilege getPrivilegeByName(){
        return privilegeRepository.findByName("USER");
    }
}
