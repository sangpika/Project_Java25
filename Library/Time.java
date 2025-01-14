package Library;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Time {

  protected static String getTime() {
    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    return now.format(formatter);
  }
}
