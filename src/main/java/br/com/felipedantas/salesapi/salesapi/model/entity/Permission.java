package br.com.felipedantas.salesapi.salesapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity( name = "permission" )
@Table( name = "permission" )
public class Permission implements GrantedAuthority {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "id" )
    private Long id;

    @Column( name = "description" )
    private String description;

    @Override
    public String getAuthority() {
        return this.description;
    }
}
