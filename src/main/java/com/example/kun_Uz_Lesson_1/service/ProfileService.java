package com.example.kun_Uz_Lesson_1.service;

import com.example.kun_Uz_Lesson_1.dto.profile.FilterProfileDTO;
import com.example.kun_Uz_Lesson_1.dto.profile.CreatedProfileDTO;
import com.example.kun_Uz_Lesson_1.dto.PaginationResultDTO;
import com.example.kun_Uz_Lesson_1.dto.profile.Profile;
import com.example.kun_Uz_Lesson_1.entity.ProfileEntity;
import com.example.kun_Uz_Lesson_1.enums.Language;
import com.example.kun_Uz_Lesson_1.exp.AppBadException;
import com.example.kun_Uz_Lesson_1.repository.CustomRepository;
import com.example.kun_Uz_Lesson_1.repository.ProfileRepository;
import com.example.kun_Uz_Lesson_1.utils.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
@Slf4j
@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private CustomRepository customRepository;
    @Autowired
    private ResourceBundleService resourceBundleService;

    public CreatedProfileDTO create(CreatedProfileDTO dto, Language language) {
        if (!dto.getPassword().matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{5,}$")) {
            log.warn("Profile password required{}",dto.getPassword());
            throw new AppBadException(resourceBundleService.getMessage("password.required",language));
        }
        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        entity.setRole(dto.getRole());
        entity.setPassword(MD5Util.encode(dto.getPassword()));
        profileRepository.save(entity);

        return dto;
    }

    public Profile update(Profile dto, Integer id, Language language) {
        ProfileEntity entity = get(id,language);

        if (dto.getName() != null) {
            entity.setName(dto.getName());
        }else {
            dto.setName(entity.getName());
        }
        if (dto.getSurname() != null) {
            entity.setSurname(dto.getSurname());
        }else {
            dto.setSurname(entity.getSurname());
        }
        if (dto.getRole() != null) {
            entity.setRole(dto.getRole());
        }else {
            dto.setRole(entity.getRole());
        }
        if (dto.getStatus() != null) {
            entity.setStatus(dto.getStatus());
        }else {
            dto.setStatus(entity.getStatus());
        }
        entity.setUpdatedDate(LocalDateTime.now());
        profileRepository.update(dto.getName(), dto.getSurname(), dto.getRole(), dto.getStatus(), entity.getId());
        return dto;
    }
    public Boolean delete(Integer id,Language language) {
        ProfileEntity profileEntity = get(id,language);
        Integer effectiveRows = profileRepository.deleteByIdProfile(profileEntity.getId());
        if (effectiveRows == 0) {
            log.warn("Profile not found{}",effectiveRows);
            throw new AppBadException(resourceBundleService.getMessage("profile.not.found",language));
        }
        return true;
    }
    public PageImpl<Profile> filter(FilterProfileDTO filter, Pageable pageable) {
        PaginationResultDTO<ProfileEntity> resultFilter = customRepository.profileFilter(filter, pageable);

        List<Profile>profileList=new LinkedList<>();
        for (ProfileEntity entity : resultFilter.getList()) {
            profileList.add(toDo(entity));
        }
        return new PageImpl<>(profileList, pageable, resultFilter.getTotalSize());
    }

    public Profile toDo(ProfileEntity entity){
        Profile dto = new Profile();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setEmail(entity.getEmail());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }
    public Profile toDoForComment(ProfileEntity entity){
        Profile dto = new Profile();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        return dto;
    }

    public ProfileEntity get(Integer id,Language language) {
//        return profileRepository.findById(id).orElseThrow(() -> new AppBadException("Profile not found"));
        return profileRepository.findById(id).orElseThrow(() -> {
            log.warn("Profile not found{}",id);
            return new AppBadException(resourceBundleService.getMessage("profile.not.found",language));
        });
    }


    public Profile updateDetail(Profile dto, Integer id, Language language) {
        ProfileEntity entity = get(id,language);
        if (dto.getName() != null) {
            entity.setName(dto.getName());
        }else {
        dto.setName(entity.getName());
        }
        if (dto.getSurname() != null) {
            entity.setSurname(dto.getSurname());
        }else {
            dto.setSurname(entity.getSurname());
        }
        if (dto.getPassword() != null) {
            entity.setPassword(MD5Util.encode(dto.getPassword()));
        }else {
            dto.setPassword(entity.getPassword());
        }
        profileRepository.save(entity);
        return dto;
    }
}
