package car.hey.listings.service.impl;

import car.hey.listings.exceptions.HeycarException;
import car.hey.listings.model.CSVListing;
import car.hey.listings.model.Listing;
import car.hey.listings.service.ConversionService;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ConversionServiceImpl implements ConversionService {
    public static final float HP_TO_KILOWATTS_TO_QUOTIENT = 0.746f;

    @Override
    public List<Listing> convertCSVToListings(MultipartFile multipartFile,
                                              int dealerId) throws HeycarException {
        try (Reader reader =
                     new BufferedReader(new InputStreamReader(multipartFile.getInputStream()))) {

            CsvToBean<CSVListing> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(CSVListing.class)
                    .withSkipLines(1)
                    .withSeparator(',')
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            List<CSVListing> csvListings = csvToBean.parse();

            List<Listing> listings = new ArrayList<Listing>();

            csvListings.forEach((csvListing) ->
                    listings.add(convertCSVListingToListing(csvListing,
                            dealerId)));

            return listings;

        } catch (Exception ex) {

            log.error("CSV parse error", ex);

            throw new HeycarException("HEY.CSV_ERROR", "Problem encountered " +
                    "while converting the csv file to listings : " + ex.getMessage());
        }
    }

    @Override
    public Listing convertCSVListingToListing(CSVListing csvListing,
                                              int dealerId) {
        return Listing.builder().code(csvListing.getCode()).color(csvListing.getColor()
        ).make(getMakeFromMakeModel(csvListing.getMakeModel())).model(csvListing.getMakeModel()).
                year(csvListing.getYear()).dealer(dealerId).
                price(csvListing.getPrice()).powerInHP(csvListing.getPowerInPs()).
                powerInKW(convertHPToKW(csvListing.getPowerInPs())).build();
    }

    @Override
    public String getMakeFromMakeModel(String makeModel) {
        int separatorIndex = makeModel.indexOf("/");
        if (separatorIndex != -1) {
            return makeModel.substring(0, separatorIndex);
        } else {
            return "";
        }
    }

    @Override
    public String getModelFromMakeModel(String makeModel) {
        int separatorIndex = makeModel.indexOf("/");
        if (separatorIndex != -1) {
            return makeModel.substring(separatorIndex);
        } else {
            return "";
        }
    }

    @Override
    public Float convertHPToKW(Float hp) {
        return hp * HP_TO_KILOWATTS_TO_QUOTIENT;
    }

    @Override
    public Float convertKWToHP(Float kw) {
        return kw / HP_TO_KILOWATTS_TO_QUOTIENT;
    }

}
