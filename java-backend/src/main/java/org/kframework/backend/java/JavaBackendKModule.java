// Copyright (c) 2015-2018 Runtime Verification, Inc. (RV-Match team). All Rights Reserved.
package org.kframework.backend.java;

import com.google.inject.AbstractModule;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.MapBinder;
import com.google.inject.name.Names;
import org.kframework.backend.java.symbolic.InitializeRewriter;
import org.kframework.backend.java.symbolic.JavaBackend;
import org.kframework.compile.Backend;
import org.kframework.kprove.KProve;
import org.kframework.krun.ToolActivation;
import org.kframework.krun.modes.ExecutionMode;
import org.kframework.main.AbstractKModule;
import org.kframework.rewriter.Rewriter;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * Created by dwightguth on 5/27/15.
 */
public class JavaBackendKModule extends AbstractKModule {

    @Override
    public List<Module> getKompileModules() {
        List<Module> mods = super.getKompileModules();
        mods.add(new AbstractModule() {
            @Override
            protected void configure() {
                installJavaBackend(binder());
            }
            });
        return mods;
    }

    private void installJavaBackend(Binder binder) {
        MapBinder<String, Backend> mapBinder = MapBinder.newMapBinder(
                binder, String.class, Backend.class);
        mapBinder.addBinding("java").to(JavaBackend.class);
    }

    @Override
    public List<Module> getKRunModules() {
        return Collections.singletonList(new AbstractModule() {
            @Override
            protected void configure() {
                installJavaRewriter(binder());

                MapBinder<String, Integer> checkPointBinder = MapBinder.newMapBinder(
                        binder(), String.class, Integer.class, Names.named("checkpointIntervalMap"));
                checkPointBinder.addBinding("java").toInstance(50); //TODO(dwightguth): finesse this number
            }
        });
    }

    @Override
    public List<Module> getKProveModules() {
        return Collections.singletonList(new AbstractModule() {
            @Override
            protected void configure() {
                installJavaBackend(binder());
                installJavaRewriter(binder());
            }
        });
    }

    private void installJavaRewriter(Binder binder) {
        MapBinder<String, Function<org.kframework.definition.Module, Rewriter>> rewriterBinder = MapBinder.newMapBinder(
                binder, TypeLiteral.get(String.class), new TypeLiteral<Function<org.kframework.definition.Module, Rewriter>>() {
                });
        rewriterBinder.addBinding("java").to(InitializeRewriter.class);
    }

    @Override
    public List<Module> getDefinitionSpecificKEqModules() {
        return Collections.singletonList(new AbstractModule() {
            @Override
            protected void configure() {
                installJavaBackend(binder());
                installJavaRewriter(binder());
            }
        });
    }
}
