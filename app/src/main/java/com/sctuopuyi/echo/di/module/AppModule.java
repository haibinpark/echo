package com.sctuopuyi.echo.di.module;

import javax.inject.Singleton;
import com.sctuopuyi.echo.app.App;
import com.sctuopuyi.echo.data.DatamanagerHelper;
import com.sctuopuyi.echo.data.db.DatabaseHelper;
import com.sctuopuyi.echo.data.http.HttpHelper;
import com.sctuopuyi.echo.data.http.MainServiceApi;
import com.sctuopuyi.echo.data.local.SharedPreferenceHelper;

import dagger.Module;
import dagger.Provides;

/**
 * Created on 25/07/2017 16:41.
 */
@Module
public class AppModule {

    private final App application;


    public AppModule(App application) {
        this.application = application;
    }

    /**
     * 提供context
     *
     * @return
     */
    @Provides
    @Singleton
    App provideApplicationContext() {
        return application;
    }


    @Provides
    @Singleton
    DatabaseHelper provideDatabaseHelper() {
        return new DatabaseHelper(application);
    }


    /**
     * 提供网络请求帮助类
     *
     * @param serviceApi
     * @return RetrofitHelper
     */
    @Provides
    @Singleton
    HttpHelper provideRetrofitHelper(MainServiceApi serviceApi) {
        return new HttpHelper(serviceApi);
    }


    @Provides
    @Singleton
    DatamanagerHelper provideDatamanagerHelper(HttpHelper httpHelper
            , SharedPreferenceHelper sharedPreferenceHelper
            , DatabaseHelper databaseHelper) {
        return new DatamanagerHelper(httpHelper, sharedPreferenceHelper, databaseHelper);
    }


    @Provides
    @Singleton
    SharedPreferenceHelper provideSharedPreferenceHelper(App app) {
        return new SharedPreferenceHelper(app);
    }

}
