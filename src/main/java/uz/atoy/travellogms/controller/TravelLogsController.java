package uz.atoy.travellogms.controller;

import liquibase.pro.packaged.T;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import uz.atoy.travellogms.model.TravelLog;
import uz.atoy.travellogms.model.ReportFilter;
import uz.atoy.travellogms.model.ResponseData;
import uz.atoy.travellogms.service.TravelLogsService;

import java.util.List;

@RestController
@RequestMapping("/travel/log")
public class TravelLogsController {

    private final TravelLogsService travelLogsService;

    public TravelLogsController(TravelLogsService travelLogsService) {
        this.travelLogsService = travelLogsService;
    }

    @PostMapping("/add")
    public ResponseData<T> addTravelLog(@RequestBody TravelLog travelLog) {
        return travelLogsService.addTravelLog(travelLog);
    }

    @GetMapping("/get/{id}")
    public ResponseData<TravelLog> getTravelLogById(@PathVariable Integer id) {
        return travelLogsService.getTravelLogById(id);
    }

    @PutMapping("/edit")
    public ResponseData<T> editTravelLog(@RequestBody TravelLog travelLog) {
        return travelLogsService.editTravelLog(travelLog);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseData<T> removeTravelLogById(@PathVariable Integer id) {
        return travelLogsService.removeTravelLogById(id);
    }

    @PostMapping("/report")
    public ResponseData<List<TravelLog>> generateReportByFilters(@Nullable @RequestBody ReportFilter reportFilter) {
        return travelLogsService.generateReportByFilters(reportFilter);
    }

}
