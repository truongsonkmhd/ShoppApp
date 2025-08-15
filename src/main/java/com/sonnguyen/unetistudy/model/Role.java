package com.example.ShoppApp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_role")
public class Role extends  AbstractEntity<Integer>{
    private String name;
    private String description;
    @OneToMany(mappedBy = "role")
    private Set<RoleHasPermission> roles = new HashSet<>();
}
