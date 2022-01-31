package com.zhopy.userservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@SQLDelete(sql = "UPDATE usuarios SET eliminado=true WHERE id_usuario=?")// Para implementar el soft delete
@Where(clause = "eliminado = false")
@Data // getters setters toString etc
@NoArgsConstructor // Genera el constructor vacio
@AllArgsConstructor
public class User {
    
    @Id
    private String idUsuario;
    
    private String nombreUsuario;
    private String correo;
    private String direccion;
    private String password;
    private String telefono;
    private int codigoRol;
    private boolean eliminado;
}
