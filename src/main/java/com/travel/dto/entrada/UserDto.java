package com.travel.dto.entrada;

public class UserDto {
    private Long id;
    private String email;

    public UserDto() {}
    
    public UserDto(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

}
