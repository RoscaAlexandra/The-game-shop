package com.proiecte.GamesStore.domain;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class SignUpForm {

    @Size(min=5,max=30)
    private String username="";


    @Size(min=7, max=30)
    private String password = "";


    @Size(min=7, max=30)
    private String passwordCheck = "";

    private String role = "USER";

    private String address;
    @NotNull
    @Size(min=6, max=30)
    private String email;

    @NotNull
    @Size(min=2, max=20)
    private String firstName;
    @NotNull
    @Size(min=2, max=20)
    private String lastName;


}