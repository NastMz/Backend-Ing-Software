package com.zhopy.statusservice.entity;

import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "estados")
@SQLDelete(sql = "UPDATE estados SET eliminado=true WHERE codigo_estado=?")
@Where(clause = "eliminado = false")
public class Status implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_estado")
    private Long statusCode;
    @Column(name = "nombre_estado")
    private String statusName;
    @Column(name = "eliminado")
    private boolean deleted;

    @PrePersist
    public void prePersist() {
        this.deleted = false;
    }

}