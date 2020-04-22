package com.sctuopuyi.echo.di.scope;

import javax.inject.Scope;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created on 24/03/2017 18:35.
 */

@Scope
@Retention(RUNTIME)
public @interface ServiceScope {
}
