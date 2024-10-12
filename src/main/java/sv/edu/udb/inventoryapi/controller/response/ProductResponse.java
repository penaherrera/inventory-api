package sv.edu.udb.inventoryapi.controller.response;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import java.time.LocalDate;

@Getter
@Setter
@Builder(toBuilder = true)
@FieldNameConstants
public class ProductResponse {

    private String nombre;
    private double precio;
    private Integer cantidad;
    private LocalDate fechaExpiracion;

}
