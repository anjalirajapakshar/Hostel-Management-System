package org.hms.dto.TM;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTM {
    private String userID;
    private String userName;
    private String password;
    private String email;
}
