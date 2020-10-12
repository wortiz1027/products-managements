package co.edu.javeriana.products.application;

import co.edu.javeriana.products.domain.Product;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class ProductCommandServiceImpl implements ProductCommandService {
    private static final Logger LOG = LoggerFactory.getLogger(ProductCommandServiceImpl.class);

    /*@Override
    public String createProduct(ProductDto productDto) {
        try {
            LOG.info("1-PRODUCTO_ID >>> {}", productDto.getProductId());
            AddProductCommand command = new AddProductCommand(productDto.getProductId(),
                    productDto.getProductCode(),
                    productDto.getProductName(),
                    productDto.getProductDescription(),
                    productDto.getStartDate(),
                    productDto.getEndDate(),
                    productDto.getProductPrice(),
                    productDto.getOriginCity(),
                    productDto.getDestinationCity(),
                    productDto.getVendorId());
                    //productDto.getProductType());
            LOG.info("2-PRODUCTO_ID >>> {}", productDto.getProductId());
            return gateway.sendAndWait(command);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }*/

    @Override
    public CompletableFuture<String> createProduct(Product product) {
        return null;
    }
}
