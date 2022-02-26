package com.zhopy.buyservice.entity;

import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.time.ZonedDateTime;

@Data
@Entity
@Table(name = "compras")
@SQLDelete(sql = "UPDATE compras SET eliminado=true WHERE numero_compra=?")
@Where(clause = "eliminado = false")
public class Buy implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "numero_compra")
    private Long buyNumber;
    @Column(name = "total")
    private Long total;
    @Column(name = "fecha_compra")
    private Date buyDate;
    @Column(name = "id_usuario")
    private String userId;
    @Column(name = "eliminado")
    private boolean deleted;

    @PrePersist
    public void prePersist() {
        this.deleted = false;
        java.util.Date utilDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        this.buyDate = sqlDate;
    }

}