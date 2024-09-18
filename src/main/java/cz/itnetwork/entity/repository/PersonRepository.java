package cz.itnetwork.entity.repository;

import cz.itnetwork.dto.PersonStatisticsDTO;
import cz.itnetwork.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PersonRepository extends JpaRepository<PersonEntity, Long>, CrudRepository<PersonEntity, Long> {

    List<PersonEntity> findByHidden(boolean hidden);

    @Query("SELECT new cz.itnetwork.dto.PersonStatisticsDTO(p.id, p.name, SUM(i.price)) " +
            "FROM person p LEFT JOIN invoice i ON p.id = i.seller.id " +
            "GROUP BY p.id, p.name")
    List<PersonStatisticsDTO> getPersonStatistics();
}
