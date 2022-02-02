package com.zhopy.userservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "usuarios")
@SQLDelete(sql = "UPDATE usuarios SET eliminado=true WHERE id_usuario=?")
@Where(clause = "eliminado = false")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    
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
    private int roleCode;
    @JsonIgnore
    @Column(name = "eliminado")
    private boolean deleted;
}
