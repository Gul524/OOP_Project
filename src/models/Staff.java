package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;
import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Staff {

    private enum EmployeeRoles {
        Manager,
        Waiter,
        Cashier,
        Cook,
    }

    @JsonProperty(access = WRITE_ONLY)
    private int id;
    private String name;
    private int cnic;
    private EmployeeRoles roles;



    public Staff(String name , int cnic, EmployeeRoles roles ){
        this.name = name;
        this.cnic = cnic;
        this.roles = roles ;

    }

}



