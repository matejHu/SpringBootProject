package sk.streetofcode.productordermanagement.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sk.streetofcode.productordermanagement.api.entity.Product;
@Repository
public interface ProductJpaRepository extends JpaRepository<Product, Long> {
}
