package sv.edu.udb.inventoryapi.controller;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import sv.edu.udb.inventoryapi.controller.response.ProductResponse;
import sv.edu.udb.inventoryapi.controller.request.ProductRequest;
import sv.edu.udb.inventoryapi.service.ProductService;
import java.util.List;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;


@RestController
@RequiredArgsConstructor
@RequestMapping(path = "products")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public List<ProductResponse> findAllProduct() {
        return productService.findAll();
    }

    @GetMapping(path = "{id}")
    public ProductResponse findProductById(@PathVariable(name = "id") final Long id) {
        return productService.findById(id);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public ProductResponse saveProduct(@Valid @RequestBody final ProductRequest request) {
        return productService.save(request);
    }

    @PutMapping(path = "{id}")
    public ProductResponse updateProduct(@PathVariable(name = "id") final Long id,
                                         @Valid @RequestBody final ProductRequest request) {
        return productService.update(id, request);
    }

    @DeleteMapping(path = "{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteProduct(@PathVariable(name = "id") final Long id) {
        productService.delete(id);
    }

}
