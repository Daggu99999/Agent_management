package AgentManagement.Service;

import AgentManagement.Entity.InsuranceProduct;

import java.util.List;

public interface InsuranceProductService {

    InsuranceProduct createProduct(
            InsuranceProduct product);

    List<InsuranceProduct> getAllProducts();

    InsuranceProduct getProductById(
            Long productId);

    InsuranceProduct updateProduct(
            Long productId,
            InsuranceProduct product);
}