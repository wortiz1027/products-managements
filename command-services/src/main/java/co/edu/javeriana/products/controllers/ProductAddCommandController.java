package co.edu.javeriana.products.controllers;

import co.edu.javeriana.products.commands.AddProductCommand;
import co.edu.javeriana.products.repository.entities.Products;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProductAddCommandController {

    private final CommandGateway commandGateway;

    @PostMapping("/products")
    public void handle(@RequestBody Products data) {
        AddProductCommand cmd = new AddProductCommand(
                                            data.getProductId(),
                                            data.getProductCode(),
                                            data.getProductName(),
                                            data.getProductDescription(),
                                            data.getStartDate(),
                                            data.getEndDate(),
                                            data.getProductPrice(),
                                            data.getOriginCity(),
                                            data.getDestinationCity(),
                                            data.getVendorId(),
                                            data.getProductType()
                                    );
        commandGateway.sendAndWait(cmd);
    }

}
