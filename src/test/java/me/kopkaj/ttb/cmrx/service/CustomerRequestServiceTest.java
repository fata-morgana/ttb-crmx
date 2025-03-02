package me.kopkaj.ttb.cmrx.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import me.kopkaj.ttb.cmrx.constant.RequestPriority;
import me.kopkaj.ttb.cmrx.constant.RequestStatus;
import me.kopkaj.ttb.cmrx.exception.CrmxBusinessConditionException;
import me.kopkaj.ttb.cmrx.model.CustomerRequest;
import me.kopkaj.ttb.cmrx.repository.CustomerRequestRepository;

@ExtendWith(MockitoExtension.class)
class CustomerRequestServiceTest {
    
    @Mock
    private CustomerRequestRepository customerRequestRepository;
    
    @InjectMocks
    private CustomerRequestService customerRequestService;
    
    private CustomerRequest mockRequest;
    
    @BeforeEach
    void setUp() {
        mockRequest = new CustomerRequest();
        mockRequest.setId(1L);
        mockRequest.setContent("Initial content");
        mockRequest.setAssignedTo("User008");
        mockRequest.setPriority(RequestPriority.HIGH);
        mockRequest.setStatus(RequestStatus.IN_PROGRESS);
        mockRequest.setNotes("Initial note");
        mockRequest.setCreatedDate(LocalDateTime.now());
        mockRequest.setLastModifiedDate(LocalDateTime.now());
    }
    
    @Test
    void testGetRequestById() {
        when(customerRequestRepository.findById(1L)).thenReturn(Optional.of(mockRequest));

        CustomerRequest result = customerRequestService.getRequestById(1L);

        assertNotNull(result);
        assertEquals(result.getId(), 1L);
        verify(customerRequestRepository, times(1)).findById(1L);
    }
    
    @Test
    void createRequest_ShouldSaveAndReturnRequest() {
        when(customerRequestRepository.save(any(CustomerRequest.class))).thenReturn(mockRequest);
        
        CustomerRequest createdRequest = customerRequestService.createRequest(mockRequest);
        
        assertNotNull(createdRequest);
        assertEquals(mockRequest.getContent(), createdRequest.getContent());
        verify(customerRequestRepository, times(1)).save(any(CustomerRequest.class));
    }
    
    @Test
    void updateRequest_ShouldModifyAllowedFields() {
        when(customerRequestRepository.findById(1L)).thenReturn(Optional.of(mockRequest));
        when(customerRequestRepository.save(any(CustomerRequest.class))).thenReturn(mockRequest);
        
        CustomerRequest updatedRequest = customerRequestService.updateRequest(1L, "Updated content", "User008", RequestPriority.LOW, RequestStatus.COMPLETED, "Updated notes");
        
        assertEquals("Updated content", updatedRequest.getContent());
        assertEquals("User008", updatedRequest.getAssignedTo());
        assertEquals(RequestPriority.LOW, updatedRequest.getPriority());
        assertEquals(RequestStatus.COMPLETED, updatedRequest.getStatus());
        assertEquals("Updated notes", updatedRequest.getNotes());
        verify(customerRequestRepository, times(1)).save(any(CustomerRequest.class));
    }
    
    @Test
    void completeRequest_ShouldThrowExceptionIfAlreadyCompleted() {
        mockRequest.setCompletionDate(LocalDateTime.now());
        when(customerRequestRepository.findById(1L)).thenReturn(Optional.of(mockRequest));
        
        CrmxBusinessConditionException exception = assertThrows(CrmxBusinessConditionException.class, () ->
            customerRequestService.completeRequest(1L, "Completed content", "User008", RequestPriority.MEDIUM, RequestStatus.COMPLETED, "Final notes")
        );
        
        assertEquals("B1002", exception.getCode());
        verify(customerRequestRepository, never()).save(any(CustomerRequest.class));
    }
    
    @Test
    void getRequestById_ShouldReturnRequestIfExists() {
        when(customerRequestRepository.findById(1L)).thenReturn(Optional.of(mockRequest));
        
        CustomerRequest result = customerRequestService.getRequestById(1L);
        
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(customerRequestRepository, times(1)).findById(1L);
    }
    
    @Test
    void searchByPriority_ShouldReturnMatchingRequests() {
        List<CustomerRequest> requests = List.of(mockRequest);
        when(customerRequestRepository.findByRequestCriteria(RequestPriority.HIGH, null, null, null)).thenReturn(requests);
        
        List<CustomerRequest> result = customerRequestService.searchByCriteria(RequestPriority.HIGH, null, null, null);
        
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(customerRequestRepository, times(1)).findByRequestCriteria(RequestPriority.HIGH, null, null, null);
    }
    
    @Test
    void searchByCreatedDate_ShouldReturnMatchingRequests() {
        LocalDateTime start = LocalDateTime.now().minusDays(1);
        LocalDateTime stop = LocalDateTime.now();
        List<CustomerRequest> requests = List.of(mockRequest);
        when(customerRequestRepository.findByCreatedDateBetween(start, stop)).thenReturn(requests);
        
        List<CustomerRequest> result = customerRequestService.searchByCreatedDate(start, stop);
        
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(customerRequestRepository, times(1)).findByCreatedDateBetween(start, stop);
    }
    
    @Test
    void testGetRequestsByDateRange() {
        LocalDateTime startDate = LocalDateTime.now().minusDays(1);
        LocalDateTime endDate = LocalDateTime.now().plusDays(1);
        when(customerRequestRepository.findByCreatedDateBetween(startDate, endDate))
                .thenReturn(List.of(mockRequest));

        List<CustomerRequest> result = customerRequestService.searchByCreatedDate(startDate, endDate);

        assertFalse(result.isEmpty());
        assertEquals(result.size(), 1);
        verify(customerRequestRepository, times(1)).findByCreatedDateBetween(startDate, endDate);
    }

    @Test
    void testGetRequestsByLastModifiedDateRange() {
        LocalDateTime startDate = LocalDateTime.now().minusDays(1);
        LocalDateTime endDate = LocalDateTime.now().plusDays(1);
        when(customerRequestRepository.findByLastModifiedDateBetween(startDate, endDate))
                .thenReturn(List.of(mockRequest));

        List<CustomerRequest> result = customerRequestService.searchByLastModifiedDate(startDate, endDate);

        assertFalse(result.isEmpty());
        assertEquals(result.size(), 1);
        verify(customerRequestRepository, times(1)).findByLastModifiedDateBetween(startDate, endDate);
    }

    @Test
    void testGetRequestsByCompletionDateRange() {
        LocalDateTime startDate = LocalDateTime.now().minusDays(1);
        LocalDateTime endDate = LocalDateTime.now().plusDays(1);
        when(customerRequestRepository.findByCompletionDateBetween(startDate, endDate))
                .thenReturn(List.of(mockRequest));

        List<CustomerRequest> result = customerRequestService.searchByCompletionDate(startDate, endDate);

        assertFalse(result.isEmpty());
        assertEquals(result.size(), 1);
        verify(customerRequestRepository, times(1)).findByCompletionDateBetween(startDate, endDate);
    }
}

