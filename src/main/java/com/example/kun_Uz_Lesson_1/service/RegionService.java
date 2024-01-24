package com.example.kun_Uz_Lesson_1.service;

import com.example.kun_Uz_Lesson_1.dto.Region;
import com.example.kun_Uz_Lesson_1.entity.RegionEntity;
import com.example.kun_Uz_Lesson_1.enums.Language;
import com.example.kun_Uz_Lesson_1.exp.AppBadException;
import com.example.kun_Uz_Lesson_1.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class RegionService {
    @Autowired
    private RegionRepository regionRepository;

    public Region create(Region dto) {
        if (dto.getOrderNumber() == null || dto.getOrderNumber() <= 0) {
            throw new AppBadException("Article Type order number required ");
        }
        if (dto.getNameEn() == null || dto.getNameEn().trim().length() <= 1
                || dto.getNameUz() == null || dto.getNameUz().trim().length() <= 1
                || dto.getNameRu() == null || dto.getNameRu().trim().length() <= 1) {
            throw new AppBadException("Article Type Language required ");
        }
        RegionEntity entity = new RegionEntity();
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        regionRepository.save(entity);
        dto.setId(entity.getId());
        dto.setVisible(entity.getVisible());
        dto.setCreatedDate(entity.getCreatedDate());

        return dto;
    }

    public Boolean update(Integer id, Region region) {
        String nameUz = region.getNameUz().trim();
        String nameEn = region.getNameEn().trim();
        String nameRu = region.getNameRu().trim();
        Integer effectiveRows = regionRepository.update(id, nameUz, nameEn, nameRu);
        if (effectiveRows == 0) {
            throw new AppBadException("Region not found");
        }
        return true;
    }

    public Boolean delete(Integer id) {
        Integer effectiveRows = regionRepository.deleteRegionById(id);
        if (effectiveRows == 0) {
            throw new AppBadException("Region not found");
        }
        return true;
    }

    public List<Region> all() {
        Iterable<RegionEntity> all = regionRepository.findAll();
        List<Region> regionList = new LinkedList<>();
        for (RegionEntity entity : all) {
            regionList.add(toDo(entity));
        }
        return regionList;
    }

    public List<Region> getAllByLang(String language) {
        if (!language.toUpperCase().equals(String.valueOf(Language.EN)) &&
                !language.toUpperCase().equals(String.valueOf(Language.UZ)) &&
                !language.toUpperCase().equals(String.valueOf(Language.RU))) {
            throw new AppBadException("Wrong language");
        }
        Language language1 = Language.valueOf(language.toUpperCase());
        List<Region> regionList = new LinkedList<>();
        List<RegionEntity> allRegionByLang = null;
        if (language1.equals(Language.UZ)) {
            allRegionByLang = regionRepository.getAllByLanUz();
        } else if (language1.equals(Language.EN)) {
            allRegionByLang = regionRepository.getAllByLanEn();
        } else if (language1.equals(Language.RU)) {
            allRegionByLang = regionRepository.getAllByLanRu();
        }
        if (allRegionByLang.isEmpty()) {
            throw new AppBadException("ERROR!!!");
        }
        for (RegionEntity entity : allRegionByLang) {
            regionList.add(toDo2(entity, language1));
        }

        return regionList;
    }

    private Region toDo(RegionEntity entity) {
        Region region = new Region();
        region.setId(entity.getId());
        region.setCreatedDate(entity.getCreatedDate());
        region.setVisible(entity.getVisible());
        region.setOrderNumber(entity.getOrderNumber());
        region.setNameUz(entity.getNameUz());
        region.setNameEn(entity.getNameEn());
        region.setNameRu(entity.getNameRu());
        return region;
    }

    private Region toDo2(RegionEntity entity, Language language) {
        Region region = new Region();
        region.setId(entity.getId());
        region.setCreatedDate(entity.getCreatedDate());
        region.setOrderNumber(entity.getOrderNumber());
        if (language.equals(Language.UZ)) {
            region.setNameUz(entity.getNameUz());
        } else if (language.equals(Language.EN)) {
            region.setNameEn(entity.getNameEn());
        } else if (language.equals(Language.RU)) {
            region.setNameRu(entity.getNameRu());
        }
        return region;
    }
}
