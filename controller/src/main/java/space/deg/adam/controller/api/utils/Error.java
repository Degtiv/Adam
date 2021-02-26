package space.deg.adam.controller.api.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import space.deg.adam.controller.api.constants.Constants;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Builder
@AllArgsConstructor
public class Error {
    String message;
    String dateTime;
    String solution = Constants.CONTACT_TECH_SUPPORT_MESSAGE;

    public Error() {
        this.dateTime = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    public Error(String message) {
        this();
        this.message = message;
    }

    public Error(String message, String solution) {
        this(message);
        this.solution = solution;
    }
}
