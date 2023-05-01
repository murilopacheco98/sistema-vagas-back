package com.growdev.GrowdevPeople.resources;

import com.growdev.GrowdevPeople.config.TokenResponse;
import com.growdev.GrowdevPeople.config.TokenService;
import com.growdev.GrowdevPeople.dto.user.CreateUserDTO;
import com.growdev.GrowdevPeople.dto.user.ResetPasswordDTO;
import com.growdev.GrowdevPeople.dto.user.UserDTO;
import com.growdev.GrowdevPeople.services.user.UserService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    TokenService tokenService;

    @GetMapping("/users/get")
    public ResponseEntity<Page<UserDTO>> getAll(Pageable pageable){
        Page<UserDTO> userDTOPage = userService.getAll(pageable);
        return ResponseEntity.ok().body(userDTOPage);
    }

    @GetMapping("/users/get/partners")
    public ResponseEntity<Page<UserDTO>> getAllPartners(Pageable pageable){
        Page<UserDTO> userDTOPage = userService.getAllPartners(pageable);
        return ResponseEntity.ok().body(userDTOPage);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> signIn(@RequestParam String email, @RequestParam String senha) {
        TokenResponse tokenResponse = tokenService.generateToken(email, senha);
        return ResponseEntity.ok().body(tokenResponse);
    }

    @GetMapping("/resend/confirm-email/{email}")
    public ResponseEntity<?> resendConfirmEmail(@PathVariable("email") String email) throws MessagingException {
        userService.resendConfirmEmail(email);
        return ResponseEntity.ok().body("E-mail confirmado com sucesso.");
    }

    @GetMapping("/email/reset-password/{email}")
    public ResponseEntity<?> reset(@PathVariable("email") String email) throws MessagingException {
        userService.sendEmailResetPassword(email);
        return ResponseEntity.ok().body("Email enviado neste endereço de email.");
    }

    @PostMapping("/users/post")
    public ResponseEntity<UserDTO> create(@Valid @RequestBody CreateUserDTO data){
        UserDTO user = userService.create(data);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/confirm-email/{checkerCode}")
    public ResponseEntity<?> confirmEmail(@RequestParam("checkerCode") String checkerCode) {
        userService.confirmEmail(checkerCode);
        return ResponseEntity.ok().body("E-mail confirmado com sucesso.");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordDTO resetPasswordDTO) throws NoSuchAlgorithmException {
        userService.resetPassword(resetPasswordDTO);
        return ResponseEntity.ok().body("Senha alterada com sucesso.");
    }
}
