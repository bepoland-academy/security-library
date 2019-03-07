package pl.betse.beontime.model;

import lombok.Data;

import java.util.List;

@Data
public class User {

    private String userId;

    private String email;

    private String firstName;

    private String lastName;

    private boolean active;

    private String department;

    private List<String> roles;

}
