package com.zhopy.cityservice.entity;

import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "ciudad")
@SQLDelete(sql = "UPDATE ciudad SET eliminado=true WHERE codigo_ciudad=?")
@Where(clause = "eliminado = false")
public class City implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_ciudad")
    private Long cityCode;
    @Column(name = "nombre_ciudad")
    private String cityName;
    @Column(name = "eliminado")
    private boolean deleted;

    @PrePersist
    public void prePersist() {
        this.deleted = false;
    }

}