package uz.atoy.travellogms.service.impl;

import liquibase.pro.packaged.T;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import uz.atoy.travellogms.model.ReportFilter;
import uz.atoy.travellogms.model.ResponseData;
import uz.atoy.travellogms.model.TravelLog;
import uz.atoy.travellogms.service.TravelLogsService;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Service
public class TravelLogsServiceImpl implements TravelLogsService {

    private final JdbcTemplate jdbcTemplate;

    public TravelLogsServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public ResponseData<T> addTravelLog(TravelLog travelLog) {

        try {
            int code = jdbcTemplate.update(
                    "INSERT INTO public.travel_log " +
                            "(vehicle_reg_num, vehicle_owner, odometer_value_at_start, odometer_value_at_finish, route, short_description, log_time) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?);",
                    travelLog.getVehicleRegNum(),
                    travelLog.getVehicleOwner(),
                    travelLog.getOdometerValueAtStart(),
                    travelLog.getOdometerValueAtFinish(),
                    travelLog.getRoute(),
                    travelLog.getShortDescription(),
                    travelLog.getLogTime()
            );
            if (code == 0) {
                throw new Exception();
            }
            return new ResponseData<>("Successfully", code);
        } catch (Exception e) {
            return new ResponseData<>("Failed operation", -1);
        }

    }

    @Override
    public ResponseData<TravelLog> getTravelLogById(Integer id) {

        try {
            TravelLog travelLog = jdbcTemplate.query("SELECT * FROM public.travel_log WHERE id = ?", rs -> {
                TravelLog log = new TravelLog();
                rs.next();
                log.setId(rs.getInt("id"));
                log.setVehicleRegNum(rs.getString("vehicle_reg_num"));
                log.setVehicleOwner(rs.getString("vehicle_owner"));
                log.setOdometerValueAtStart(rs.getInt("odometer_value_at_start"));
                log.setOdometerValueAtFinish(rs.getInt("odometer_value_at_finish"));
                log.setRoute(rs.getString("route"));
                log.setShortDescription(rs.getString("short_description"));
                log.setLogTime(Timestamp.valueOf(rs.getString("log_time")).toLocalDateTime());
                return log;
            }, id);
            return new ResponseData<>(travelLog, "Successfully");
        } catch (DataAccessException e) {
            return new ResponseData<>("Travel log by this id not exists", -1);
        }
    }

    @Override
    public ResponseData<T> editTravelLog(TravelLog travelLog) {

        try {
            if (Objects.isNull(travelLog.getId()))
                return new ResponseData<>("id is required parameter!", -1);

            int code = jdbcTemplate.update(
                    "UPDATE public.travel_log\n" +
                            "SET vehicle_reg_num          = ?, " +
                            "    vehicle_owner            = ?, " +
                            "    odometer_value_at_start  = ?, " +
                            "    odometer_value_at_finish = ?, " +
                            "    route                    = ?, " +
                            "    short_description        = ?, " +
                            "    log_time                 = ? " +
                            "WHERE id = ?;",
                    travelLog.getVehicleRegNum(),
                    travelLog.getVehicleOwner(),
                    travelLog.getOdometerValueAtStart(),
                    travelLog.getOdometerValueAtFinish(),
                    travelLog.getRoute(),
                    travelLog.getShortDescription(),
                    travelLog.getLogTime(),
                    travelLog.getId()
            );
            if (code == 0) {
                throw new Exception();
            }
            return new ResponseData<>("Successfully", code);
        } catch (Exception e) {
            return new ResponseData<>("Failed operation", -1);
        }

    }

    @Override
    public ResponseData<T> removeTravelLogById(Integer id) {

        try {
            int code = jdbcTemplate.update(
                    "DELETE FROM public.travel_log WHERE id = ?;",
                    id
            );
            if (code == 0) {
                throw new Exception();
            }
            return new ResponseData<>("Successfully");
        } catch (Exception e) {
            return new ResponseData<>("Failed operation", -1);
        }

    }

    @Override
    public ResponseData<List<TravelLog>> generateReportByFilters(ReportFilter reportFilter) {

        try {
            List<TravelLog> travelLogList;

            if (Objects.isNull(reportFilter)) {
                // return all data after sorting
                travelLogList = jdbcTemplate.query(
                        "SELECT * FROM public.travel_log ORDER BY odometer_value_at_start;",
                        (rs, rowNum) -> new TravelLog(
                                rs.getInt("id"),
                                rs.getString("vehicle_reg_num"),
                                rs.getString("vehicle_owner"),
                                rs.getInt("odometer_value_at_start"),
                                rs.getInt("odometer_value_at_finish"),
                                rs.getString("route"),
                                rs.getString("short_description"),
                                Timestamp.valueOf(rs.getString("log_time")).toLocalDateTime()
                        )
                );
                return new ResponseData<>(travelLogList, "Not filtered list");
            } else {
                // return all data after sorting and filtering
                travelLogList = jdbcTemplate.query(
                        "SELECT * FROM public.travel_log WHERE log_time BETWEEN ? AND ? AND vehicle_reg_num = ? AND vehicle_owner = ?",
                        (rs, rowNum) -> new TravelLog(
                                rs.getInt("id"),
                                rs.getString("vehicle_reg_num"),
                                rs.getString("vehicle_owner"),
                                rs.getInt("odometer_value_at_start"),
                                rs.getInt("odometer_value_at_finish"),
                                rs.getString("route"),
                                rs.getString("short_description"),
                                Timestamp.valueOf(rs.getString("log_time")).toLocalDateTime()
                        ),
                        reportFilter.getStartDate(), reportFilter.getEndDate(), reportFilter.getVehicleRegNum(), reportFilter.getVehicleOwner());
                return new ResponseData<>(travelLogList, "Filtered list");
            }
        } catch (DataAccessException e) {
            return new ResponseData<>("Failed operation", -1);
        }

    }

}
