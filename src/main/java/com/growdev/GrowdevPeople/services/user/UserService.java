package com.growdev.GrowdevPeople.services.user;

import com.growdev.GrowdevPeople.dto.user.CreateUserDTO;
import com.growdev.GrowdevPeople.dto.user.ResetPasswordDTO;
import com.growdev.GrowdevPeople.dto.user.UserDTO;
import com.growdev.GrowdevPeople.entities.Company;
import com.growdev.GrowdevPeople.entities.DataProfile;
import com.growdev.GrowdevPeople.entities.Role;
import com.growdev.GrowdevPeople.entities.User;
import com.growdev.GrowdevPeople.exceptions.exception.BadRequestException;
import com.growdev.GrowdevPeople.exceptions.exception.ResourceNotFoundException;
import com.growdev.GrowdevPeople.repositories.CompanyRepository;
import com.growdev.GrowdevPeople.repositories.user.DataProfileRepository;
import com.growdev.GrowdevPeople.repositories.user.RoleRepository;
import com.growdev.GrowdevPeople.repositories.user.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final DataProfileRepository dataProfileRepository;
    private final CompanyRepository companyRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    @Value("${spring.mail.password}")
    private String passwordEmail;

    @Transactional(readOnly = true)
    public Page<UserDTO> getAll(Pageable pageable) {
        Page<User> pageUser = userRepository.findAll(pageable);
        return pageUser.map(UserDTO::new);
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> getAllPartners(Pageable pageable) {
        List<User> listUser = userRepository.findAll();
        List<User> listUserParceiro = new ArrayList<>();
        for (User user : listUser) {
            if (Objects.equals(user.getRole().getName(), "PARCEIRO")) {
                listUserParceiro.add(user);
            }
        }
        Page<User> userPage = new PageImpl<>(listUserParceiro, pageable, listUserParceiro.size());
        return userPage.map(UserDTO::new);
    }

    public UserDTO create(CreateUserDTO createUserDTO) {
        User userExist = userRepository.findByLogin(createUserDTO.getEmail());
        if (userExist != null) throw new BadRequestException("Este email já existe.");

        DataProfile dataProfileExist = dataProfileRepository.findByEmail(createUserDTO.getEmail());
        if (dataProfileExist != null) throw new BadRequestException("Este perfil já está cadastrado.");

        Role role = roleRepository.findByName(createUserDTO.getRoleName())
                .orElseThrow(() -> new ResourceNotFoundException("Está função não existe."));

        User user = new User();
        user.setLogin(createUserDTO.getEmail());
        user.setPassword(passwordEncoder.encode(createUserDTO.getPassword()));
        user.setEnable(true);
        user.setRole(role);

        DataProfile dataProfile = new DataProfile();
        dataProfile.setName(createUserDTO.getName());
        dataProfile.setEmail(createUserDTO.getEmail());

        String phoneNumber = createUserDTO.getPhoneNumber().replaceAll("\\D", "");
        dataProfile.setPhoneNumber(phoneNumber);

        if (Objects.equals(role.getName(), "PARCEIRO")) {
            if (createUserDTO.getCompanyUid() == null) {
                throw new BadRequestException("Obrigatória informar uma empresa para este perfil");
            }

            Company company = companyRepository.findById(createUserDTO.getCompanyUid())
                    .orElseThrow(() -> new ResourceNotFoundException("Esta companhia não existe."));
            dataProfile.setCompany(company);
        }

        try {
            dataProfile = dataProfileRepository.save(dataProfile);
            user.setDataProfile(dataProfile);
            user = userRepository.save(user);
        } catch (Exception e) {
            throw new BadRequestException("Não foi possível criar o usuário.");
        }

        return new UserDTO(user);
    }

    public void confirmEmail(String checkerCode) {
        User user = userRepository.findByCheckerCode(checkerCode)
                .orElseThrow(() -> new ResourceNotFoundException("usuário não encontrado"));
        user.setEnable(true);
        userRepository.save(user);
    }

    public void resendConfirmEmail(String email) throws MessagingException {
        User user = userRepository.findByLogin(email);
        if (user == null) throw new BadRequestException("Email não encontrado.");

        JavaMailSenderImpl mailSender = this.getJavaMailSender();
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setSubject("Comfirmação de e-mail.");
        helper.setFrom("murilo.bot100@gmail.com");
        helper.setTo(email);

        boolean html = true;
        helper.setText("<p>Olá,</p>"
                        + "<p>Você cadastrou em nosso site.</p>"
                        + "<br>"
                        + "<p>Para confirmar seu e-mail clique no link: <a href=\"http://localhost:3000/confirm-account/" + user.getCheckerCode() + "\">Confirmar e-mail</a></p>"
                        + "<br>"
                        + "<p>Ignore this email if you do remember your password, or you have not made the request.</p>"
                , html);

        mailSender.send(message);
    }

    public void resetPassword(ResetPasswordDTO resetPasswordDTO) throws NoSuchAlgorithmException {
        User user = userRepository.findByCheckerCode(resetPasswordDTO.getResetPasswordToken())
                .orElseThrow(() -> new ResourceNotFoundException("Token inválido."));

        user.setCheckerCode(null);
        user.setPassword(passwordEncoder.encode(resetPasswordDTO.getNewPassword()));
//        user.setPassword(hashPassword(resetPasswordDTO.getNewPassword()));

        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new DataIntegrityViolationException("Não foi possível salvar este usuário");
        }
    }

    public void sendEmailResetPassword(String to) throws MessagingException {
        User user = userRepository.findByLogin(to);
        if (user == null) throw new BadRequestException("Usuário não encontrado.");

        user.setCheckerCode(String.valueOf(UUID.randomUUID()));
        userRepository.save(user);
        JavaMailSenderImpl mailSender = this.getJavaMailSender();

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setSubject("Link da sua senha.");
        helper.setFrom("murilo.bot100@gmail.com");
        helper.setTo(to);

        boolean html = true;
        helper.setText("<p>Olá,</p>"
                        + "<p>Você solicitou acesso a sua senha.</p>"
                        + "<br>"
                        + "<p>Clique no link para alterar sua senha: <a href=\"http://localhost:3000/reset-password/" + user.getCheckerCode() + "\">Trocar senha</a></p>"
                        + "<br>"
                        + "<p>Ignore this email if you do remember your password, or you have not made the request.</p>"
                , html);

        mailSender.send(message);
    }

    public JavaMailSenderImpl getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("murilo.bot100@gmail.com");
        mailSender.setPassword(passwordEmail);

        Properties props = mailSender.getJavaMailProperties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.smtp.starttls.enable", "true");
//        props.setProperty("mail.debug", "true");
//        props.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
        props.setProperty("mail.smtp.ssl.trust", "smtp.gmail.com");
        mailSender.setJavaMailProperties(props);

        return mailSender;
    }
}
