package com.goldenleaf.shop.dto;


import java.time.LocalDate;


public abstract class UserDTO {
    private Long id;
    private String login;
    private String name;
    private LocalDate lastActivity;


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public LocalDate getLastActivity() { return lastActivity; }
    public void setLastActivity(LocalDate lastActivity) { this.lastActivity = lastActivity; }
}
