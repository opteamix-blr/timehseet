package com.ewconline.timesheet

class LookupController {

    def laborCategory = {
        def task = Task.get(params["selectedValue"])

        render (template:"/taskAssignment/laborCategorySelect", model : ["task":task])
    }

    def chargeCode = {
        def task = Task.get(params["selectedValue"])

        render (template:"/taskAssignment/chargeCodeSelect", model : ["task":task])
    }
}
