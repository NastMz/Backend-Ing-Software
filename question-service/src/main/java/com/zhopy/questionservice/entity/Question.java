package com.zhopy.questionservice.entity;

import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "preguntas")
@SQLDelete(sql = "UPDATE preguntas SET eliminado=true WHERE codigo_pregunta=?")
@Where(clause = "eliminado = false")
public class Question implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_pregunta")
    private Long questionCode;
    @Column(name = "pregunta")
    private String question;
    @Column(name = "eliminado")
    private boolean deleted;

    @PrePersist
    public void prePersist() {
        this.deleted = false;
    }

}