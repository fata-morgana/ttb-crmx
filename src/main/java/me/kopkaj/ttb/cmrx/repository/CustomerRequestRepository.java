package me.kopkaj.ttb.cmrx.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import me.kopkaj.ttb.cmrx.constant.RequestChannel;
import me.kopkaj.ttb.cmrx.constant.RequestPriority;
import me.kopkaj.ttb.cmrx.constant.RequestStatus;
import me.kopkaj.ttb.cmrx.constant.RequestType;
import me.kopkaj.ttb.cmrx.model.CustomerRequest;

public interface CustomerRequestRepository extends JpaRepository<CustomerRequest, Long> {
    List<CustomerRequest> findByStatus(RequestStatus status);
    List<CustomerRequest> findByChannel(RequestChannel channel);
    List<CustomerRequest> findByType(RequestType type);
    List<CustomerRequest> findByPriority(RequestPriority priority);

    List<CustomerRequest> findByCustomerId(Long customerId);
    List<CustomerRequest> findByCustomerAccountNumber(String accountNumber);
    List<CustomerRequest> findByReferenceNumber(String referenceNumber);
    List<CustomerRequest> findByCustomerNameContainingIgnoreCase(String customerName);
    
    List<CustomerRequest> findByAssignedTo(String assignedTo);

    List<CustomerRequest> findByCreatedDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<CustomerRequest> findByLastModifiedDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<CustomerRequest> findByCompletionDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    // Partial Update: Modify only allowed fields
    @Modifying
    @Transactional
    @Query("""
        UPDATE CustomerRequest cr SET 
            cr.content = :content,
            cr.assignedTo = :assignedTo,
            cr.priority = :priority,
            cr.status = :status,
            cr.notes = :notes,
            cr.lastModifiedDate = CURRENT_TIMESTAMP
        WHERE cr.id = :id
    """)
    int updateRequest(Long id, String content, String assignedTo, RequestPriority priority, 
                      RequestStatus status, String notes);

    // Mark request as completed (completionDate and lastModifiedDate set by DB)
    @Modifying
    @Transactional
    @Query("""
        UPDATE CustomerRequest cr SET 
            cr.content = :content,
            cr.assignedTo = :assignedTo,
            cr.priority = :priority,
            cr.status = :status,
            cr.notes = :notes,
            cr.completionDate = CURRENT_TIMESTAMP,
            cr.lastModifiedDate = CURRENT_TIMESTAMP
        WHERE cr.id = :id AND cr.completionDate IS NULL
    """)
    int completeRequest(Long id, String content, String assignedTo, RequestPriority priority, 
                        RequestStatus status, String notes);

}
