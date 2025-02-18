package me.kopkaj.ttb.cmrx.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import me.kopkaj.ttb.cmrx.constant.RequestChannel;
import me.kopkaj.ttb.cmrx.constant.RequestPriority;
import me.kopkaj.ttb.cmrx.constant.RequestStatus;
import me.kopkaj.ttb.cmrx.constant.RequestType;
import me.kopkaj.ttb.cmrx.model.CustomerRequest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Transactional
public class CustomerRequestRepositoryTest {

    @Autowired
    private CustomerRequestRepository customerRequestRepository;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @PersistenceContext
    private EntityManager entityManager;

    private CustomerRequest request;

    @BeforeEach
    public void setUp() {
    	request = new CustomerRequest();
        request.setCustomerId(1001L);
        request.setCustomerName("Kopkaj Oupapatig");
        request.setCustomerAccountNumber("AcctNo11001100");
        request.setReferenceNumber("REF-001");
        request.setContent("Original content");
        request.setAssignedTo("EMP-100001");
        request.setPriority(RequestPriority.MEDIUM);
        request.setStatus(RequestStatus.PENDING);
        request.setNotes("Urgent request");
        request.setType(RequestType.ADDRESS_CHANGE);
        request.setChannel(RequestChannel.PHONE);
        
        customerRequestRepository.save(request);
    }

    @Test
    void testFindByCustomerId() {
        List<CustomerRequest> results = customerRequestRepository.findByCustomerId(1001L);
        assertThat(results).isNotEmpty();
        assertThat(results.get(0).getCustomerName()).isEqualTo("Kopkaj Oupapatig");
    }

    @Test
    void testUpdateRequest() {
        CustomerRequest request = customerRequestRepository.findByCustomerId(1001L).get(0);

        int updatedRows = customerRequestRepository.updateRequest(
                request.getId(), "Updated content", "EMP-100002", RequestPriority.HIGH, 
                RequestStatus.IN_PROGRESS, "Updated notes"
        );
        assertThat(updatedRows).isEqualTo(1);

        CustomerRequest updatedRequest = customerRequestRepository.findById(request.getId()).orElseThrow();
        entityManager.refresh(updatedRequest);
        assertThat(updatedRequest.getContent()).isEqualTo("Updated content");
        assertThat(updatedRequest.getAssignedTo()).isEqualTo("EMP-100002");
        assertThat(updatedRequest.getPriority()).isEqualTo(RequestPriority.HIGH);
        assertThat(updatedRequest.getStatus()).isEqualTo(RequestStatus.IN_PROGRESS);
        assertThat(updatedRequest.getNotes()).isEqualTo("Updated notes");
        assertThat(updatedRequest.getLastModifiedDate()).isNotNull(); // Auto-set by DB
    }

    @Test
    void testCompleteRequest() {
        CustomerRequest request = customerRequestRepository.findByCustomerId(1001L).get(0);

        int completedRows = customerRequestRepository.completeRequest(
                request.getId(), "Final content", "EMP-100003", RequestPriority.LOW, 
                RequestStatus.COMPLETED, "Final notes"
        );

        assertThat(completedRows).isEqualTo(1);

        CustomerRequest completedRequest = customerRequestRepository.findById(request.getId()).orElseThrow();
        entityManager.refresh(completedRequest);
        assertThat(completedRequest.getCompletionDate()).isNotNull(); // Auto-set by DB
    }

    @Test
    void testCompleteRequestAlreadyCompleted() {
        CustomerRequest request = customerRequestRepository.findByCustomerId(1001L).get(0);

        // First completion should succeed
        int firstCompletion = customerRequestRepository.completeRequest(
                request.getId(), "Final content", "EMP-100003", RequestPriority.LOW, 
                RequestStatus.COMPLETED, "Final notes"
        );

        assertThat(firstCompletion).isEqualTo(1);

        // Second attempt should fail as it is already completed
        int secondCompletion = customerRequestRepository.completeRequest(
                request.getId(), "Another update", "EMP-100004", RequestPriority.MEDIUM, 
                RequestStatus.COMPLETED, "Should fail"
        );

        assertThat(secondCompletion).isEqualTo(0);
    }
}
