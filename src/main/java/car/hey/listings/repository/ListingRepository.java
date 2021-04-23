package car.hey.listings.repository;

import car.hey.listings.model.Listing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListingRepository extends JpaRepository<Listing, Long> {

    <Optional> Listing findById(Integer id);

    <Optional> Listing findOneByDealerAndCode(Integer dealerId, String code);

    List<Listing> findAllByDealer(int dealerId);

}
