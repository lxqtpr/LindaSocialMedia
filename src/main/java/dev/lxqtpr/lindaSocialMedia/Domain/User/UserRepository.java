package dev.lxqtpr.lindaSocialMedia.Domain.User;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
}
