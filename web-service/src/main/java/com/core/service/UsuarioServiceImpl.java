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
                String fileContent = "<!DOCTYPE html>\n" +
                        "<html lang=\"es\">\n" +
                        "<head>\n" +
                        "    <meta charset=\"UTF-8\">\n" +
                        "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                        "    <title>Email Template</title>\n" +
                        "    <style>\n" +
                        "        body {\n" +
                        "            font-family: Arial, sans-serif;\n" +
                        "            background-color: #fce4ec;\n" +
                        "            color: #333;\n" +
                        "            margin: 0;\n" +
                        "            padding: 0;\n" +
                        "        }\n" +
                        "        .email-container {\n" +
                        "            max-width: 600px;\n" +
                        "            margin: 0 auto;\n" +
                        "            background-color: #ffffff;\n" +
                        "            border-radius: 10px;\n" +
                        "            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\n" +
                        "            overflow: hidden;\n" +
                        "        }\n" +
                        "        .email-header {\n" +
                        "            background-color: #f8bbd0;\n" +
                        "            color: #ffffff;\n" +
                        "            padding: 20px;\n" +
                        "            text-align: center;\n" +
                        "        }\n" +
                        "        .email-body {\n" +
                        "            padding: 20px;\n" +
                        "            color: #333333;\n" +
                        "        }\n" +
                        "        .email-footer {\n" +
                        "            background-color: #f8bbd0;\n" +
                        "            color: #ffffff;\n" +
                        "            padding: 10px;\n" +
                        "            text-align: center;\n" +
                        "        }\n" +
                        "        .token {\n" +
                        "            background-color: #fce4ec;\n" +
                        "            color: #d81b60;\n" +
                        "            padding: 10px;\n" +
                        "            border-radius: 5px;\n" +
                        "            display: inline-block;\n" +
                        "            margin: 10px 0;\n" +
                        "            font-size: 18px;\n" +
                        "            font-weight: bold;\n" +
                        "        }\n" +
                        "        .message {\n" +
                        "            font-size: 16px;\n" +
                        "        }\n" +
                        "    </style>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "<div class=\"email-container\">\n" +
                        "    <div class=\"email-header\">\n" +
                        "        <h1>¡Hola, {{emailId}}!</h1>\n" +
                        "    </div>\n" +
                        "    <div class=\"email-body\">\n" +
                        "        <p class=\"message\">Esperamos que estés teniendo un día maravilloso. Para continuar con el proceso, por favor utiliza el siguiente token de seguridad:</p>\n" +
                        "        <div class=\"token\">{{token}}</div>\n" +
                        "        <p class=\"message\">Si tienes alguna pregunta, no dudes en contactarnos. ¡Que tengas un excelente día!</p>\n" +
                        "    </div>\n" +
                        "    <div class=\"email-footer\">\n" +
                        "        <p>&copy; 2024 Tu Empresa. Todos los derechos reservados.</p>\n" +
                        "    </div>\n" +
                        "</div>\n" +
                        "</body>\n" +
                        "</html>";

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
                message.setFrom(new InternetAddress("jotaguzman08@gmail.com"));
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
