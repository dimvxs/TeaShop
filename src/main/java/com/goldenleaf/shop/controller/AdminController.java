package com.goldenleaf.shop.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.goldenleaf.shop.dto.AdminDTO;
import com.goldenleaf.shop.exception.EmptyLastActivityException;
import com.goldenleaf.shop.exception.EmptyLoginException;
import com.goldenleaf.shop.model.Admin;
import com.goldenleaf.shop.service.AdminService;


import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admins")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')") 
public class AdminController {

    private final AdminService adminService; 
    
    public AdminController(AdminService adminService)
    {
    			this.adminService = adminService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Admin> get(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Admin> create(@RequestBody AdminDTO dto) {
        return ResponseEntity.ok(adminService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Admin> update(@PathVariable Long id, @RequestBody AdminDTO dto) throws EmptyLastActivityException, EmptyLoginException {
        return ResponseEntity.ok(adminService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        adminService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}