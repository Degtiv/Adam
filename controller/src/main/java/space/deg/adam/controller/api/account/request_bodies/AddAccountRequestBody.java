package space.deg.adam.controller.api.account.request_bodies;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class AddAccountRequestBody {
    String username;
    String title;
    String description;
    String currency;
}
