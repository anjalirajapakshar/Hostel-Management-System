package org.hms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDTO {
    private String reservationId;
    private String date;
    private String roomId;
    private String studentId;
    private String status;
}
