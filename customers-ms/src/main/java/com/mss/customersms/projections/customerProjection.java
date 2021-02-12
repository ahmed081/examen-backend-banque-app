package com.mss.customersms.projections;

import com.mss.customersms.entities.Client;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "fullcustomer",types = Client.class)
public interface customerProjection {
    String getFullName();
}
