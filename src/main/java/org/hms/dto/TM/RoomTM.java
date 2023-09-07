package org.hms.dto.TM;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomTM {
    private String roomId;
    private String type;
    private Double keyMoney;
    private Integer qty;

}
