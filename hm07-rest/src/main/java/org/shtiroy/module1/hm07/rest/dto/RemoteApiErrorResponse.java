package org.shtiroy.module1.hm07.rest.dto;

import java.time.LocalDateTime;
import java.util.List;

public record RemoteApiErrorResponse(LocalDateTime timestamp,
                                     int status,
                                     String error,
                                     String service,
                                     List<String> details) {
}
