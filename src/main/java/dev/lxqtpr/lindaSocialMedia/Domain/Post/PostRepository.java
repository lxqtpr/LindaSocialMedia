package dev.lxqtpr.lindaSocialMedia.Domain.Post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;

public interface PostRepository extends
        JpaRepository<PostEntity,Long>,
        ListPagingAndSortingRepository<PostEntity,Long>
{

}
