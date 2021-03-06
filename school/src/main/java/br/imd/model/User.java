package br.imd.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "user")
@NaturalIdCache
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class User implements UserDetails, Serializable{

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	
	@NotNull
	@Size(min=5, max=45)
	@Column(name="nome", length=45)
	private String nome;
	
	@NotNull
	@Email
	@Column(name="email", length=45)
	private String email;
	
	@NaturalId
	@Column(name="login", length=14)
	private String login;
	
	@NotNull
	@Size(min=5)
	@Column(name="password")
	private String password;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Collection> usuarioTem = new ArrayList<Collection>(); 

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<WishList> usuarioQuer = new ArrayList<WishList>();
	
	@ManyToMany
	@JoinTable ( name = "users_roles", 
			joinColumns = @JoinColumn ( name = "user_login", referencedColumnName = "login", foreignKey = @ForeignKey( name = "user_login" )), 
	        inverseJoinColumns = @JoinColumn( name = "role_id", referencedColumnName = "id", foreignKey = @ForeignKey( name = "role_id" ))) 
    private List<Role> roles = new ArrayList<Role>();
	
	//Getters and setters omitted for brevity
	 
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(login, user.login);
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(login);
    }
    
    public User()
    {
    	Role role = new Role();
    	role.setId("ROLE_USER");
    	this.roles.add(role);
    }
    
    public User(String nome, String email, String login, String password) 
    {
		this.nome = nome;
		this.email = email;
		this.login = login;
		this.password = password;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Collection> getUsuarioTem() {
		return usuarioTem;
	}

	public void setUsuarioTem(List<Collection> usuarioTem) {
		this.usuarioTem = usuarioTem;
	}

	public List<WishList> getUsuarioQuer() {
		return usuarioQuer;
	}

	public void setUsuarioQuer(List<WishList> usuarioQuer) {
		this.usuarioQuer = usuarioQuer;
	}

	@Override
	public java.util.Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return (java.util.Collection<? extends GrantedAuthority>) this.roles;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.login;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
}
