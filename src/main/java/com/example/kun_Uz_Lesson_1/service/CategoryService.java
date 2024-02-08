package com.example.kun_Uz_Lesson_1.service;

import com.example.kun_Uz_Lesson_1.dto.category.Category;
import com.example.kun_Uz_Lesson_1.dto.category.CreateCategoryDTO;
import com.example.kun_Uz_Lesson_1.dto.region.Region;
import com.example.kun_Uz_Lesson_1.entity.CategoryEntity;
import com.example.kun_Uz_Lesson_1.entity.ProfileEntity;
import com.example.kun_Uz_Lesson_1.entity.RegionEntity;
import com.example.kun_Uz_Lesson_1.enums.Language;
import com.example.kun_Uz_Lesson_1.exp.AppBadException;
import com.example.kun_Uz_Lesson_1.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
@Slf4j
@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public CreateCategoryDTO create(CreateCategoryDTO dto) {
        CategoryEntity entity = new CategoryEntity();
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        categoryRepository.save(entity);
        return dto;
    }

    public Category update(Integer id, Category dto) {
        CategoryEntity entity = get(id);
        if (dto.getOrderNumber() != null) {
            entity.setOrderNumber(dto.getOrderNumber());
        } else {
            dto.setOrderNumber(entity.getOrderNumber());
        }
        if (dto.getNameUz() != null) {
            entity.setNameUz(dto.getNameUz());
        } else {
            dto.setNameUz(entity.getNameUz());
        }
        if (dto.getNameRu() != null) {
            entity.setNameRu(dto.getNameRu());
        } else {
            dto.setNameRu(entity.getNameRu());
        }
        if (dto.getNameEn() != null) {
            entity.setNameEn(dto.getNameEn());
        } else {
            dto.setNameEn(entity.getNameEn());
        }
        entity.setUpdatedDate(LocalDateTime.now());
        categoryRepository.update(id, entity.getNameUz(), entity.getNameRu(), entity.getNameEn());
        return dto;
    }

    public CategoryEntity get(Integer id) {
//        return categoryRepository.findById(id).orElseThrow(() -> new AppBadException("Category not found"));
        return categoryRepository.findById(id).orElseThrow(() -> {
            log.warn("Category not found{}",id);
            return new AppBadException("Category not found");
        });
    }

    public Boolean delete(Integer id) {
        Integer effectiveRows = categoryRepository.deleteCategoryById(id);
        if (effectiveRows == 0) {
            log.warn("Category not found {}",effectiveRows);
            throw new AppBadException("Category not found");
        }
        return true;
    }

    public PageImpl<Category> all(Pageable pageable) {
        Page<CategoryEntity> allCategory = categoryRepository.findAllCategory(pageable);
        List<CategoryEntity> content = allCategory.getContent();
        long totalElements = allCategory.getTotalElements();
        List<Category> categories = new LinkedList<>();
        for (CategoryEntity entity : content) {
            categories.add(toDo(entity));
        }
        return new PageImpl<>(categories, pageable, totalElements);
    }

    private Category toDo(CategoryEntity entity) {
        Category category = new Category();
        category.setId(entity.getId());
        category.setOrderNumber(entity.getOrderNumber());
        category.setNameUz(entity.getNameUz());
        category.setNameRu(entity.getNameRu());
        category.setNameEn(entity.getNameEn());
        if (entity.getUpdatedDate() != null) {
            category.setCreatedDate(entity.getUpdatedDate());
        } else {
            category.setCreatedDate(entity.getCreatedDate());
        }
        return category;
    }

    public List<Category> gatAllByLang(Language language) {
        List<CategoryEntity> allCategoryByLang = categoryRepository.findAllCategoryByLang();
        if (allCategoryByLang.isEmpty()) {
            log.warn("Category not found {}",allCategoryByLang);
            throw new AppBadException("Category is empty");
        }
        List<Category> categoryList = new LinkedList<>();
        for (CategoryEntity entity : allCategoryByLang) {
            Category category = new Category();
            category.setId(entity.getId());
            switch (language) {
                case UZ -> category.setName(entity.getNameUz());
                case RU -> category.setName(entity.getNameRu());
                default -> category.setName(entity.getNameEn());
            }
            categoryList.add(category);
        }
        return categoryList;
    }


}
