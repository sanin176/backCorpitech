package com.alex.Services;

import com.alex.Model.Customer;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {

    @Modifying
    @Transactional
    @Query(value = "SELECT COUNT(*) AS numberRecordsCustomers FROM customer c", nativeQuery = true)
    List<NumberRecordsCustomers> numberRecordsCustomers();

    interface NumberRecordsCustomers {
        int getNumberRecordsCustomers();
    }

    @Modifying
    @Transactional
    @Query(value = "SELECT c.id as id, c.organization as organization, c.fio as fio, c.email as email, c.date_to as dateTo, c.date_from as dateFrom, " +
            "c.max_seat_count as maxSeatCount,(c.max_seat_count - (IFNULL((SELECT SUM(cs.is_active) FROM customer_seat cs WHERE c.id = cs.customer_id " +
            "AND cs.is_active GROUP BY cs.is_active), 0))) as used FROM customer c LIMIT ?1, ?2"
            , nativeQuery = true)
    List<CustomerServ> findAllSomeField(int idFrom, int idTo);

    interface CustomerServ {
        int getId();
        String getOrganization();
        String getFio();
        String getEmail();
        LocalDate getDateTo();
        LocalDate getDateFrom();
        int getMaxSeatCount();
        int getUsed();
    }

    @Modifying
    @Transactional
    @Query(value = "SELECT c.license_key as licenseKey, c.tmapi_email as tmapiEmail, c.tmapi_password as tmapiPassword, " +
            "c.tmapi_limit_key as tmapiLimitKey FROM customer c " +
            "WHERE c.email = ?1 AND c.fio = ?2 AND c.organization = ?3 AND c.max_seat_count = ?4 AND c.max_workstation_count = ?5 AND c.note = ?6"
            , nativeQuery = true)
    List<CustomerLicenseKey> findDemoInfo(String email, String name, String organization,
                                          int seatcount, int worstationcount, String note);

    @Modifying
    @Transactional
    @Query(value = "SELECT c.license_key as licenseKey, c.tmapi_email as tmapiEmail, c.tmapi_password as tmapiPassword, " +
            "c.tmapi_limit_key as tmapiLimitKey FROM customer c"
            , nativeQuery = true)
    List<CustomerLicenseKey> findInfoByicenseKey(String licenseKey);

    interface CustomerLicenseKey{
        String getLicenseKey();
        String getTmapiEmail();
        String getTmapiPassword();
        String getTmapiLimitKey();
    }


    @Modifying
    @Transactional
    @Query(value = "SELECT c.is_active as status FROM customer c " +
            "WHERE c.license_key = ?1 AND c.email = ?2 AND c.fio = ?3"
            , nativeQuery = true)
    List<CustomerStatus> findStatusByInfo(String licenseKey, String email, String fio);

    interface CustomerStatus{
        String getStatus();
    }
}
