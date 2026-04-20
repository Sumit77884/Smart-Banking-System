package com.banking.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String fullName;
    private String phone;
    private String address;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Account> accounts;

    @PrePersist
    protected void onCreate() { createdAt = LocalDateTime.now(); }

    public enum Role { USER, ADMIN }

    public User() {}

    public User(Long id, String username, String email, String password,
                String fullName, String phone, String address, Role role,
                LocalDateTime createdAt, List<Account> accounts) {
        this.id = id; this.username = username; this.email = email;
        this.password = password; this.fullName = fullName; this.phone = phone;
        this.address = address; this.role = role; this.createdAt = createdAt;
        this.accounts = accounts;
    }

    public Long getId()                 { return id; }
    public String getUsername()         { return username; }
    public String getEmail()            { return email; }
    public String getPassword()         { return password; }
    public String getFullName()         { return fullName; }
    public String getPhone()            { return phone; }
    public String getAddress()          { return address; }
    public Role getRole()               { return role; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public List<Account> getAccounts()  { return accounts; }

    public void setId(Long id)                        { this.id = id; }
    public void setUsername(String v)                 { this.username = v; }
    public void setEmail(String v)                    { this.email = v; }
    public void setPassword(String v)                 { this.password = v; }
    public void setFullName(String v)                 { this.fullName = v; }
    public void setPhone(String v)                    { this.phone = v; }
    public void setAddress(String v)                  { this.address = v; }
    public void setRole(Role v)                       { this.role = v; }
    public void setCreatedAt(LocalDateTime v)         { this.createdAt = v; }
    public void setAccounts(List<Account> v)          { this.accounts = v; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private String username, email, password, fullName, phone, address;
        private Role role = Role.USER;
        private LocalDateTime createdAt;
        private List<Account> accounts;

        public Builder id(Long v)               { this.id = v; return this; }
        public Builder username(String v)       { this.username = v; return this; }
        public Builder email(String v)          { this.email = v; return this; }
        public Builder password(String v)       { this.password = v; return this; }
        public Builder fullName(String v)       { this.fullName = v; return this; }
        public Builder phone(String v)          { this.phone = v; return this; }
        public Builder address(String v)        { this.address = v; return this; }
        public Builder role(Role v)             { this.role = v; return this; }
        public Builder createdAt(LocalDateTime v){ this.createdAt = v; return this; }
        public Builder accounts(List<Account> v){ this.accounts = v; return this; }

        public User build() {
            return new User(id, username, email, password, fullName,
                            phone, address, role, createdAt, accounts);
        }
    }
}
