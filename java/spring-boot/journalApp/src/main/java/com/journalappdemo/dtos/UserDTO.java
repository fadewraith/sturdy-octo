package com.journalappdemo.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @NotEmpty
    @Schema(description = "username")
    private String userName;
    private String email;
    private boolean sentimentAnalysis;
    @NotEmpty
    private String password;

}
