package sv.edu.udb.inventoryapi.service.mapper;
import org.mapstruct.Mapper;
import sv.edu.udb.inventoryapi.controller.request.ProductRequest;
import sv.edu.udb.inventoryapi.controller.response.ProductResponse;
import sv.edu.udb.inventoryapi.repository.domain.Product;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductResponse toProductResponse(final Product data);

    List<ProductResponse> toProductResponseList(final List<Product> productList);

    Product toProduct(final ProductRequest productRequest);

}