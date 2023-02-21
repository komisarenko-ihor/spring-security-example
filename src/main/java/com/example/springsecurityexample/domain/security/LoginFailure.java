package com.example.springsecurityexample.domain.security;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class LoginFailure {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String username;

    @ManyToOne
    private User user;

    private String sourceIp;

    @CreationTimestamp
    private Timestamp createdDate;

    @UpdateTimestamp
    @Column(updatable = false)
    private Timestamp lastModifiedDate;
}
