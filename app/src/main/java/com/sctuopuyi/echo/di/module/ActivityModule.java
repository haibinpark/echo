package com.sctuopuyi.echo.di.module;

import android.app.Activity;
import com.sctuopuyi.echo.di.scope.ActivityScope;
import dagger.Module;
import dagger.Provides;

/**
 * Created on 02/03/2017 14:20.
 */
@Module
public class ActivityModule {

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    Activity provideActivity(){
        return this.activity;
    }

    private Activity activity;

}
