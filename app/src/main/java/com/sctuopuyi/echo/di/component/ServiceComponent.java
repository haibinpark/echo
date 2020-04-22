package com.sctuopuyi.echo.di.component;


import com.sctuopuyi.echo.di.module.ServiceModule;
import com.sctuopuyi.echo.di.scope.ServiceScope;

import dagger.Component;

/**
 * Created on 24/03/2017 18:41.
 */
@ServiceScope
@Component(
        dependencies = {AppComponent.class},
        modules = {
                ServiceModule.class
        }
)
public interface ServiceComponent {

}
