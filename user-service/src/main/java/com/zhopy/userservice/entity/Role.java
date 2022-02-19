package com.zhopy.userservice.entity;

import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "roles")
@SQLDelete(sql = "UPDATE roles SET eliminado=true WHERE codigo_rol=?")
@Where(clause = "eliminado = false")
public class Role implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_rol")
    private Long roleCode;
    @Column(name = "nombre_rol")
    private String roleName;
    @Column(name = "eliminado")
    private boolean deleted;

    @PrePersist
    public void prePersist() {
        this.deleted = false;
    }

}