package com.mss.billing.fiegn;

import com.mss.billing.entities.Client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="CLIENT-SERVICE")
public interface ClientRestClient {
    @GetMapping(path="/clients/{id}")
    Client getCustomerById(@PathVariable(name = "code") Long id);

    @GetMapping(path="/clients")
    PagedModel<Client> pageClient(@RequestParam(name = "page")int page, @RequestParam(name = "size")int size);
}
