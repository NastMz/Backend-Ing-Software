package com.zhopy.shoesservice.entity;

import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "categoria")
@SQLDelete(sql = "UPDATE categoria SET eliminado=true WHERE codigo_categoria=?")
@Where(clause = "eliminado = false")
public class Category implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_categoria")
    private Long categoryCode;
    @Column(name = "nombre_categoria")
    private String categoryName;
    @Column(name = "eliminado")
    private boolean deleted;

    @PrePersist
    public void prePersist() {
        this.deleted = false;
    }

}