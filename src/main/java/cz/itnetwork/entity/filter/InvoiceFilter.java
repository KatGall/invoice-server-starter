package cz.itnetwork.entity.filter;

import com.fasterxml.jackson.annotation.JsonFormat;
import cz.itnetwork.dto.PersonDTO;
import lombok.Data;

import java.time.LocalDate;

@Data
public class InvoiceFilter {

 //komentař
    //komentář2 
    private Long buyerID;
    private Long sellerID;
    private String product;
    private Integer minPrice;
    private Integer maxPrice;
    private Integer limit = 10;

}
