/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nwmissouri.universitypolice.saferidehome.service;

import edu.nwmissouri.universitypolice.saferidehome.dao.SafeRideHomeReportDao;
import edu.nwmissouri.universitypolice.saferidehome.pojos.DailyReport;
import edu.nwmissouri.universitypolice.saferidehome.pojos.GenderByVehicle;
import edu.nwmissouri.universitypolice.saferidehome.pojos.HourlyReport;
import edu.nwmissouri.universitypolice.saferidehome.pojos.Report;
import edu.nwmissouri.universitypolice.saferidehome.pojos.RidersByVehicle;
import edu.nwmissouri.universitypolice.saferidehome.pojos.StudentsByVehicle;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.Font;

/**
 *
 * @author Deepthi Nelavelli
 */
public class SafeRideHomeReportService {

    public HSSFWorkbook generateReport(String startDate, String endDate, String startTime, String endTime, String interval) throws ParseException {
        SafeRideHomeReportDao dao = new SafeRideHomeReportDao();
        // convert data from string to date, time
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDt = new Date();
        Date endDt = new Date();
        Time sTime = new Time(1223);
        Time eTime = new Time(1223);
        try {
            startDt = dateFormat.parse(startDate);
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            endDt = dateFormat.parse(endDate);
        } catch (Exception e) {
            System.out.println(e);
        }
        sTime = new Time(1223);
        sTime.setHours(Integer.parseInt(startTime.split(":")[0]));
        sTime.setMinutes(Integer.parseInt(startTime.split(":")[1]));
        sTime.setSeconds(0);
        eTime = new Time(1223);
        eTime.setHours(Integer.parseInt(endTime.split(":")[0]));
        eTime.setMinutes(Integer.parseInt(endTime.split(":")[1]));
        eTime.setSeconds(59);
        int intervalMinutes = Integer.parseInt(interval);
        // call the getReport method from report dao 
        Report report = dao.getReport(startDt, endDt, sTime, eTime, intervalMinutes);
        // call writeToExcel method.
        return writeToExcel(report);
        //dao.getReport(Date startDate, Date endDate, Time startTime, Time endTime, int interval) 
    }

    private HSSFWorkbook writeToExcel(Report report) throws ParseException {
        String fileName = "";
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Sample sheet");
        CreationHelper createHelper = workbook.getCreationHelper();
        int rownum = 1;
        HashMap<String, int[]> ridersByVehicleMap = new HashMap();
        CellStyle cellStyle = workbook.createCellStyle();
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
        headerStyle.setFillPattern(cellStyle.SOLID_FOREGROUND);
        headerStyle.setBorderBottom(org.apache.poi.ss.usermodel.CellStyle.BORDER_THICK);
        headerStyle.setBorderTop(org.apache.poi.ss.usermodel.CellStyle.BORDER_THICK);
        sheet.setColumnWidth(0, 8000);

        sheet.addMergedRegion(new CellRangeAddress(0, 0, 4, 7));
        Row headerrow = sheet.createRow(0);
        //Cell header = headerrow.createCell(4);

        for (int i = 4; i <= 7; i++) {
            Cell header = headerrow.createCell(i);
            header.setCellStyle(headerStyle);
            if (i == 4) {
                header.setCellValue("Safe Ride Statistics");
                headerStyle.setBorderLeft(org.apache.poi.ss.usermodel.CellStyle.BORDER_THICK);
            }
            if (i == 7) {
                headerStyle.setBorderRight(org.apache.poi.ss.usermodel.CellStyle.BORDER_THICK);
            }

        }
        cellStyle.setBorderBottom(org.apache.poi.ss.usermodel.CellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(org.apache.poi.ss.usermodel.CellStyle.BORDER_THIN);
        cellStyle.setBorderRight(org.apache.poi.ss.usermodel.CellStyle.BORDER_THIN);
        cellStyle.setBorderTop(org.apache.poi.ss.usermodel.CellStyle.BORDER_THIN);
        headerStyle.setAlignment(org.apache.poi.ss.usermodel.CellStyle.ALIGN_CENTER);
        Font headerFont = workbook.createFont();
        headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);

        headerFont.setColor(IndexedColors.BLACK.getIndex());
        CellStyle Style = workbook.createCellStyle();
        Style.setFont(headerFont);

        Style.setBorderBottom(org.apache.poi.ss.usermodel.CellStyle.BORDER_THIN);
        Style.setBorderLeft(org.apache.poi.ss.usermodel.CellStyle.BORDER_THIN);
        Style.setBorderRight(org.apache.poi.ss.usermodel.CellStyle.BORDER_THIN);
        Style.setBorderTop(org.apache.poi.ss.usermodel.CellStyle.BORDER_THIN);

        rownum++;
        Row totalCall = sheet.createRow(rownum);
        Cell totalCallCell = totalCall.createCell(0);
        totalCallCell.setCellStyle(cellStyle);
        totalCallCell.setCellValue("Total Calls");
        Cell totoalCallValue = totalCall.createCell(1);
        totoalCallValue.setCellStyle(cellStyle);
        totoalCallValue.setCellValue(report.getTotalNoOfCalls());
        rownum++;
        Row totalPassangers = sheet.createRow(rownum);
        Cell totalPassangersCell = totalPassangers.createCell(0);
        totalPassangersCell.setCellStyle(cellStyle);
        totalPassangersCell.setCellValue("Total Passangers");
        Cell totalPassangersCellValue = totalPassangers.createCell(1);
        totalPassangersCellValue.setCellStyle(cellStyle);
        totalPassangersCellValue.setCellValue(report.getTotalNoOfRiders());
        for (DailyReport dailyReport : report.getDailyReports()) {
            rownum++;
            rownum++;
            String[] dateString = dailyReport.getDate().split(" ");
            Row date = sheet.createRow(rownum);
            Cell dateCell = date.createCell(0);
            dateCell.setCellValue("Date: " + dateString[1] + " " + dateString[2] + ", " + dateString[5]);
            dateCell.setCellStyle(Style);
            rownum++;
            rownum++;
            Row totalNumberOfCalls = sheet.createRow(rownum);
            Cell totalNumberOfCallsCell = totalNumberOfCalls.createCell(0);
            totalNumberOfCallsCell.setCellStyle(Style);
            totalNumberOfCallsCell.setCellValue("Total Number Of Calls");
            Cell totalNumberOfCallsValue = totalNumberOfCalls.createCell(1);
            totalNumberOfCallsValue.setCellStyle(Style);
            totalNumberOfCallsValue.setCellValue(dailyReport.getTotalNoOfCalls());
            rownum++;
            rownum++;
            Row intervalRow = sheet.createRow(rownum);
            rownum++;
            Row totalNumberOfCallsByTime = sheet.createRow(rownum);
            Cell totalNumberOfCallsByTimeCell = totalNumberOfCallsByTime.createCell(0);
            totalNumberOfCallsByTimeCell.setCellStyle(cellStyle);
            totalNumberOfCallsByTimeCell.setCellValue("Total Number of Calls By Time");
            int cellNum = 1;
            for (HourlyReport hourlyReport : dailyReport.getHourlyReports()) {
                Cell totalNumberOfCallsByTimeValue = totalNumberOfCallsByTime.createCell(cellNum);
                totalNumberOfCallsByTimeValue.setCellStyle(cellStyle);
                totalNumberOfCallsByTimeValue.setCellValue(hourlyReport.getNoOfCalls());
                rownum--;
                Cell intervalvalue = intervalRow.createCell(cellNum);
                intervalvalue.setCellStyle(cellStyle);
                sheet.setColumnWidth(cellNum, 3000);
                intervalvalue.setCellValue(hourlyReport.getInterval());
                cellNum++;
                rownum++;
            }
            rownum++;
            rownum++;
            Row passangersByVehicleInterval = sheet.createRow(rownum);
            Cell PassangersByVehicleCell = passangersByVehicleInterval.createCell(0);
            PassangersByVehicleCell.setCellValue("Riders By Vehicle");
            PassangersByVehicleCell.setCellStyle(cellStyle);
            rownum++;
            int rows = 1;
            int cols = 1;
            int rownumber = rownum;
            String[] vehicles = new String[100];
            int[] totalRiders = new int[100];
            int[][] riders = new int[100][100];
            int noOfVehicles = 0;
            int count = 0;
            ArrayList<String> vehicleIds = new ArrayList<String>();
            for (HourlyReport hourlyReport : dailyReport.getHourlyReports()) {
                Cell passangersByVehicleCell = passangersByVehicleInterval.createCell(cols);
                passangersByVehicleCell.setCellStyle(cellStyle);
                passangersByVehicleCell.setCellValue(hourlyReport.getInterval());
                noOfVehicles = hourlyReport.getRidersByVehicles().size();

                if (hourlyReport.getRidersByVehicles() != null) {
                    for (RidersByVehicle ridersByVehicle : hourlyReport.getRidersByVehicles()) {
                        if (!vehicleIds.contains(ridersByVehicle.getVehicleId())) {
                            vehicleIds.add(ridersByVehicle.getVehicleId());
                        }
                        vehicles[rows] = ridersByVehicle.getVehicleId();
                        riders[rows][cols] = ridersByVehicle.getNoOfRiders();
                        rows++;
                        count++;
                        if (count == vehicleIds.size()) {
                            rows = rows - count;
                            count = 0;
                        }
                    }
                }
                totalRiders[cols] = hourlyReport.getTotalNoOfRiders();
                cols++;
            }
            for (int i = 1; i <= vehicleIds.size(); i++) {
                Row passangersByVehicle = sheet.createRow(rownum);
                for (int j = 0; j < cols; j++) {
                    if (j == 0) {
                        Cell passangersByVehicleCell = passangersByVehicle.createCell(j);
                        passangersByVehicleCell.setCellStyle(cellStyle);
                        passangersByVehicleCell.setCellValue(vehicles[i]);
                    } else {
                        Cell passangersByVehicleCell = passangersByVehicle.createCell(j);
                        passangersByVehicleCell.setCellStyle(cellStyle);
                        passangersByVehicleCell.setCellValue(riders[i][j]);
                    }
                }
                rownum++;
            }
            Row totalNumberOfRidersByInterval = sheet.createRow(rownum);
            Cell totalRidersCell = totalNumberOfRidersByInterval.createCell(0);
            totalRidersCell.setCellStyle(Style);
            totalRidersCell.setCellValue("Total");
            for (int i = 1; i < cols; i++) {
                Cell totalNumberOfRiders = totalNumberOfRidersByInterval.createCell(i);
                totalNumberOfRiders.setCellValue(totalRiders[i]);
                totalNumberOfRiders.setCellStyle(Style);
            }
            rownum++;
            rownum++;
            Row gender = sheet.createRow(rownum);
            rownum++;
            Cell genderHeader = gender.createCell(0);
            genderHeader.setCellStyle(cellStyle);
            genderHeader.setCellValue("Gender of Transported By Vehicle");
            Cell male = gender.createCell(1);
            male.setCellStyle(cellStyle);
            male.setCellValue("Male");
            Cell female = gender.createCell(2);
            female.setCellStyle(cellStyle);
            female.setCellValue("Female");
            Cell others = gender.createCell(3);
            others.setCellStyle(cellStyle);
            others.setCellValue("Others");
            int totalMale = 0;
            int totalFemale = 0;
            int totalOtherRiders = 0;
            for (GenderByVehicle genderByVehicle : dailyReport.getGenderReports()) {
                Row genderByVehicleRow = sheet.createRow(rownum);
                rownum++;
                Cell genderByVehicleCell = genderByVehicleRow.createCell(0);
                genderByVehicleCell.setCellStyle(cellStyle);
                genderByVehicleCell.setCellValue(genderByVehicle.getVehicleId());
                Cell MaleByVehicle = genderByVehicleRow.createCell(1);
                MaleByVehicle.setCellStyle(cellStyle);
                MaleByVehicle.setCellValue(genderByVehicle.getNoOfMaleRiders());
                totalMale += genderByVehicle.getNoOfMaleRiders();
                Cell FemaleByVehicle = genderByVehicleRow.createCell(2);
                totalFemale += genderByVehicle.getNoOfFemaleRiders();
                FemaleByVehicle.setCellStyle(cellStyle);
                FemaleByVehicle.setCellValue(genderByVehicle.getNoOfFemaleRiders());
                Cell othersByVehicle = genderByVehicleRow.createCell(3);
                othersByVehicle.setCellValue(genderByVehicle.getNoOfOtherRiders());
                totalOtherRiders += genderByVehicle.getNoOfOtherRiders();
                othersByVehicle.setCellStyle(cellStyle);
            }

            Row totalByGender = sheet.createRow(rownum);
            Cell total = totalByGender.createCell(0);
            total.setCellStyle(Style);
            total.setCellValue("Total");
            Cell totalMaleCell = totalByGender.createCell(1);
            totalMaleCell.setCellStyle(Style);
            totalMaleCell.setCellValue(totalMale);
            Cell totalFemaleCell = totalByGender.createCell(2);
            totalFemaleCell.setCellStyle(Style);
            totalFemaleCell.setCellValue(totalFemale);
            Cell totalOtherRiders1 = totalByGender.createCell(3);
            totalOtherRiders1.setCellStyle(Style);
            totalOtherRiders1.setCellValue(totalOtherRiders);
            rownum++;
            rownum++;
            Row nwStudents = sheet.createRow(rownum);
            rownum++;
            Cell studentsCell = nwStudents.createCell(0);
            studentsCell.setCellValue("Northwest Students");
            studentsCell.setCellStyle(cellStyle);
            Cell nwStudentsY = nwStudents.createCell(1);
            nwStudentsY.setCellStyle(cellStyle);
            nwStudentsY.setCellValue("Yes");
            Cell nwStudentsN = nwStudents.createCell(2);
            nwStudentsN.setCellStyle(cellStyle);
            nwStudentsN.setCellValue("No");
            int totalNwStudents = 0;
            int totalNonNwStudents = 0;
            for (StudentsByVehicle studentsByVehicle : dailyReport.getStudentReports()) {
                Row studentByVehicleRow = sheet.createRow(rownum);
                Cell studentsByVehicleCell = studentByVehicleRow.createCell(0);
                studentsByVehicleCell.setCellStyle(cellStyle);
                studentsByVehicleCell.setCellValue(studentsByVehicle.getVehicleId());
                Cell studentByVehicleY = studentByVehicleRow.createCell(1);
                studentByVehicleY.setCellStyle(cellStyle);
                studentByVehicleY.setCellValue(studentsByVehicle.getNoOfNWStudents());
                totalNwStudents += studentsByVehicle.getNoOfNWStudents();
                Cell studentByVehicleN = studentByVehicleRow.createCell(2);
                studentByVehicleN.setCellStyle(cellStyle);
                studentByVehicleN.setCellValue(studentsByVehicle.getNoOfNonNWStudents());
                totalNonNwStudents += studentsByVehicle.getNoOfNonNWStudents();
                rownum++;
            }
            Row totalByNwStudents = sheet.createRow(rownum);
            Cell totalStudents = totalByNwStudents.createCell(0);
            totalStudents.setCellStyle(Style);
            totalStudents.setCellValue("Total");
            Cell totalNwStudentsValue = totalByNwStudents.createCell(1);
            totalNwStudentsValue.setCellStyle(Style);
            totalNwStudentsValue.setCellValue(totalNwStudents);
            Cell totalnonNwStudentsValue = totalByNwStudents.createCell(2);
            totalnonNwStudentsValue.setCellStyle(Style);
            totalnonNwStudentsValue.setCellValue(totalNonNwStudents);
            vehicleIds.removeAll(vehicleIds);
        }
//        try {
//            FileOutputStream out = new FileOutputStream(new File("../SafeRideHomeReport.xls"));
//            workbook.write(out);
//            out.close();
//        } catch (FileNotFoundException e) {
//            System.out.println(e);
//        } catch (IOException e) {
//            System.out.println(e);
//        }
        return workbook;
    }
}
