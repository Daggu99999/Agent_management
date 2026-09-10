package AgentManagement.Controller;

import AgentManagement.Entity.InsuranceProduct;
import AgentManagement.Service.InsuranceProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class InsuranceProductController {

    private final InsuranceProductService productService;

    public InsuranceProductController(
            InsuranceProductService productService) {

        this.productService = productService;
    }



    @GetMapping
    public List<InsuranceProduct> getAllProducts() {

        return productService.getAllProducts();
    }

    @GetMapping("/{productId}")
    public InsuranceProduct getProductById(
            @PathVariable Long productId) {

        return productService.getProductById(productId);
    }

    @PutMapping("/{productId}")
    public InsuranceProduct updateProduct(
            @PathVariable Long productId,
            @RequestBody InsuranceProduct product) {

        return productService.updateProduct(
                productId,
                product);
    }
}