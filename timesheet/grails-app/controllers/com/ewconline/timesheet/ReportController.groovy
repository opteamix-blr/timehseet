package com.ewconline.timesheet

import org.codehaus.groovy.grails.commons.*
import grails.util.Environment
import hirondelle.date4j.DateTime
import java.util.TimeZone
import java.util.*

class ReportController {

    def dataTransferService
    def exportService

    def view = {
        
    }

    def exportTimesheets = {
        DateTime saturday = null
        def targetDay
        def dateOnWeek = params["dateOnWeek"]
        def millis
        def realDate
        if (dateOnWeek) {
            dateOnWeek = "${dateOnWeek?.format("yyyy-MM-dd")}"
        }
        if (dateOnWeek && dateOnWeek.length() > 0) {
            targetDay = new DateTime(dateOnWeek)
            if (targetDay.getWeekDay() == 7) {
                saturday = targetDay.getStartOfDay()
            }else {
                saturday = targetDay.minusDays(targetDay.getWeekDay()).getStartOfDay()
            }
            millis = saturday.getMilliseconds(TimeZone.getDefault())
            realDate = new Date(millis)
        }
        

        def chargeNumber = params.remove("chargeNumber")


        params.format = "excel"
        params.extension = "xls"
        response.contentType = ConfigurationHolder.config.grails.mime.types[params.format]
        response.setHeader("Content-disposition", "attachment; filename=books.${params.extension}")

        def taskDto = dataTransferService.sevenDayReport(realDate, chargeNumber)

        exportService.export(params.format, response.outputStream,taskDto, [:], [:]) 
    }
}
