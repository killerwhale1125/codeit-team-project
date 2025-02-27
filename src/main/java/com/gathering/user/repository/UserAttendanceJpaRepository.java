package com.gathering.user.repository;

import com.gathering.user.model.entitiy.UserAttendance;
import jakarta.transaction.Transactional;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserAttendanceJpaRepository extends JpaRepository<UserAttendance, Long> {
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO USER_ATTENDANCE (USER_ID, CREATE_DATE) VALUES (:userId, DATE_FORMAT(NOW(), '%Y-%m-%d')) " +
            "ON DUPLICATE KEY UPDATE USER_ID = :userId", nativeQuery = true)
    int insertAttendance(@Param("userId") Long userId);
//
//    @EntityGraph(attributePaths = {"userAttendanceBook", "userAttendanceBook.book"})
    @Query("SELECT ua FROM UserAttendance ua WHERE ua.user.id = :userId AND ua.createDate BETWEEN :startDate AND :endDate")
    List<UserAttendance> getUserAttendancesByUserIdAndDate(@Param("userId") Long userId,
                                                   @Param("startDate") LocalDate startDate,
                                                   @Param("endDate") LocalDate endDate);

    Optional<UserAttendance> findByUserIdAndCreateDate(long userId, LocalDate today);
}
