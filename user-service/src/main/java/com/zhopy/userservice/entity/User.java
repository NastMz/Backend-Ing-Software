package com.zhopy.userservice.entity;

import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "usuarios")
@SQLDelete(sql = "UPDATE usuarios SET eliminado=true WHERE id_usuario=?")
@Where(clause = "eliminado = false")
public class User implements Serializable {

    @Id
    @Column(name = "id_usuario")
    private String userId;
    @Column(name = "nombre_usuario")
    private String userName;
    @Column(name = "correo")
    private String email;
    @Column(name = "direccion")
    private String address;
    @Column(name = "password")
    private String password;
    @Column(name = "telefono")
    private String phone;
    @Column(name = "codigo_rol")
    private Long roleCode;
    @Column(name = "eliminado")
    private boolean deleted;

    @PrePersist
    public void prePersist() {
        this.deleted = false;
    }
}
