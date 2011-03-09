package com.ewconline.timesheet

import org.codehaus.groovy.grails.commons.DefaultGrailsDomainClass

abstract class AuditableObject {

//    def auditingService

    def afterInsert = {
        def objectToAudit = new DefaultGrailsDomainClass(this.class)
        objectToAudit.constrainedProperties.each{k, v ->
            def c = new Change()
            c.domainModelName = this.class.simpleName
            c.referenceId = this.id
            c.previousValue = null
            c.fieldName = k
            c.newValue = this."$k"  //all "owned" objects must implement toString for this to work
//            c.userName = auditingService.currentUserName()
            c.save()
        }
    }

    def beforeUpdate = {
        def modifiedFields = this.getDirtyPropertyNames()
        modifiedFields.each{
            def currentValue = this."$it"
            def originalValue = this.getPersistentValue(it)
            if (currentValue != originalValue){
                def c = new Change()
                c.domainModelName = this.class.name
                c.referenceId = this.id
                c.previousValue = originalValue
                c.newValue = currentValue
                c.fieldName = it
//                c.userName = auditingService.currentUserName()
                c.save()
            }
        }
    }

    static constraints = {
    }
}
