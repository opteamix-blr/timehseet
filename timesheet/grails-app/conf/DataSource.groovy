dataSource {
    pooled = true
    driverClassName = "org.hsqldb.jdbcDriver"
    username = "sa"
    password = ""
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
            dbCreate = "create-drop" // one of 'create', 'create-drop','update'
            url = "jdbc:hsqldb:file:devDB;shutdown=true"
        }
    }
    test {
        dataSource {
            dbCreate = "create-drop"
            url = "jdbc:hsqldb:mem:testDb"
        }
    }
    production {
        dataSource {
            dbCreate = "update"
            driverClassName = "com.mysql.jdbc.Driver"
            username = "timesheet"
            password = "timesheet"
            url = "jdbc:mysql://localhost:3306/timesheet"
            properties{
                maxActive = 50
                maxIdle = 25
                minIdle = 5
                intialSize = 5
                minEvictableTimeMillis = 1800000
                timeBetweenEvictionRunsMillis = 1800000
                maxWait = 10000
                validationQuery = "Select 1"
            }

        }
    }
}
