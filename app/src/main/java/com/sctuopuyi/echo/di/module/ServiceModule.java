package com.sctuopuyi.echo.di.module;


import android.app.Service;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

/**
 * Created on 24/03/2017 18:37.
 */
@Module
public class ServiceModule {

    public ServiceModule(Service service) {
        this.service = service;
    }


    @Provides
    @Singleton
    Service providePollIntentService() {
        return service;
    }


    //region 申明方法
    private Service service;
    //endregion

}
