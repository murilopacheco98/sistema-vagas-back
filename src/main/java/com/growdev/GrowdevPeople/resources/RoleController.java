package com.growdev.GrowdevPeople.resources;

import com.growdev.GrowdevPeople.dto.RoleDTO;
import com.growdev.GrowdevPeople.services.user.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("/get")
    public ResponseEntity<Page<RoleDTO>> getAll(Pageable pageable){
        Page<RoleDTO> roleDTOPage = roleService.getAll(pageable);
        return ResponseEntity.ok().body(roleDTOPage);
    }

    @PostMapping()
    public ResponseEntity<RoleDTO> create(RoleDTO roleDTO){
        RoleDTO roleDTOr = roleService.create(roleDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(roleDTO.getUid()).toUri();
        return ResponseEntity.created(uri).body(roleDTOr);
    }
}
