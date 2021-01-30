package br.com.felipedantas.salesapi.salesapi.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({ "username", "password" })
public class AccountCredentialsDTO
{
    @NotEmpty
    @JsonProperty("username")
    private String username;
    @NotEmpty
    @JsonProperty("password")
    private String password;
}
