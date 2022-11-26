package uz.atoy.travellogms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TravelLog {

    private Integer id;

    private String vehicleRegNum;

    private String vehicleOwner;

    private Integer odometerValueAtStart;

    private Integer odometerValueAtFinish;

    private String route;

    private String shortDescription;

    private LocalDateTime logTime = LocalDateTime.now();

    public TravelLog(Integer id, String vehicleRegNum, String vehicleOwner, Integer odometerValueAtStart, Integer odometerValueAtFinish, String route, String shortDescription) {
        this.id = id;
        this.vehicleRegNum = vehicleRegNum;
        this.vehicleOwner = vehicleOwner;
        this.odometerValueAtStart = odometerValueAtStart;
        this.odometerValueAtFinish = odometerValueAtFinish;
        this.route = route;
        this.shortDescription = shortDescription;
    }

}
