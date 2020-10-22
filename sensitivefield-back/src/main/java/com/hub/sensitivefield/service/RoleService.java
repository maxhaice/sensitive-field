package com.hub.sensitivefield.service;

import com.hub.sensitivefield.model.Role;
import com.hub.sensitivefield.model.RoleName;
import com.hub.sensitivefield.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    public void save(Role role) {
        roleRepository.save(role);
    }

    public Optional<Role> findByName(RoleName roleName) {
        return roleRepository.findAll()
                .stream()
                .filter(role -> role.getName() == roleName)
                .findAny();
    }
}
