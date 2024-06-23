package sk.streetofcode.productordermanagement.api.request;

import sk.streetofcode.productordermanagement.api.entity.Product;

import java.util.List;

public interface ProductService {

    long addProduct(ProductAddRequest request);
    Product getProduct(Long id);
    List<Product> getAllProducts();
    void updateProduct(long id, ProductEditRequest request);
    void deleteProduct(long id);
    long getProductAmount(long id);
    void addProductAmount(long id, long amount);

}
