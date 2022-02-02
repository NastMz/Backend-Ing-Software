package com.zhopy.roleservice.entity;

import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@SQLDelete(sql = "UPDATE roles SET eliminado=true WHERE codigo_rol=?")
@Where(clause = "eliminado = false")
@NoArgsConstructor
@RequiredArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_rol")
    private Long roleCode;
    @NonNull
    @Column(name = "nombre_rol")
    private String roleName;
    @NonNull
    @Column(name = "eliminado")
    private boolean deleted;
}