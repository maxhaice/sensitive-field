package com.hub.sensitivefield.service;

import com.hub.sensitivefield.model.Role;
import com.hub.sensitivefield.model.RoleName;
import com.hub.sensitivefield.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Optional<Role> findByName(RoleName roleName) {
        return roleRepository.findAll()
                .stream()
                .filter(role -> role.getName() == roleName)
                .findAny();
    }
}