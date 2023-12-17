package dev.lxqtpr.lindaSocialMedia.Domain.Chat;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<ChatEntity,Long> {
}
