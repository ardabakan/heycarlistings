package car.hey.listings.controller;

import car.hey.listings.exceptions.BadRequestException;
import car.hey.listings.exceptions.HeycarException;
import car.hey.listings.model.Listing;
import car.hey.listings.service.ConversionService;
import car.hey.listings.service.ListingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

@RestController
@Slf4j
public class ListingController {

    @Autowired
    ListingService listingService;

    @Autowired
    ConversionService conversionService;

    @PostMapping("/upload-csv-file/{dealerId}")
    public List<Listing> upsertListingsByCSVFile(
            @RequestParam("file") MultipartFile multipartFile,
            @PathVariable Integer dealerId)
            throws BadRequestException {

        if (multipartFile.isEmpty()) {
            return Collections.emptyList();
        }

        List<Listing> convertedList =
                null;

        try {
            convertedList =
                    conversionService.convertCSVToListings(multipartFile,
                            dealerId);
        } catch (HeycarException e) {
            throw new BadRequestException();
        }

        return listingService.upsertListings(convertedList);
    }

    @PostMapping("/vehicle_listings/{dealerId}")
    public List<Listing> upsertListingsByJson(@RequestBody List<Listing> listings,
                                              @PathVariable Integer dealerId) {

        // @todo add security layer and have dealerId from the user details
        //  of spring boot

        log.info("Upserting {} listings by JSON endpoint from dealer {}",
                listings.size(),
                dealerId);

        // @todo add a validation layer here (i.e check if listings really
        //  belongs to the dealerId etc..)

        return listingService.upsertListings(listings);
    }

    @GetMapping("/search")
    public List<Listing> getListings(@RequestParam(required=false) String make,
                                         @RequestParam(required=false) String model,
                                         @RequestParam(required=false) Integer year,
                                         @RequestParam(required=false) String color) {
        return listingService.searchForAListing(make, model, year, color);
    }
}
