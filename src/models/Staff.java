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


    @JsonProperty(access = WRITE_ONLY)
    private int id;
    private String name;
    private int cnic;
    private String role;



    public Staff(String name , int cnic, String role ){
        this.name = name;
        this.cnic = cnic;
        this.role = role ;

    }

}



