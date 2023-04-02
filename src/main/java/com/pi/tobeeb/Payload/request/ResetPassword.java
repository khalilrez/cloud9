package com.pi.tobeeb.Payload.request;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPassword {
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
