package by.grsu.les2.service.dao;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SignUp {
    private String email;

    private String password;

    private String passwordRepeat;

    public boolean isPasswordsMatch(){
        return getPassword().equals(getPasswordRepeat());
    }
}
