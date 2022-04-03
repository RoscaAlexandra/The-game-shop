package com.proiecte.GamesStore.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Data
@Setter
@Getter
public class GameInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    private  String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @Lob
    private Byte[] image;

}
