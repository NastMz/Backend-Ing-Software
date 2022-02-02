package com.zhopy.userservice.dto;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter // getters setters toString etc
public class UserDTO{

    private String idUsuario;
    private String nombreUsuario;
    private String correo;
    private String direccion;
    private Long codigoRol;

}
