dataSource {
    pooled = true
    //driverClassName = "org.hsqldb.jdbcDriver"
    driverClassName = "com.mysql.jdbc.Driver"
    username = "timesheet"
    password = "timesheet"
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = true
    cache.provider_class = 'net.sf.ehcache.hibernate.EhCacheProvider'
}
// environment specific settings
environments {
    development {
       dataSource {
            driverClassName = "org.hsqldb.jdbcDriver"
            dbCreate = "create-drop" // one of 'create', 'create-drop','update'
            url = "jdbc:hsqldb:mem:devDB"
            username = ""
            password = ""
       }
    }
    test {
        dataSource {
            driverClassName = "org.hsqldb.jdbcDriver"
            dbCreate = "update"
            url = "jdbc:hsqldb:mem:testDb"
            username = ""
            password = ""
        }
    }
    production {
        dataSource {
            dbCreate = "update"
            url = "jdbc:mysql://dev.bct-llc.com:3306/timesheet"
        }
    }
}
