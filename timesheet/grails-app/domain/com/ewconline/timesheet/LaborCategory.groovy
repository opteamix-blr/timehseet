package com.ewconline.timesheet

class LaborCategory {
    String name
    String description
    
    static constraints = {
    }
    
    static belongsTo = [User, TimesheetEntry]
}
