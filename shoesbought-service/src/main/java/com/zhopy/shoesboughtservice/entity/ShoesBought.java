package com.zhopy.shoesboughtservice.entity;

import com.zhopy.shoesboughtservice.compositekey.CompositeKey;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "zapatos_comprados")
@IdClass(CompositeKey.class)
public class ShoesBought implements Serializable {
    @Id
    @Column(name = "codigo_zapato")
    private String shoeCode;
    @Id
    @Column(name = "numero_compra")
    private Long buyNumber;

}