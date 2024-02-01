package com.example.kun_Uz_Lesson_1.repository;

import com.example.kun_Uz_Lesson_1.entity.ProfileEntity;
import com.example.kun_Uz_Lesson_1.enums.ProfileRole;
import com.example.kun_Uz_Lesson_1.enums.ProfileStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends CrudRepository<ProfileEntity, Integer>, PagingAndSortingRepository<ProfileEntity, Integer> {
    Optional<ProfileEntity> findByEmail(String email);

    Optional<ProfileEntity> findByEmailAndPassword(String email, String password);

    @Transactional
    @Modifying
    @Query("update ProfileEntity p set p.name=:name,p.surname=:surname,p.role=:role,p.status=:status where p.id=:id")
    Integer update(String name, String surname, ProfileRole role, ProfileStatus status, Integer id);

    @Transactional
    @Modifying
    @Query("update ProfileEntity set visible=false where id=:id")
    Integer deleteByIdProfile(Integer id);

    Optional<ProfileEntity> findBySms(String sms);

    @Transactional
    @Modifying
    @Query("update ProfileEntity set status='ACTIVE' where email=:email")
    Integer register(String email);

    @Transactional
    @Modifying
    @Query("Update ProfileEntity  set status =?2 where id = ?1")
    void updateStatus(Integer id, ProfileStatus active);


}
