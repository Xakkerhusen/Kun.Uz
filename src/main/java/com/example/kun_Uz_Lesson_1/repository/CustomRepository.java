package com.example.kun_Uz_Lesson_1.repository;

import com.example.kun_Uz_Lesson_1.dto.FilterProfileDTO;
import com.example.kun_Uz_Lesson_1.dto.FilterProfileDTO;
import com.example.kun_Uz_Lesson_1.dto.PaginationResultDTO;
import com.example.kun_Uz_Lesson_1.entity.ProfileEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CustomRepository {
    @Autowired
    private EntityManager entityManager;


    public PaginationResultDTO<ProfileEntity> profileFilter(FilterProfileDTO filter, Pageable pageable) {
        StringBuilder builder = new StringBuilder();
        Map<String, Object> params = new HashMap<>();
        if (filter.getId() != null) {
            builder.append(" and id=:id ");
            params.put("id", filter.getId());
        }
        if (filter.getName() != null) {
            builder.append(" and lower(name) like :name ");
            params.put("name", "%"+filter.getName().toLowerCase()+"%");
        }
        if (filter.getSurname() != null) {
            builder.append(" and lower(surname)like :surname ");
            params.put("surname", "%"+filter.getSurname().toLowerCase()+"%");
        }
        if (filter.getRole() != null) {
            builder.append(" and role=:role ");
            params.put("role", filter.getRole());
        }
        if (filter.getFromDate() != null && filter.getToDate() != null) {
            LocalDateTime fromDate = LocalDateTime.of(filter.getFromDate(), LocalTime.MIN);
            LocalDateTime toDate = LocalDateTime.of(filter.getToDate(), LocalTime.MAX);
            builder.append(" and createdDate  between :fromDate and :toDate");
            params.put("fromDate", fromDate);
            params.put("toDate", toDate);
        } else if (filter.getFromDate() != null) {
            LocalDateTime fromDate = LocalDateTime.of(filter.getFromDate(), LocalTime.MIN);
            LocalDateTime toDate = LocalDateTime.of(filter.getFromDate(), LocalTime.MAX);
            builder.append(" and createdDate between :fromDate and :toDate ");
            params.put("fromDate", fromDate);
            params.put("toDate", toDate);
        } else if (filter.getToDate() != null) {
            builder.append(" and createdDate <= :toDate");
            params.put("toDate", filter.getToDate());
        }
        StringBuilder selectBuilder = new StringBuilder(" from ProfileEntity p where 1=1 ");
        selectBuilder.append(builder);
        selectBuilder.append(" order by createdDate desc ");

        StringBuilder countBuilder = new StringBuilder(" select count(p) from ProfileEntity p where 1=1 ");
        countBuilder.append(builder);

        Query selectQuery = entityManager.createQuery(selectBuilder.toString());
        selectQuery.setMaxResults(pageable.getPageSize());
        selectQuery.setFirstResult((int) pageable.getOffset());

        Query countQuery = entityManager.createQuery(countBuilder.toString());

        for (Map.Entry<String, Object> param : params.entrySet()) {
            selectQuery.setParameter(param.getKey(),param.getValue());
            countQuery.setParameter(param.getKey(),param.getValue());
        }
        List<ProfileEntity> enitityList = selectQuery.getResultList();
        Long totalElement = (Long) countQuery.getSingleResult();
        return new PaginationResultDTO<>(totalElement, enitityList);
    }


}
