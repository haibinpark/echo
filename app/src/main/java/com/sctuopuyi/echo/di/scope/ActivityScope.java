package com.sctuopuyi.echo.di.scope;

import javax.inject.Scope;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by haibin on 01/03/2017.
 */
@Scope
@Retention(RUNTIME)
public @interface ActivityScope {
}
