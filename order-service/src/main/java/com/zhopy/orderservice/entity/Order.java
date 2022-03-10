package com.zhopy.orderservice.entity;

import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Data
@Entity
@Table(name = "pedidos")
@SQLDelete(sql = "UPDATE pedidos SET eliminado=true WHERE numero_pedido=?")
@Where(clause = "eliminado = false")
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "numero_pedido")
    private Long orderNumber;
    @Column(name = "fecha_pedido")
    private Date orderDate;
    @Column(name = "id_usuario")
    private String userId;
    @Column(name = "codigo_estado")
    private Long statusCode;
    @Column(name = "numero_compra")
    private Long buyNumber;
    @Column(name = "eliminado")
    private boolean deleted;

    @PrePersist
    public void prePersist() {
        this.deleted = false;
        java.util.Date utilDate = new java.util.Date();
        Date sqlDate = new Date(utilDate.getTime());
        this.orderDate = sqlDate;
    }

}