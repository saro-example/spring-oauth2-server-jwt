package me.saro.example.oauth2.user;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 회원 테이블
 */
@Entity(name = "account")
@Table(name = "account")
@Data
@AllArgsConstructor
@NoArgsConstructor
@NamedEntityGraph(name = "roles", attributeNodes = @NamedAttributeNode("roles"))
@SequenceGenerator(name = "account_no_seq", sequenceName = "account_no_seq", allocationSize = 1)
public class Account implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_no_seq")
    @Column(name = "no", nullable = false)
    long no;

    @Column(name = "account", nullable = false, length = 64, unique = true)
    String account;

    @JsonIgnore
    @Column(name = "password", nullable = false, length = 512)
    String password;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumns({ @JoinColumn(name = "no", nullable = false, insertable = false) })
    List<AccountRole> roles;
}
