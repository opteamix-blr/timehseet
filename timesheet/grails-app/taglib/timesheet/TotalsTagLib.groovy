package timesheet
import com.ewconline.timesheet.*;

class TotalsTagLib {
	static namespace="gt"
	
	def timesheetEntryTotal = { attrs ->
		def totalHours = 0 
		for(d in attrs.days){
			if (d.hoursWorked) {
				totalHours += d.hoursWorked
			}
		}
		out << String.format("%.2f", totalHours)
	}
}
