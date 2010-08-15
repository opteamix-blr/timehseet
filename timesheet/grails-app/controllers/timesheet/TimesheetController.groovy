package timesheet

import com.ewconline.timesheet.Timesheet 

class TimesheetController {
	def scafold = true
    def index = { }
	
	//  http://localhost:8080/Timesheet/timesheet/listTimesheets
	def listTimesheets = {
		def timesheetList = Timesheet.list()
		render(view:"list", timesheetList:timesheetList)
	}
}
