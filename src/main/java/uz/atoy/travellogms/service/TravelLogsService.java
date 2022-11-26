package uz.atoy.travellogms.service;

import liquibase.pro.packaged.T;
import uz.atoy.travellogms.model.ReportFilter;
import uz.atoy.travellogms.model.TravelLog;
import uz.atoy.travellogms.model.ResponseData;

import java.util.List;

public interface TravelLogsService {

    ResponseData<T> addTravelLog(TravelLog travelLog);

    ResponseData<TravelLog> getTravelLogById(Integer id);

    ResponseData<T> editTravelLog(TravelLog travelLog);

    ResponseData<T> removeTravelLogById(Integer id);

    ResponseData<List<TravelLog>> generateReportByFilters(ReportFilter reportFilter);

}
