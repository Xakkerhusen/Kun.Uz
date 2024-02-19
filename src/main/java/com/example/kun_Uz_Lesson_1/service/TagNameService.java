package com.example.kun_Uz_Lesson_1.service;

import com.example.kun_Uz_Lesson_1.dto.TagNameDTO;
import com.example.kun_Uz_Lesson_1.entity.TagNameEntity;
import com.example.kun_Uz_Lesson_1.enums.Language;
import com.example.kun_Uz_Lesson_1.exp.AppBadException;
import com.example.kun_Uz_Lesson_1.repository.TagNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TagNameService {
    @Autowired
    private TagNameRepository tagNameRepository;
    @Autowired
    private ResourceBundleService resourceBundleService;
    public TagNameDTO create(TagNameDTO dto) {

        TagNameEntity entity=new TagNameEntity();
        entity.setTagName(dto.getName());
        entity.setCreatedDate(LocalDateTime.now());
        tagNameRepository.save(entity);
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setName(entity.getTagName());
        return dto;
    }
    public TagNameEntity get(Long tagId, Language language){
        return tagNameRepository.findById(tagId).orElseThrow(() ->
                new AppBadException(resourceBundleService.getMessage("tag.name.not.found",language)));
    }
}
