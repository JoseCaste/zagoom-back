package com.core.service;

import com.core.dto.EmployeeDTO;
import com.core.dto.LoginDTO;
import com.core.dto.RecoveryPasswordDTO;
import com.core.dto.UserDTO;
import com.core.entity.EmailRecoveryToken;
import com.core.entity.Usuario;
import com.core.repository.EmailRecoveryTokenRepository;
import com.core.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalTime;
import java.util.List;

import java.util.Properties;
import java.util.Random;
import static java.time.temporal.ChronoUnit.MINUTES;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    protected UsuarioRepository usuarioRepository;
    @Autowired
    protected EmailRecoveryTokenRepository emailRecoveryTokenRepository;
    @Autowired
    protected ResourceLoader resourceLoader;
    private String username;
    private String password;
    Random random = new Random();

    @Override
    public List<Usuario> getEmployeesList() {
        return usuarioRepository.findAll();
    }

    @Override
    public String saveEmployee(EmployeeDTO employeeDTO) {
        return "Usuario creado";
    }

    @Override
    public Usuario doLogin(LoginDTO loginDTO) throws Exception {
        Usuario usuario = this.usuarioRepository.findByCorreoAndPassword(loginDTO.getCorreo(), loginDTO.getPassword()).orElseThrow(()-> new Exception("Credenciales no identificadas"));
        return usuario;
    }

    @Override
    public Usuario saveUser(UserDTO userDTO) throws Exception {
        List<Usuario> byCorreoList = this.usuarioRepository.findByCorreoList(userDTO.getCorreo());

        if(!byCorreoList.isEmpty()) {
            throw new Exception("El correo ya ha sido registrado");
        }

        Usuario usuario = Usuario.builder()
                .correo(userDTO.getCorreo())
                .nombre(userDTO.getNombre())
                .apellidoPaterno(userDTO.getApellidoPaterno())
                .apellidoMaterno(userDTO.getApellidoMaterno())
                .telefono(userDTO.getTelefono())
                .password(userDTO.getPassword())
                .build();
        this.usuarioRepository.save(usuario);
        return usuario;
    }

    @Override
    public Object recoveryPassowrd(RecoveryPasswordDTO recoveryPasswordDTO) {


            try {
                Properties properties = new Properties();
                Resource resource = resourceLoader.getResource("classpath:" + "recovery-password-token.html");
                Path path = resource.getFile().toPath();
                String fileContent = new String(Files.readAllBytes(path));

                properties.put("mail.smtp.host", "smtp.office365.com");
                properties.put("mail.smtp.port", "587");
                properties.put("mail.smtp.auth", "true");
                properties.put("mail.smtp.starttls.enable", "true");

                Session session = Session.getInstance(properties, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("jotaguzman08@gmail.com", "Fifajuan0208.");
                    }
                });


                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(username));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recoveryPasswordDTO.getCorreo()));
                message.setSubject("Token de seguridad | Reestrablecer contraseña");
                int recoveryToken = generateRandomToken();
                message.setContent(fileContent.replace("{{emailId}}", recoveryPasswordDTO.getCorreo()).replace("{{token}}", String.valueOf(recoveryToken)), "text/html; charset=utf-8");

                Usuario usuario = this.usuarioRepository.findByCorreo(recoveryPasswordDTO.getCorreo()).orElseThrow(()-> new Exception("No existe el correo"));
                EmailRecoveryToken emailRecoveryToken = EmailRecoveryToken.builder()
                        .token(recoveryToken)
                        .usuario(usuario)
                        .creationDate(Timestamp.from(Instant.now()))
                        .build();

                emailRecoveryTokenRepository.save(emailRecoveryToken);
                Transport.send(message);

                System.out.println("Correo enviado");

        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
                throw new RuntimeException(e);
            }
        return "Correo enviado";
    }

    @Override
    public Object validateToken(RecoveryPasswordDTO recoveryPasswordDTO) throws Exception {
        EmailRecoveryToken emailRecoveryToken = this.emailRecoveryTokenRepository.findByToken(recoveryPasswordDTO.getToken()).orElseThrow(()-> new Exception("El token no existe"));
        Usuario usuario = this.usuarioRepository.findByCorreo(recoveryPasswordDTO.getCorreo()).orElseThrow(()-> new Exception("No existe el correo"));

        long minutesBefore = MINUTES.between(emailRecoveryToken.getCreationDate().toLocalDateTime().toLocalTime(), LocalTime.now());

        if(minutesBefore >= 5) {
            throw new Exception("El token ha expirado");
        }
        return "Token válido";
    }

    private int generateRandomToken() {
        return random.nextInt(2000);
    }
}
