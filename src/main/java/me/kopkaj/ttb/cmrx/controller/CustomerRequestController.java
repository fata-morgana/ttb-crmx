package me.kopkaj.ttb.cmrx.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.kopkaj.ttb.cmrx.api.CustomerInfoShort;
import me.kopkaj.ttb.cmrx.model.CustomerRequest;
import me.kopkaj.ttb.cmrx.service.CustomerRequestService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/customer-requests")
@Tag(name = "Customer Requests")
public class CustomerRequestController {

	private final CustomerRequestService customerRequestService;

	@GetMapping("/{id}")
	@Operation(summary = "Get Customer Request by ID")
	public ResponseEntity<CustomerRequest> getRequestById(
			@Parameter(description = "ID of the customer request", required = true) @PathVariable Long id) {
		CustomerRequest customerRequest = customerRequestService.getRequestById(id);
		return customerRequest != null ? ResponseEntity.ok(customerRequest) : ResponseEntity.notFound().build();
	}

	@GetMapping("/created-date-range")
	@Operation(summary = "Search Customer Requests by Date Range")
	public ResponseEntity<List<CustomerRequest>> getRequestsByDateRange(
			@Parameter(description = "Start date of the range", required = true) @RequestParam("startDate") String startDateStr,
			@Parameter(description = "End date of the range", required = true) @RequestParam("endDate") String endDateStr) {
		LocalDateTime startDate = LocalDateTime.parse(startDateStr);
		LocalDateTime endDate = LocalDateTime.parse(endDateStr);
		List<CustomerRequest> requests = customerRequestService.searchByCreatedDate(startDate, endDate);
		return requests.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(requests);
	}

	@GetMapping("/last-modified-range")
	@Operation(summary = "Search Customer Requests by Last Modified Date Range")
	public ResponseEntity<List<CustomerRequest>> getRequestsByLastModifiedDateRange(
			@Parameter(description = "Start date of the range", required = true) @RequestParam("startDate") String startDateStr,
			@Parameter(description = "End date of the range", required = true) @RequestParam("endDate") String endDateStr) {
		LocalDateTime startDate = LocalDateTime.parse(startDateStr);
		LocalDateTime endDate = LocalDateTime.parse(endDateStr);
		List<CustomerRequest> requests = customerRequestService.searchByLastModifiedDate(startDate, endDate);
		return requests.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(requests);
	}

	@GetMapping("/completion-range")
	@Operation(summary = "Search Customer Requests by Completion Date Range")
	public ResponseEntity<List<CustomerRequest>> getRequestsByCompletionDateRange(
			@Parameter(description = "Start date of the range", required = true) @RequestParam("startDate") String startDateStr,
			@Parameter(description = "End date of the range", required = true) @RequestParam("endDate") String endDateStr) {
		LocalDateTime startDate = LocalDateTime.parse(startDateStr);
		LocalDateTime endDate = LocalDateTime.parse(endDateStr);
		List<CustomerRequest> requests = customerRequestService.searchByCompletionDate(startDate, endDate);
		return requests.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(requests);
	}

	@GetMapping("/assigned-to/{employeeId}")
	@Operation(summary = "Search Task Assigned to Me")
	public ResponseEntity<List<CustomerRequest>> searchTaskAssignedToMe(
			@Parameter(description = "Assigned to user ID", required = true) @PathVariable String employeeId) {
		List<CustomerRequest> requests = customerRequestService.searchTasksAssignedToMe(employeeId);
		return requests.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(requests);
	}

	@PostMapping
	@Operation(summary = "Create New Customer Request")
	public ResponseEntity<CustomerRequest> createRequest(@Valid @RequestBody CustomerRequest customerRequest) {
		CustomerRequest createdRequest = customerRequestService.createRequest(customerRequest);
		return new ResponseEntity<>(createdRequest, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	@Operation(summary = "Update Customer Request")
	public ResponseEntity<CustomerRequest> updateRequest(
			@Parameter(description = "ID of the customer request to be updated", required = true) @PathVariable Long id,
			@RequestBody CustomerRequest customerRequest) {
		CustomerRequest updatedRequest = customerRequestService.updateRequest(id, customerRequest.getContent(),
				customerRequest.getAssignedTo(), customerRequest.getPriority(), customerRequest.getStatus(),
				customerRequest.getNotes());
		return ResponseEntity.ok(updatedRequest);
	}

	@PostMapping("/{id}/complete")
	@Operation(summary = "Complete Customer Request")
	public ResponseEntity<CustomerRequest> completeRequest(
			@Parameter(description = "ID of the customer request to be completed", required = true) @PathVariable Long id,
			@Valid @RequestBody CustomerRequest customerRequest) {
		CustomerRequest completedRequest = customerRequestService.completeRequest(id, customerRequest.getContent(),
				customerRequest.getAssignedTo(), customerRequest.getPriority(), customerRequest.getStatus(),
				customerRequest.getNotes());
		return ResponseEntity.ok(completedRequest);
	}

	@Operation(summary = "Fetch Customer Details by ID or Account Number")
	@GetMapping("/customer-details")
	public ResponseEntity<CustomerInfoShort> fetchCustomerDetails(
			@Parameter(description = "ID of the customer", required = true) @RequestParam Long customerId,
			@Parameter(description = "Account number of the customer", required = true) @RequestParam String customerAccountNumber) {
		CustomerInfoShort customerDetails = customerRequestService.fetchCustomerDetails(customerId,
				customerAccountNumber);
		if (customerDetails == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(customerDetails);
	}

}
