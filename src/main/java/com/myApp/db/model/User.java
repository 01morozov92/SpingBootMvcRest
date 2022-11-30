package com.myApp.db.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "users")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JsonProperty(required = true)
    @NotNull(message="Please provide a valid name")
    @Column(name = "name")
    String name;

    @JsonProperty(required = true)
    @NotNull(message="Please provide a valid age")
    @Column(name = "age")
    Integer age;
}