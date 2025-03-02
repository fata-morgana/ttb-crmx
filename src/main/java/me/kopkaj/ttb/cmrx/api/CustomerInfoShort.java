package me.kopkaj.ttb.cmrx.api;

import lombok.Data;

@Data
public class CustomerInfoShort {

	public CustomerInfoShort(Long customerId, String customerAccountNumber, String customerFullName,
			String customerEmail) {
		this.customerId = customerId;
		this.customerAccountNumber = customerAccountNumber;
		this.customerFullName = customerFullName;
		this.customerEmail = customerEmail;
	}
	
	private Long customerId;
	
	private String customerAccountNumber;
	
	private String customerFullName;
	
	private String customerEmail;
}
