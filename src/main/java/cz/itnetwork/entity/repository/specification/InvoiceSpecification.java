package cz.itnetwork.entity.repository.specification;

import cz.itnetwork.entity.InvoiceEntity;
import cz.itnetwork.entity.InvoiceEntity_;
import cz.itnetwork.entity.PersonEntity;
import cz.itnetwork.entity.PersonEntity_;
import cz.itnetwork.entity.filter.InvoiceFilter;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class InvoiceSpecification implements Specification<InvoiceEntity> {
    private final InvoiceFilter filter;


    @Override
    public Predicate toPredicate(Root<InvoiceEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        // Filtr podle buyerID
        if (filter.getBuyerID() != null) {
            Join<PersonEntity, InvoiceEntity> buyerJoin = root.join(InvoiceEntity_.BUYER);
            predicates.add(criteriaBuilder.equal(buyerJoin.get(PersonEntity_.ID), filter.getBuyerID()));
        }
        // Filtr podle sellerID
        if (filter.getSellerID() != null) {
            Join<PersonEntity, InvoiceEntity> sellerJoin = root.join(InvoiceEntity_.SELLER);
            predicates.add(criteriaBuilder.equal(sellerJoin.get(PersonEntity_.ID), filter.getSellerID()));
        }
        // Filtr podle produktu
        if (filter.getProduct() != null) {
            String productPattern = "%" + filter.getProduct() + "%";
            Predicate productPredicate = criteriaBuilder.like(root.get(InvoiceEntity_.PRODUCT), productPattern);
            predicates.add(productPredicate);
        }
        // Filtr podle minPrice
        if (filter.getMinPrice() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(InvoiceEntity_.PRICE), filter.getMinPrice()));
        }
        // Filtr podle maxPrice
        if (filter.getMaxPrice() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(InvoiceEntity_.PRICE), filter.getMaxPrice()));
        }
        // Filtr podle roku
        if (filter.getYear() != null) {
            LocalDate startOfYear = LocalDate.of(filter.getYear(), 1, 1);
            LocalDate endOfYear = LocalDate.of(filter.getYear(), 12, 31);

            Predicate yearPredicate = criteriaBuilder.between(root.get(InvoiceEntity_.ISSUED), startOfYear, endOfYear);
            predicates.add(yearPredicate);
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}