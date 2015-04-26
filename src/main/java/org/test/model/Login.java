package org.test.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.test.utils.ApplicationUtils;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.security.NoSuchAlgorithmException;

import static org.test.utils.ApplicationStrings.*;

@Entity
@Table(name = LOGIN)
@NamedQueries({
        // NATURAL KEY
        @NamedQuery(name = LOGIN_FIND_FILTER_BY_EMAIL,
                query = "SELECT l FROM Login l " +
                        "WHERE l.email = :email"),
        // FIND
        @NamedQuery(name = LOGIN_FIND_BY_EMAIL_AND_PASSWORD,
                query = "SELECT l FROM Login l " +
                        "WHERE l.email = :email " +
                        "AND l.password = :password"),
        @NamedQuery(name = LOGIN_FIND_ALL_ORDER_BY_EMAIL_ASC,
                query = "SELECT l FROM Login l " +
                        "ORDER BY l.email ASC"),
})
public class Login extends Domain {

    private static Log logger = LogFactory.getLog(Login.class);

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = ID, columnDefinition = "BIGINT(10) UNSIGNED", unique = true, nullable = false)
	private Long id;
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @NotNull
    @Size(min=1, max=255)
	@Column(name = EMAIL, columnDefinition = "VARCHAR(255)", unique = true, nullable = false)
	private String email;
    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = (email == null)? null : email.toUpperCase();
    }

    @NotNull
    @Size(min=1, max=128)
    @Column(name = PASSWORD, columnDefinition = "VARCHAR(128)", nullable = false)
    private String password;
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) throws NoSuchAlgorithmException {
        this.password = ApplicationUtils.getPasswordToStore(password);
    }

    @NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = ROLE, columnDefinition = "ENUM('ADMIN','USER') DEFAULT 'USER'", nullable = false)
	private Role role;
    public Role getRole() {
        return this.role;
    }
    public void setRole(Role role) {
        this.role = role;
    }

    @NotNull
    @Valid
    @Embedded
    private Record record = new Record();
    public Record getRecord() {
        return record;
    }
    public void setRecord(Record record) {
        this.record = record;
    }

	public Login() {}

	public Login(String email, String password) {
        logger.info(CREATING + Login.class);
		this.email = (email == null)? null : email.toUpperCase();
		this.password = password;
		this.role = Role.USER;
	}

	public Login(String email, String password, Role role) throws NoSuchAlgorithmException {
        logger.info(CREATING + Login.class);
		this.email = (email == null)? null : email.toUpperCase();
		this.password = ApplicationUtils.getPasswordToStore(password);
		this.role = role;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Login login = (Login) o;

        if (email != null ? !email.equals(login.email) : login.email != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return email != null ? email.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Login{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", record=" + record +
                '}';
    }
}
