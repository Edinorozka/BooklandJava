package com.booklnad.bookland.DB.repository;

import com.booklnad.bookland.DB.entity.Authorized;
import com.booklnad.bookland.DB.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.Optional;

public interface AuthorizedRepository extends JpaRepository<Authorized, Long> {
    Optional<Authorized> findById(int id);
    Optional<Authorized> findByUser(User user);
    Optional<Authorized> findByRefreshToken(String refreshToken);
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "Update authorized set refresh_token = :refreshToken, date_authorization = :dateAuthorization " +
            "where user_id = :user_id", nativeQuery = true)
    void updateAuthorized(@Param("refreshToken") String refreshToken, @Param("dateAuthorization") Date date, @Param("user_id") int userId);
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "delete from authorized where user_id = :user_id", nativeQuery = true)
    void deleteToken(@Param("user_id") int userId);
}
