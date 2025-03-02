package me.kopkaj.ttb.cmrx.service;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import me.kopkaj.ttb.cmrx.api.CustomerInfoShort;
import me.kopkaj.ttb.cmrx.constant.RequestChannel;
import me.kopkaj.ttb.cmrx.constant.RequestPriority;
import me.kopkaj.ttb.cmrx.constant.RequestStatus;
import me.kopkaj.ttb.cmrx.constant.RequestType;
import me.kopkaj.ttb.cmrx.exception.CrmxBusinessConditionException;
import me.kopkaj.ttb.cmrx.exception.CrmxDataValidationException;
import me.kopkaj.ttb.cmrx.model.CustomerRequest;
import me.kopkaj.ttb.cmrx.repository.CustomerRequestRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerRequestService {
    private final CustomerRequestRepository customerRequestRepository;
    private static final Logger logger = LoggerFactory.getLogger(CustomerRequestService.class);

    public CustomerRequest createRequest(CustomerRequest request) {
        request.setCreatedDate(LocalDateTime.now());
        request.setLastModifiedDate(LocalDateTime.now());
        request.setStatus(RequestStatus.IN_PROGRESS); // Default status on creation
        return customerRequestRepository.save(request);
    }

    public CustomerRequest updateRequest(Long id, String requestContent, String assignedTo, RequestPriority priority,
                                         RequestStatus status, String notes) {
        CustomerRequest request = customerRequestRepository.findById(id)
                .orElseThrow(() -> CrmxBusinessConditionException.customerRequestNotFound(id));

        request.setContent(requestContent);
        request.setAssignedTo(assignedTo);
        request.setPriority(priority);
        request.setStatus(status);
        request.setNotes(notes);
        request.setLastModifiedDate(LocalDateTime.now());

        return customerRequestRepository.save(request);
    }

    public CustomerRequest completeRequest(Long id, String requestContent, String assignedTo, RequestPriority priority,
                                           RequestStatus status, String notes) {
        CustomerRequest request = customerRequestRepository.findById(id)
                .orElseThrow(() -> CrmxBusinessConditionException.customerRequestNotFound(id));

        if (request.getCompletionDate() != null) {
            throw CrmxBusinessConditionException.customerRequestAlreadyCompleted(id);
        }

        request.setAssignedTo(assignedTo);
        request.setContent(requestContent);
        request.setPriority(priority);
        request.setStatus(RequestStatus.COMPLETED);
        request.setNotes(notes);
        request.setCompletionDate(LocalDateTime.now());
        request.setLastModifiedDate(LocalDateTime.now());

        return customerRequestRepository.save(request);
    }

    public List<CustomerRequest> searchByCriteria(RequestPriority priority, RequestStatus status, RequestChannel channel, RequestType type) {
        if (priority == null && status == null && type == null && channel == null) {
            throw CrmxDataValidationException.missingConditonRequireFields("priority, status, type, channel");
        }
        return customerRequestRepository.findByRequestCriteria(priority, status, channel, type);
    }
    
    public CustomerRequest getRequestById(Long id) {
        return customerRequestRepository.findById(id)
                .orElseThrow(() -> CrmxBusinessConditionException.customerRequestNotFound(id));
    }    
    
    public List<CustomerRequest> searchByCreatedDate(LocalDateTime begin, LocalDateTime end) {
        return customerRequestRepository.findByCreatedDateBetween(begin, end);
    }

    public List<CustomerRequest> searchByLastModifiedDate(LocalDateTime begin, LocalDateTime end) {
        return customerRequestRepository.findByLastModifiedDateBetween(begin, end);
    }

    public List<CustomerRequest> searchByCompletionDate(LocalDateTime begin, LocalDateTime end) {
        return customerRequestRepository.findByCompletionDateBetween(begin, end);
    }

    public List<CustomerRequest> searchTasksAssignedToMe(String assignedTo) {
        return customerRequestRepository.findByAssignedTo(assignedTo);
    }

    public CustomerInfoShort fetchCustomerDetails(Long customerId, String customerAccountNumber) {
        logger.info("Fetching customer details for customerId: {} or accountNumber: {}", customerId, customerAccountNumber);
        // call to external customer microservice. the logic is omitted here.
        return new CustomerInfoShort(customerId, customerAccountNumber, "Bill Clinton", "bill.clinton@whitehouse.gov");
    }
}
