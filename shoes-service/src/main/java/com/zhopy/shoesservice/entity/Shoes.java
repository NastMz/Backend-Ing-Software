package com.zhopy.shoesservice.entity;

import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "zapatos")
@SQLDelete(sql = "UPDATE zapatos SET eliminado=true WHERE codigo_zapato=?")
@Where(clause = "eliminado = false")
public class Shoes implements Serializable {
    @Id
    @Column(name = "codigo_zapato")
    private String shoeCode;
    @Column(name = "nombre_zapato")
    private String shoeName;
    @Column(name = "precio")
    private String price;
    @Column(name = "stock")
    private String stock;
    @Column(name = "descripcion")
    private String description;
    @Column(name = "imagen_nombre")
    private String imageName;
    @Column(name = "imagen_bytes")
    private byte[] imageBytes;
    @Column(name = "codigo_categoria")
    private Long categoryCode;
    @Column(name = "nit_proveedor")
    private String supplierNit;
    @Column(name = "eliminado")
    private boolean deleted;

    @PrePersist
    public void prePersist() {
        this.deleted = false;
    }

}