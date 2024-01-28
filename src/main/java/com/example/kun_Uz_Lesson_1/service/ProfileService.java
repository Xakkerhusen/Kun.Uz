package com.example.kun_Uz_Lesson_1.service;

import com.example.kun_Uz_Lesson_1.dto.FilterProfileDTO;
import com.example.kun_Uz_Lesson_1.dto.profile.CreatedProfileDTO;
import com.example.kun_Uz_Lesson_1.dto.FilterProfileDTO;
import com.example.kun_Uz_Lesson_1.dto.PaginationResultDTO;
import com.example.kun_Uz_Lesson_1.dto.profile.Profile;
import com.example.kun_Uz_Lesson_1.entity.ProfileEntity;
import com.example.kun_Uz_Lesson_1.enums.ProfileRole;
import com.example.kun_Uz_Lesson_1.enums.ProfileStatus;
import com.example.kun_Uz_Lesson_1.exp.AppBadException;
import com.example.kun_Uz_Lesson_1.repository.CustomRepository;
import com.example.kun_Uz_Lesson_1.repository.ProfileRepository;
import com.example.kun_Uz_Lesson_1.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private CustomRepository customRepository;

    public CreatedProfileDTO create(CreatedProfileDTO dto) {
        if (dto.getName() == null || dto.getName().trim().length() <= 1) {
            throw new AppBadException("Profile name required");
        }
        if (dto.getSurname() == null || dto.getSurname().trim().length() <= 1) {
            throw new AppBadException("Profile surname required");
        }
        if (dto.getEmail() == null || dto.getEmail().trim().length() < 8) {
            throw new AppBadException("Profile email required");
        }
        if (dto.getPassword() == null || dto.getPassword().trim().isEmpty()) {
            throw new AppBadException("Profile password required");
        }
        ProfileEntity entity = new ProfileEntity();
        if (dto.getStatus() == null) {
            entity.setStatus(ProfileStatus.ACTIVE);
        } else {
            entity.setStatus(dto.getStatus());
        }
        if (dto.getRole() == null) {
            entity.setRole(ProfileRole.USER);
        } else {
            entity.setRole(dto.getRole());
        }
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        entity.setPassword(MD5Util.encode(dto.getPassword()));
        profileRepository.save(entity);

        return dto;
    }

    public Profile update(Profile dto, Integer id) {
        ProfileEntity entity = get(id);

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
    public Boolean delete(Integer id) {
        ProfileEntity profileEntity = get(id);
        Integer effectiveRows = profileRepository.deleteByIdProfile(profileEntity.getId());
        if (effectiveRows == 0) {
            throw new AppBadException("Profile not found");
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

    private ProfileEntity get(Integer id) {
        return profileRepository.findById(id).orElseThrow(() -> new AppBadException("Profile not found"));
    }


    public Profile updateDetail(Profile dto, Integer id) {
        ProfileEntity entity = get(id);
        if (entity == null) {
            throw new AppBadException("Profile not found");
        }
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
