package org.example.cgi.model;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Table(name="Films")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String genre;
    private int ageRestriction;
    private String language;
    private String startTime;


}
