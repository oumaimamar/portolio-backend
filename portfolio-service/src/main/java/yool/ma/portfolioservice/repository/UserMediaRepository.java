package yool.ma.portfolioservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yool.ma.portfolioservice.model.*;

import java.util.List;

@Repository
public interface UserMediaRepository extends JpaRepository<UserMedia, Long> {
    List<UserMedia> findByProfile(Profile profile);
    List<UserMedia> findByProfileAndMediaType(Profile profile, MediaType mediaType);
}
