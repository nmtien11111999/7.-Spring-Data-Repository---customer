package customer.services;

import customer.models.Customer;
import customer.models.Province;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ICustomerService extends IGenerateService<Customer>{
    Iterable<Customer> findAllByProvince(Province province);

    Page<Customer> findAll(Pageable pageable);

    Page<Customer> findAllByNameContaining(Pageable pageable, String name);
}
