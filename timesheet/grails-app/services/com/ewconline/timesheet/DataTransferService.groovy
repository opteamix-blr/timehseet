package com.ewconline.timesheet

class DataTransferService {

    static transactional = true

    def sevenDayReport(startDate) {
        def retval = []
        def allTimesheets = Timesheet.list()
        allTimesheets.each { t ->
            def allEntries = t.timesheetEntries
            allEntries.each { te ->
                def dto = new TimesheetDTSevenDay()
                dto.userName = t.user.username
                dto.laborCategory = te.taskAssignment.laborCategory.name
                dto.taskName = te.taskAssignment.task.name
                dto.contractInfo1 = te.taskAssignment.task.contractInfo1
                dto.contractInfo2 = te.taskAssignment.task.contractInfo2
                dto.chargeCode = te.taskAssignment.chargeCode.chargeNumber
                def total = 0.0D
                te.workdays.eachWithIndex{ w, i ->
                    dto."day$i" = w.hoursWorked as String
                    total += w.hoursWorks as Double
                }
                retval.add(dto)
            }
        }
        return retval
    }
}
