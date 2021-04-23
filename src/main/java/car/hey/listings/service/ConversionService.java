package car.hey.listings.service;

import car.hey.listings.exceptions.HeycarException;
import car.hey.listings.model.CSVListing;
import car.hey.listings.model.Listing;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ConversionService {

    public Listing convertCSVListingToListing(CSVListing csvListing,
                                              int dealerId);

    public List<Listing> convertCSVToListings(MultipartFile multipartFile,
                                              int dealerId) throws HeycarException;

    public Float convertHPToKW(Float hp);

    public Float convertKWToHP(Float kw);

    public String getMakeFromMakeModel(String makeModel) ;

    public String getModelFromMakeModel(String makeModel);
}
