package me.kopkaj.ttb.cmrx.repository;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import me.kopkaj.ttb.cmrx.constant.RequestChannel;
import me.kopkaj.ttb.cmrx.constant.RequestPriority;
import me.kopkaj.ttb.cmrx.constant.RequestStatus;
import me.kopkaj.ttb.cmrx.constant.RequestType;
import me.kopkaj.ttb.cmrx.exception.CrmxBusinessCriteriaException;
import me.kopkaj.ttb.cmrx.exception.CrmxDataValidationException;
import me.kopkaj.ttb.cmrx.exception.ErrorCode;
import me.kopkaj.ttb.cmrx.model.CustomerRequest;

public class CustomerRequestSpecification {
	private static final Logger logger = LoggerFactory.getLogger(CustomerRequestSpecification.class);

    
    public static Specification<CustomerRequest> filterByRequestCriteria(
            RequestPriority priority, RequestStatus status, RequestChannel channel, RequestType type) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (priority != null) {
                predicates.add(cb.equal(root.get("priority"), priority));
            }
            if (status != null) {
                predicates.add(cb.equal(root.get("status"), status));
            }
            if (channel != null) {
                predicates.add(cb.equal(root.get("channel"), channel));
            }
            if (type != null) {
                predicates.add(cb.equal(root.get("type"), type));
            }
            // Ensure at least one filter is provided
            if (predicates.isEmpty()) {
            	throw CrmxDataValidationException.missingConditonRequireFields("priority, status, type, channel");
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
