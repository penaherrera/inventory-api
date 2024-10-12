package sv.edu.udb.inventoryapi.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import sv.edu.udb.inventoryapi.repository.domain.Product;
import sv.edu.udb.inventoryapi.controller.response.ProductResponse;
import sv.edu.udb.inventoryapi.service.ProductService;
import java.time.LocalDate;
import java.util.List;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static
        org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebMvcTest(ProductController.class)
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ProductService productService;
    private Product product;
    @BeforeEach
    void init() {
        this.product = Product
                .builder()
                .id(1L)
                .nombre("testing product")
                .precio(1.50)
                .cantidad(5)
                .fechaExpiracion(LocalDate.of(2024, 12, 28))
                .build();
    }
    @Test
    @DisplayName("Get all product")
    void findAllProduct_when_performGetRequest() throws Exception {
        final List<ProductResponse> expectedList = List.of(ProductResponse
                .builder()
                .nombre(this.product.getNombre())
                .precio(this.product.getPrecio())
                .cantidad(this.product.getCantidad())
                .fechaExpiracion(this.product.getFechaExpiracion())
                .build());
       
        when(productService.findAll()).thenReturn(expectedList);
        this.mockMvc.perform(get("/products"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(expectedList)));
    }
}