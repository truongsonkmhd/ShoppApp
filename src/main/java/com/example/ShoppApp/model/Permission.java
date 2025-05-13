package com.example.ShoppApp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_permission")
public class Permission  extends  AbstractEntity<Integer>{
    private String name;
    private String description;
}
