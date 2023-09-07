package org.hms.dto.TM;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentTM {
    private String studentID;
    private String name;
    private String address;
    private String dbo;
    private String contact;
    private String gender;

}
