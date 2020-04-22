package com.sctuopuyi.echo.di.qualifier;

import javax.inject.Qualifier;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by haibin on 01/03/2017.
 */
@Qualifier
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface ServiceUrl {
}
