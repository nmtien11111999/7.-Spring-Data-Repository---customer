package customer.repository;

import customer.models.Customer;
import customer.models.Province;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface IProvinceRepository extends JpaRepository<Province, Long> {
}
