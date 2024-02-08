package com.example.kun_Uz_Lesson_1.service;

import com.example.kun_Uz_Lesson_1.dto.region.CreatedRegionDTO;
import com.example.kun_Uz_Lesson_1.dto.region.Region;
import com.example.kun_Uz_Lesson_1.entity.ProfileEntity;
import com.example.kun_Uz_Lesson_1.entity.RegionEntity;
import com.example.kun_Uz_Lesson_1.enums.Language;
import com.example.kun_Uz_Lesson_1.exp.AppBadException;
import com.example.kun_Uz_Lesson_1.repository.RegionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
@Slf4j
@Service
public class RegionService {
    @Autowired
    private RegionRepository regionRepository;

    public CreatedRegionDTO create(CreatedRegionDTO dto) {
        RegionEntity entity = new RegionEntity();
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        regionRepository.save(entity);
        return dto;
    }

    public Region update(Integer id, Region dto) {
        RegionEntity entity = get(id);

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
        Integer effectiveRows = regionRepository.update(id, entity.getNameUz(), entity.getNameEn(), entity.getNameEn());
        if (effectiveRows == 0) {
            log.warn("Region not found{}",effectiveRows);
            throw new AppBadException("Region not found");
        }
        return dto;
    }

    public Boolean delete(Integer id) {
        Integer effectiveRows = regionRepository.deleteRegionById(id);
        if (effectiveRows == 0) {
            log.warn("Region not found{}",effectiveRows);
            throw new AppBadException("Region not found");
        }
        return true;
    }

    public List<Region> all() {
        Iterable<RegionEntity> all = regionRepository.findAllRegion();
        List<Region> regionList = new LinkedList<>();
        for (RegionEntity entity : all) {
            regionList.add(toDo(entity));
        }
        return regionList;
    }

    public List<Region> getAllByLang(Language language) {
        Iterable<RegionEntity> all = regionRepository.findAllRegion();
        List<Region> dtoList = new LinkedList<>();
        for (RegionEntity entity : all) {
            Region dto = new Region();
                dto.setId(entity.getId());
                switch (language) {
                    case UZ -> dto.setName(entity.getNameUz());
                    case RU -> dto.setName(entity.getNameRu());
                    default -> dto.setName(entity.getNameEn());
                }
            dtoList.add(dto);
        }
        return dtoList;
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


    public  RegionEntity get(Integer id) {
//        return regionRepository.findById(id).orElseThrow(() -> new AppBadException("Region not found"));
        return regionRepository.findById(id).orElseThrow(() -> {
            log.warn("Region not found{}",id);
            return new AppBadException("Region not found");
        });
    }

}
