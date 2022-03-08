package com.zhopy.supplierservice.entity;

import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "proveedores")
@SQLDelete(sql = "UPDATE proveedores SET eliminado=true WHERE nit_proveedor=?")
@Where(clause = "eliminado = false")
public class Supplier implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nit_proveedor")
    private String supplierNit;
    @Column(name = "nombre_proveedor")
    private String supplierName;
    @Column(name = "direccion_proveedor")
    private String supplierAddress;
    @Column(name = "telefono")
    private String phone;
    @Column(name = "codigo_ciudad")
    private Long cityCode;
    @Column(name = "eliminado")
    private boolean deleted;

    @PrePersist
    public void prePersist() {
        this.deleted = false;
    }

}