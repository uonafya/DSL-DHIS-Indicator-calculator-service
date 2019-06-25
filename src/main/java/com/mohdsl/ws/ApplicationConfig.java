package com.mohdsl.ws;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/rest") // set the path to REST web services
public class ApplicationConfig extends Application {
    public Set<Class<?>> getClasses() {
        Set<Class<?>> s = new HashSet<Class<?>>();
        s.add(IndicatorValueResource.class);
        s.add(NumeratorDenominatorResource.class);
        s.add(PeriodsOrgResource.class);
        s.add(PeriodsResource.class);
        s.add(mohdslRequery.class);
        return s;
    }
}