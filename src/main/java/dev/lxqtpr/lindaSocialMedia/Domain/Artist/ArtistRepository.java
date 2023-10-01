package dev.lxqtpr.lindaSocialMedia.Domain.Artist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;

public interface ArtistRepository extends
        JpaRepository<ArtistEntity, Long>,
        ListPagingAndSortingRepository<ArtistEntity,Long>
{

}
