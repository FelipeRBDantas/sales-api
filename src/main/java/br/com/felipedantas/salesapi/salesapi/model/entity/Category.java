package br.com.felipedantas.salesapi.salesapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table( name = "category" )
@Entity( name = "category" )
public class Category {
    @Id
    @Column
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    @Column( length = 45 )
    private String name;

    @Column( length = 100 )
    private String description;
}