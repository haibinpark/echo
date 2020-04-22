package com.sctuopuyi.echo.di.component;

import android.app.Activity;

import com.sctuopuyi.echo.di.module.ActivityModule;
import com.sctuopuyi.echo.di.scope.ActivityScope;
import com.sctuopuyi.echo.ui.main.MainActivity;
import com.sctuopuyi.echo.ui.startup.StartupActivity;
import com.sctuopuyi.echo.ui.web.AuthenWebsActivity;
import com.sctuopuyi.echo.ui.web.SimpleWebActivity;

import dagger.Component;

import org.jetbrains.annotations.NotNull;

/**
 * Created on 02/03/2017 14:18.
 */
@ActivityScope
@Component(
        dependencies = AppComponent.class,
        modules = {
                ActivityModule.class
        })
public interface ActivityComponent {
    Activity getActivity();

    void inject(@NotNull StartupActivity activity);

    void inject(@NotNull MainActivity activity);


    void inject(@NotNull SimpleWebActivity activity);


    void inject(@NotNull AuthenWebsActivity activity);


    //region 注入activity

    //endregion
}
