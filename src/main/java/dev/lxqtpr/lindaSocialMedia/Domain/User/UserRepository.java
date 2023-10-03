package dev.lxqtpr.lindaSocialMedia.Domain.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long>, JpaRepository<UserEntity, Long>{

    Optional<UserEntity> findUserEntityByUsername(String username);
}
