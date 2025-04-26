package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Deal {

    @JsonProperty(access = WRITE_ONLY)
    public int id;
    public int productId;
    public String productName;
    public boolean minimunSelection;
    public boolean maximumSelection;
    public boolean isActive;
    public List<DealItem> dealItems;
}
