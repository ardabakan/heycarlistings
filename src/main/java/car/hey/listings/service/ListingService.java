package car.hey.listings.service;

import car.hey.listings.model.Listing;

import java.util.List;

public interface ListingService {

    public List<Listing> upsertListings(List<Listing> listing);

    public List<Listing> findListingsOfADealer(int dealerId);

    public List<Listing> searchForAListing(String make, String model,
                                           Integer year,
                                           String color);

}
