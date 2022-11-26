package uz.atoy.travellogms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReportFilter {

    private LocalDate startDate;

    private LocalDate endDate;

    private String vehicleRegNum;

    private String vehicleOwner;

}
