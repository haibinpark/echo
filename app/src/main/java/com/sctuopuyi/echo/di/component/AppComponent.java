package com.sctuopuyi.echo.di.component;

import com.sctuopuyi.echo.app.App;
import com.sctuopuyi.echo.data.DatamanagerHelper;
import com.sctuopuyi.echo.di.module.AppModule;
import com.sctuopuyi.echo.di.module.HttpModule;
import com.sctuopuyi.echo.di.module.PageModule;
import dagger.Component;

import javax.inject.Singleton;

/**
 * Created on 25/07/2017 16:03.
 */
@Singleton
@Component(
        modules = {
                AppModule.class,
                HttpModule.class,
                PageModule.class
        }
)
public interface AppComponent {
    App getContext(); //提供App的Context

    DatamanagerHelper dataManagerHelper(); //提供数据管理类
}
