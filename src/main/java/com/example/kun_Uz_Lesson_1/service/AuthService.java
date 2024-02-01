package com.example.kun_Uz_Lesson_1.service;

import com.example.kun_Uz_Lesson_1.dto.Auth;
import com.example.kun_Uz_Lesson_1.dto.JWTDTO;
import com.example.kun_Uz_Lesson_1.dto.profile.Profile;
import com.example.kun_Uz_Lesson_1.dto.profile.RegistrationProfileDTO;
import com.example.kun_Uz_Lesson_1.entity.EmailSentHistoryEntity;
import com.example.kun_Uz_Lesson_1.entity.ProfileEntity;
import com.example.kun_Uz_Lesson_1.entity.SmsHistoryEntity;
import com.example.kun_Uz_Lesson_1.enums.ProfileRole;
import com.example.kun_Uz_Lesson_1.enums.ProfileStatus;
import com.example.kun_Uz_Lesson_1.exp.AppBadException;
import com.example.kun_Uz_Lesson_1.repository.EmailSentHistoryRepository;
import com.example.kun_Uz_Lesson_1.repository.ProfileRepository;
import com.example.kun_Uz_Lesson_1.repository.SmsHistoryRepository;
import com.example.kun_Uz_Lesson_1.utils.JWTUtil;
import com.example.kun_Uz_Lesson_1.utils.MD5Util;
import com.example.kun_Uz_Lesson_1.utils.RandomUtil;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class AuthService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private MailSenderService mailSender;
    @Autowired
    private EmailSentHistoryRepository emailSentHistoryRepository;
    @Autowired
    private SmsHistoryRepository smsHistoryRepository;
    @Autowired
    private EmailSentHistoryRepository emailSendHistoryRepository;
    @Autowired
    private SmsHistoryService smsHistoryService;
    @Autowired
    private EmailHistoryService emailHistoryService;
    @Autowired
    private SmsSenderService smsSenderService;

    public Profile auth(Auth auth) {
        Optional<ProfileEntity> optional =
                profileRepository.findByEmailAndPassword(auth.getEmail(), MD5Util.encode(auth.getPassword()));
        if (optional.isEmpty()) {
            throw new AppBadException("Email or Password is wrong");
        }
        if (!optional.get().getStatus().equals(ProfileStatus.ACTIVE)) {
            throw new AppBadException("Profile not active");
        }

        ProfileEntity entity = optional.get();

        Profile dto = new Profile();
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setRole(entity.getRole());
        dto.setJwt(JWTUtil.encode(entity.getId(), entity.getRole()));

        return dto;
    }

    public Boolean registration(RegistrationProfileDTO dto) {
        if (dto.getName() == null || dto.getName().trim().length() <= 2) {
            throw new AppBadException("Name required");
        }
        if (dto.getSurname() == null || dto.getSurname().trim().length() <= 2) {
            throw new AppBadException("Surname required");
        }
        if (!dto.getPassword().matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{5,}$")) {
            throw new AppBadException("Password required");
        }
        Optional<ProfileEntity> optional = profileRepository.findByEmail(dto.getEmail());
        if (optional.isPresent()) {
            if (optional.get().getStatus().equals(ProfileStatus.REGISTRATION)) {
                profileRepository.delete(optional.get());
            } else {
                throw new AppBadException("Email exists");
            }
        }

        LocalDateTime from = LocalDateTime.now().minusMinutes(1);
        LocalDateTime to = LocalDateTime.now();
        if (emailSendHistoryRepository.countSendEmail(dto.getEmail(), from, to) >= 4) {
            throw new AppBadException("To many attempt. Please try after 1 minute.");
        }

        if (dto.getEmail() != null && dto.getPhoneNumber() == null) {
            registrationByEmail(dto);
        } else if (dto.getPhoneNumber() != null && dto.getEmail() != null) {
            registrationByPhoneNumber(dto);
        }
        return true;
    }

    public Profile emailVerification(String jwt) {
        try {
            JWTDTO jwtDTO = JWTUtil.decode(jwt);

            Optional<ProfileEntity> optional = profileRepository.findById(jwtDTO.getId());
            if (optional.isEmpty()) {
                throw new AppBadException("Profile not found");
            }
            ProfileEntity entity = optional.get();
            if (!entity.getStatus().equals(ProfileStatus.REGISTRATION)) {
                throw new AppBadException("Profile in wrong status");
            }
            Profile profile = new Profile();
            profileRepository.updateStatus(entity.getId(), ProfileStatus.ACTIVE);
            profile.setName(entity.getName());
            profile.setSurname(entity.getSurname());
            profile.setRole(entity.getRole());
            profile.setJwt(JWTUtil.encode(entity.getId(), entity.getRole()));

        } catch (JwtException e) {
            throw new AppBadException("Please tyre again.");
        }
        return null;
    }

    private void registrationByPhoneNumber(RegistrationProfileDTO dto) {
        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setEmail(dto.getEmail());
        entity.setPassword(MD5Util.encode(dto.getPassword()));
        entity.setStatus(ProfileStatus.REGISTRATION);
        entity.setRole(ProfileRole.USER);
        profileRepository.save(entity);

        String code = RandomUtil.getRandomCode();
        smsSenderService.send(dto.getPhoneNumber(), "Husendan salomlar yuborilgan kodni kriting->https://localhost:8080/auth/verification/phone/ linkga kiriting:\nkod:", code);

        smsHistoryService.create(dto, code);
    }

    private void registrationByEmail(RegistrationProfileDTO dto) {

        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        entity.setPassword(MD5Util.encode(dto.getPassword()));
        entity.setStatus(ProfileStatus.REGISTRATION);
        entity.setRole(ProfileRole.USER);
        profileRepository.save(entity);

        String jwt = JWTUtil.encodeForEmail(entity.getId());
        String text = "<h1 style=\"text-align: center\">Hello %s</h1>\n" +
                "<p style=\"background-color: indianred; color: white; padding: 30px\">To complete registration please link to the following link</p>\n" +
                "<a style=\" background-color: #f44336;\n" +
                "  color: white;\n" +
                "  padding: 14px 25px;\n" +
                "  text-align: center;\n" +
                "  text-decoration: none;\n" +
                "  display: inline-block;\" href=\"http://localhost:8080/auth/verification/email/%s\n" +
                "\">Click</a>\n" +
                "<br>\n";
        text = String.format(text, entity.getName(), jwt);
        mailSender.sendEmail(dto.getEmail(), "Complete registration", text);

        emailHistoryService.create(dto, text);
    }

    public String smsVerification(String phone, String code) {



        return null;
    }


}
