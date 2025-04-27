package yool.ma.portfolioservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yool.ma.portfolioservice.model.*;

import java.util.List;

@Repository
public interface UserMediaRepository extends JpaRepository<UserMedia, Long> {
    List<UserMedia> findByProfileId(Long profileId);
    List<UserMedia> findByCategory(String category);
    List<UserMedia> findByMediaType(MediaType mediaType);
    boolean existsByNameAndProfileId(String name, Long profileId);
}
