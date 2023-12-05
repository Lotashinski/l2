package by.grsu.les2.controller;

import by.grsu.les2.service.UserService;
import by.grsu.les2.service.dao.SignUp;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@AllArgsConstructor
public class AuthController {
    private UserService userService;

    @ModelAttribute(name = "signUp")
    public SignUp signUp(){
        return new SignUp();
    }

    @GetMapping("/registration")
    public String registrationForm(
            @RequestParam(required = false, name = "password_mismatch") String passwordMismatch,
            @RequestParam(required = false, name = "email_exists") String emailExists,
            Model model
    ){
        if (null != passwordMismatch){
            model.addAttribute("password_mismatch", true);
        }

        if (null != emailExists){
            model.addAttribute("email_exists", true);
        }

        return "registration";
    }

    @PostMapping("/registration")
    public String registrationSubmit(SignUp signUp) {
        if (!signUp.isPasswordsMatch()){
            return "redirect:/registration?password_mismatch";
        }

        if (userService.isUsernameAlreadyExists(signUp.getEmail())){
            return "redirect:/registration?email_exists";
        }

        userService.registration(signUp);
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(){
        return "redirect:/";
    }
}
