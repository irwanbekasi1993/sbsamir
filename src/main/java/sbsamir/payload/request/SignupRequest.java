package sbsamir.payload.request;

import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;

public class SignupRequest {
    private String username;
    private String email;
    private Set<String> role;
    private String password;
    public SignupRequest(String username, String email, Set<String> role) {
        this.username = username;
        this.email = email;
        this.role=role;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Set<String> getRole() {
        return role;
    }
    public void setRole(Set<String> role) {
        this.role = role;
    }
 
public String getPassword() {
    return password;
}
public void setPassword(String password) {
    this.password = password;
}   


    
}
