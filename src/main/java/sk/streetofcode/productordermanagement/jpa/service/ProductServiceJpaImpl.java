package sk.streetofcode.productordermanagement.jpa.service;

import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import sk.streetofcode.productordermanagement.api.exception.BadRequestException;
import sk.streetofcode.productordermanagement.api.exception.InternalErrorException;
import sk.streetofcode.productordermanagement.api.exception.ResourceNotFoundException;
import sk.streetofcode.productordermanagement.api.request.ProductAddRequest;
import sk.streetofcode.productordermanagement.api.request.ProductEditRequest;
import sk.streetofcode.productordermanagement.api.request.ProductService;
import sk.streetofcode.productordermanagement.api.entity.Product;
import sk.streetofcode.productordermanagement.jpa.repository.ProductJpaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceJpaImpl implements ProductService {
    private ProductJpaRepository repository;
    private static Logger logger = LoggerFactory.getLogger(ProductServiceJpaImpl.class);

    public ProductServiceJpaImpl(ProductJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public long addProduct(ProductAddRequest request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setAmount(request.getAmount());
        product.setPrice(request.getPrice());

        Product savedProduct = repository.save(product);
        return savedProduct.getId();
    }

    @Override
    public Product getProduct(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));
    }

    @Override
    public List<Product> getAllProducts() {
        return repository.findAll().stream().map(this::mapProduct).toList();
    }

    @Override
    public void updateProduct(long id, ProductEditRequest request) {
        final Product productEntity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));
        productEntity.setName(request.getName());
        productEntity.setDescription(request.getDescription());
        repository.save(productEntity);
    }

    @Override
    public void deleteProduct(long id) {
        if (this.getProduct(id) != null){
            repository.deleteById(id);
        }
    }

    @Override
    public long getProductAmount(long id) {
        return repository.findById(id)
                .map(Product::getAmount)
                .orElse(0L);
    }
    @Override
    public void addProductAmount(long id, long amount) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));
        product.setAmount(product.getAmount() + amount);
        repository.save(product);
    }

    private Product mapProduct(Product product){
        return new Product(product.getId(), product.getName(), product.getDescription(), product.getAmount(), product.getPrice());
    }
}
