package com.sctuopuyi.echo.di.component;

import android.app.Activity;
import com.sctuopuyi.echo.di.module.FragmentModule;
import com.sctuopuyi.echo.di.scope.FragmentScope;

import dagger.Component;
import org.jetbrains.annotations.NotNull;

/**
 */

@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {

    Activity getActivity();

}
