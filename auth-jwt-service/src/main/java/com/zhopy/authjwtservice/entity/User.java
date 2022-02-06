package com.zhopy.authjwtservice.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "usuarios")
public class User{
    
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
}
