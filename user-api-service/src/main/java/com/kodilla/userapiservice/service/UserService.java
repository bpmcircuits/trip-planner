package com.kodilla.userapiservice.service;

import com.kodilla.userapiservice.domain.AccountType;
import com.kodilla.userapiservice.domain.User;
import com.kodilla.userapiservice.domain.UserStatus;
import com.kodilla.userapiservice.exception.EmailAlreadyExistsException;
import com.kodilla.userapiservice.exception.EmailSendingException;
import com.kodilla.userapiservice.exception.InvalidVerificationCodeException;
import com.kodilla.userapiservice.exception.LoginAlreadyExistsException;
import com.kodilla.userapiservice.exception.UserNotFoundException;
import com.kodilla.userapiservice.mail.domain.Mail;
import com.kodilla.userapiservice.mail.service.EmailService;
import com.kodilla.userapiservice.repository.UserRepository;
import com.kodilla.userapiservice.security.VerificationCodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final static Integer VERIFICATION_CODE_EXPIRATION_TIME = 10; // in minutes

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final EmailService emailService;

    public List<User> getAllUsers() {
        return userRepository.findAll().stream().toList();
    }

    public User getUserById(Long id) throws UserNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    public User registerUser(User user)
            throws EmailAlreadyExistsException, LoginAlreadyExistsException, EmailSendingException {
        User existingUserByEmail = userRepository.findByEmail(user.getEmail());
        if (existingUserByEmail != null) {
            throw new EmailAlreadyExistsException();
        }
        
        User existingUserByLogin = userRepository.findByLogin(user.getLogin());
        if (existingUserByLogin != null) {
            throw new LoginAlreadyExistsException();
        }

        String verificationCode = VerificationCodeGenerator.generateCode();

        String encodedPassword = bCryptPasswordEncoder.encode(user.getPasswordHash());
        User createdUser = User.builder()
                .login(user.getLogin())
                .accountType(AccountType.USER)
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .createdAt(LocalDateTime.now())
                .passwordHash(encodedPassword)
                .token(UUID.randomUUID().toString())
                .tokenCreatedAt(LocalDateTime.now())
                .tokenExpiresAt(LocalDateTime.now().plusDays(1))
                .verificationCode(verificationCode)
                .userStatus(UserStatus.NOT_VERIFIED)
                .build();

        Mail mail = Mail.builder()
                .mailTo(createdUser.getEmail())
                .subject("User Verification")
                .verificationCode(verificationCode)
                .codeExpirationTime(VERIFICATION_CODE_EXPIRATION_TIME)
                .userName(createdUser.getFirstName())
                .build();

        emailService.sendVerificationEmail(mail);

        return userRepository.save(createdUser);
    }

    public boolean verifyUser(String email, String verificationCode)
            throws UserNotFoundException, InvalidVerificationCodeException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException();
        }
        
        if (!user.getVerificationCode().equals(verificationCode)) {
            throw new InvalidVerificationCodeException();
        }
        
        user.setUserStatus(UserStatus.VERIFIED);
        user.setVerificationCode(null);
        userRepository.save(user);
        return true;
    }

    public User updateUser(Long id, User user) throws UserNotFoundException {
        return userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setLogin(user.getLogin());
                    existingUser.setFirstName(user.getFirstName());
                    existingUser.setLastName(user.getLastName());
                    existingUser.setEmail(user.getEmail());
                    return userRepository.save(existingUser);
                })
                .orElseThrow(UserNotFoundException::new);
    }

    public void deleteUser(Long id) throws UserNotFoundException {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException();
        }
        userRepository.deleteById(id);
    }

    public User generateUserToken(Long id) throws UserNotFoundException {
        return userRepository.findById(id)
                .map(user -> {
                    String newToken = UUID.randomUUID().toString();
                    user.setToken(newToken);
                    user.setTokenCreatedAt(LocalDateTime.now());
                    user.setTokenExpiresAt(LocalDateTime.now().plusDays(1));
                    return userRepository.save(user);
                })
                .orElseThrow(UserNotFoundException::new);
    }
}
