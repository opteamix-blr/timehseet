package timesheet
import com.ewconline.timesheet.*;

class TotalsTagLib {
	static namespace="gt"
	
	def timesheetEntryTotal = { attrs ->
		Float totalHours = new Float(0) 

		// add horizontal across
		if (!attrs.totalAcross || attrs.totalAcross == "true") {
			TimesheetEntry tse = TimesheetEntry.get(attrs.timesheetEntryId)
			tse.workdays.each { wd ->
				if (wd.hoursWorked) {
					totalHours += wd.hoursWorked
				}
			}
		} else {
			Timesheet ts = Timesheet.get(attrs.timesheetId)
			// TODO attribute to add by day of week
		}

		out << String.format("%.2f", totalHours)
	}
}
