package com.ewconline.timesheet

class DataTransferService {

    static transactional = true

    def sevenDayReport(startDate, chargeNumber) {
        def retval = []
        def c = Timesheet.createCriteria()
        def allTimesheets = c.list {
            createAlias("user", "u")
            createAlias("timesheetEntries", "te")
            createAlias("te.taskAssignment", "ta")
            createAlias("ta.chargeCode", "cc")
            if(startDate){
                eq("startDate", startDate)
            }
            if(chargeNumber && chargeNumber != "null"){
                eq("cc.chargeNumber", chargeNumber)
            }
//             or{
//                 eq('currentState', 'SIGNED')
//                 eq('currentState', 'APPROVED')
//             }
            
            order("u.lastName", "asc")
        }
        allTimesheets.each { t ->
            def allEntries = t.timesheetEntries
            allEntries.each { te ->
                def dto = new TimesheetDTSevenDay()
                dto.userRealName = t.user.userRealName
                dto.lastName = t.user.lastName
                dto.firstName = t.user.firstName
                dto.laborCategory = te.taskAssignment.laborCategory.name
                dto.taskName = te.taskAssignment.task.name
                dto.contractInfo1 = te.taskAssignment.task.contractInfo1
                dto.contractInfo2 = te.taskAssignment.task.contractInfo2
                dto.chargeCode = te.taskAssignment.chargeCode.chargeNumber
                dto.startDate = t.startDate
                dto.endDate = t.endDate
                def total = 0.0D
                te.workdays.eachWithIndex{ w, i ->
                    dto."day$i" = w?.hoursWorked
                    total += (w?.hoursWorked ?: 0.0) as Double
                }
                dto.totalHours = total
                retval.add(dto)
            }
        }
        return retval
    }
}
