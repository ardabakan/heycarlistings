package car.hey.listings.service.impl;

import car.hey.listings.model.Listing;
import car.hey.listings.repository.ListingRepository;
import car.hey.listings.service.ListingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ListingServiceImpl implements ListingService {

    @Autowired
    ListingRepository listingRepository;

    public List<Listing> upsertListings(List<Listing> listings) {

        for (Listing listing : listings) {

            Listing listingSearched = listingRepository
                    .findOneByDealerAndCode(listing.getDealer(),
                            listing.getCode());

            if (listingSearched != null) {

                listing.setId(listingSearched.getId());

            }

        }

        return listingRepository.saveAll(listings);

    }

    public List<Listing> findListingsOfADealer(int dealerId) {

        return listingRepository.findAllByDealer(dealerId);

    }

    public List<Listing> searchForAListing(String make, String model,
                                           Integer year,
                                           String color) {

        Listing listing = new Listing();
        listing.setMake(make);
        listing.setModel(model);
        listing.setYear(year);
        listing.setColor(color);

        ExampleMatcher exampleMatcher =

                ExampleMatcher.matching()
                        .withIgnorePaths("powerInHP")
                        .withIgnorePaths("powerInKW")
                        .withIgnorePaths("dealer")
                        .withIgnorePaths("code")
                        .withIgnorePaths("price")
                        .withIgnoreNullValues();

        Example<Listing> listingExample = Example.of(listing, exampleMatcher);

        return listingRepository.findAll(listingExample);

    }

}
