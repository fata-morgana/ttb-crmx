package me.kopkaj.ttb.cmrx.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import me.kopkaj.ttb.cmrx.constant.RequestStatus;
import me.kopkaj.ttb.cmrx.model.CustomerRequest;
import me.kopkaj.ttb.cmrx.service.CustomerRequestService;

@ExtendWith(MockitoExtension.class)
class CustomerRequestControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CustomerRequestService customerRequestService;

    @InjectMocks
    private CustomerRequestController customerRequestController;

    private CustomerRequest mockCustomerRequest;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(customerRequestController).build();
        mockCustomerRequest = new CustomerRequest();
        mockCustomerRequest.setId(1L);
        mockCustomerRequest.setStatus(RequestStatus.PENDING);
        mockCustomerRequest.setCreatedDate(LocalDateTime.now());
        mockCustomerRequest.setLastModifiedDate(LocalDateTime.now());
        mockCustomerRequest.setCompletionDate(null);
        mockCustomerRequest.setNotes("Test request");
    }

    @Test
    void testGetRequestById_Success() throws Exception {
        when(customerRequestService.getRequestById(anyLong())).thenReturn(mockCustomerRequest);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customer-requests/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.status").value(RequestStatus.PENDING.toString()));
    }

    @Test
    void testGetRequestById_NotFound() throws Exception {
        when(customerRequestService.getRequestById(anyLong())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customer-requests/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateRequest_Success() throws Exception {
        when(customerRequestService.createRequest(any(CustomerRequest.class))).thenReturn(mockCustomerRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/customer-requests")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"notes\":\"Test request\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.notes").value("Test request"));
    }
    
    @Test
    void testUpdateRequest_Success() throws Exception {
    	mockCustomerRequest.setNotes("Updated request");
        when(customerRequestService.updateRequest(anyLong(), any(), any(), any(), any(), any())).thenReturn(mockCustomerRequest);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/customer-requests/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"notes\":\"Updated request\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.notes").value("Updated request"));
    }

    @Test
    void testCompleteRequest_Success() throws Exception {
    	mockCustomerRequest.setNotes("Completed request");
    	mockCustomerRequest.setStatus(RequestStatus.COMPLETED);
        when(customerRequestService.completeRequest(anyLong(), any(), any(), any(), any(), any())).thenReturn(mockCustomerRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/customer-requests/{id}/complete", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"notes\":\"Completed request\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(RequestStatus.COMPLETED.toString()));
    }

}
