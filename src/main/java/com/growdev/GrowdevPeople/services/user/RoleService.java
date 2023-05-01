package com.growdev.GrowdevPeople.services.user;

import com.growdev.GrowdevPeople.dto.RoleDTO;
import com.growdev.GrowdevPeople.entities.Role;
import com.growdev.GrowdevPeople.repositories.user.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Transactional(readOnly = true)
    public Page<RoleDTO> getAll(Pageable pageable) {
        Page<Role> roleList = roleRepository.findAll(pageable);
        return roleList.map(RoleDTO::new);
    }

    public RoleDTO create(RoleDTO roleDTO) {
        Role role = new Role();
        role.setName(roleDTO.getName());
        role.setDescription(roleDTO.getDescription());
//        role.setType(roleDTO.getType());
        role.setEnable(roleDTO.getEnable());

        role = roleRepository.save(role);
        return new RoleDTO(role);
    }

    public List<RoleDTO> teste(List<String> search) {
        System.out.println(search);
        search = search.stream().map(String::toUpperCase).collect(Collectors.toList());
        List<Role> teste = roleRepository.teste2(search);
        return teste.stream().map(RoleDTO::new).collect(Collectors.toList());
    }
}
