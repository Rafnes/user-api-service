package ru.skillsrock.user_api_service.service;

import org.springframework.stereotype.Service;
import ru.skillsrock.user_api_service.repository.RoleRepository;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
}
