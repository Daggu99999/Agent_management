package AgentManagement.ServiceImpl;

import AgentManagement.Entity.InsuranceProduct;
import AgentManagement.Repository.InsuranceProductRepository;
import AgentManagement.Service.InsuranceProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InsuranceProductServiceImpl
        implements InsuranceProductService {

    private final InsuranceProductRepository productRepository;

    public InsuranceProductServiceImpl(
            InsuranceProductRepository productRepository) {

        this.productRepository = productRepository;
    }

    @Override
    public InsuranceProduct createProduct(
            InsuranceProduct product) {

        product.setActive(true);

        return productRepository.save(product);
    }

    @Override
    public List<InsuranceProduct> getAllProducts() {

        return productRepository.findAll();
    }

    @Override
    public InsuranceProduct getProductById(
            Long productId) {

        return productRepository.findById(productId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Product not found"));
    }

    @Override
    public InsuranceProduct updateProduct(
            Long productId,
            InsuranceProduct product) {

        InsuranceProduct existingProduct =
                productRepository.findById(productId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Product not found"));

        existingProduct.setProductName(
                product.getProductName());

        existingProduct.setPolicyType(
                product.getPolicyType());

        existingProduct.setBasePremium(
                product.getBasePremium());

        existingProduct.setCoverageAmount(
                product.getCoverageAmount());

        existingProduct.setDescription(
                product.getDescription());

        existingProduct.setActive(
                product.isActive());

        return productRepository.save(
                existingProduct);
    }
}