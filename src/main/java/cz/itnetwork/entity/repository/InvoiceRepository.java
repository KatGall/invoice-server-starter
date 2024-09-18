package cz.itnetwork.entity.repository;

import cz.itnetwork.entity.InvoiceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface InvoiceRepository extends PagingAndSortingRepository<InvoiceEntity, Long>, JpaRepository<InvoiceEntity, Long> {

    Page<InvoiceEntity> findAll(Specification<InvoiceEntity>invoiceSpecification, Pageable pageable);

    @Query("SELECT SUM(i.price) FROM invoice i WHERE FUNCTION('YEAR', i.issued) = FUNCTION('YEAR', CURRENT_DATE)")
    Long getCurrentYearSum();

    @Query("SELECT SUM(i.price) FROM invoice i")
    Long getAllTimeSum();

    @Query("SELECT COUNT(i) FROM invoice i")
    Long getInvoicesCount();
    }
