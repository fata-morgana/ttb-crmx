package me.kopkaj.ttb.cmrx.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import me.kopkaj.ttb.cmrx.constant.RequestChannel;
import me.kopkaj.ttb.cmrx.constant.RequestPriority;
import me.kopkaj.ttb.cmrx.constant.RequestStatus;
import me.kopkaj.ttb.cmrx.constant.RequestType;

@Data
@Entity
public class CustomerRequest {
	public CustomerRequest() {
		// default constructure for JPA
	}

	public CustomerRequest(Long id, Long customerId, String customerName, String customerAccountNumber, String content,
			String referenceNumber, String assignedTo, RequestPriority priority, RequestType type,
			RequestChannel channel, RequestStatus status, String notes, LocalDateTime createdDate,
			LocalDateTime lastModifiedDate, LocalDateTime completionDate) {
		super();
		this.id = id;
		this.customerId = customerId;
		this.customerName = customerName;
		this.customerAccountNumber = customerAccountNumber;
		this.content = content;
		this.referenceNumber = referenceNumber;
		this.assignedTo = assignedTo;
		this.priority = priority;
		this.type = type;
		this.channel = channel;
		this.status = status;
		this.notes = notes;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.completionDate = completionDate;
	}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_id")
    private Long customerId;
    
    @Column(name = "customer_name")
    private String customerName;
    
    @Column(name = "customer_account_number")
    private String customerAccountNumber;
    
    @Column(name = "content")
    private String content;
    
    @Column(name = "reference_number")
    private String referenceNumber;
    
    @Column(name = "assigned_to")
    private String assignedTo;
    
    @Enumerated(EnumType.STRING)
    private RequestPriority priority;
    
    @Enumerated(EnumType.STRING)
    private RequestType type;
    
    @Enumerated(EnumType.STRING)
    private RequestChannel channel;
    
    @Enumerated(EnumType.STRING)
    private RequestStatus status;
    
    private String notes;
    
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    
    @Column(name = "last_modified_date")
    private LocalDateTime lastModifiedDate;
    
    @Column(name = "completion_date")
    private LocalDateTime completionDate;
}
