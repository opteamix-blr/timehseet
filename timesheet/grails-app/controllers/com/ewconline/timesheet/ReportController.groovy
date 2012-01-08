package com.ewconline.timesheet

import org.codehaus.groovy.grails.commons.*
import grails.util.Environment


class ReportController {

    def dataTransferService
    def exportService

    def view = {
        
    }

    def exportTimesheets = {
        params.format = "excel"
        params.extension = "xls"
        response.contentType = ConfigurationHolder.config.grails.mime.types[params.format]
        response.setHeader("Content-disposition", "attachment; filename=books.${params.extension}")

        def taskDto = dataTransferService.sevenDayReport(null)

        exportService.export(params.format, response.outputStream,taskDto, [:], [:]) 
    }
}
