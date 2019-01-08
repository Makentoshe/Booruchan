package com.makentoshe.booruchan;


import android.app.Application;
import com.makentoshe.booruapi.Booru;
import com.makentoshe.booruapi.Gelbooru;
import com.makentoshe.network.HttpClient;
import com.makentoshe.network.fuel.FuelClientFactory;
import io.reactivex.exceptions.UndeliverableException;
import io.reactivex.functions.Consumer;
import io.reactivex.plugins.RxJavaPlugins;
import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;

import java.util.ArrayList;
import java.util.List;

public final class Booruchan extends Application {

    public static Booruchan INSTANCE;
    private Cicerone<Router> cicerone;
    private Style style;
    private List<Booru> boorus = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        cicerone = Cicerone.create();
        initRxErrorHandler();
        load();
    }

    private void initRxErrorHandler() {
        RxJavaPlugins.setErrorHandler(new Consumer<Throwable>() {
            @Override
            public void accept(Throwable e) throws Exception {
                if (e instanceof UndeliverableException) {
                    /*
                    Just ignore this exception - in the postpage screen throws each time after device rotation
                     */
                    return;
                }
            }
        });
    }

    private void load() {
        style = new SotisStyle();
        HttpClient client = new FuelClientFactory().buildClient();
        boorus.add(new Gelbooru(client));
    }

    public NavigatorHolder getNavigatorHolder() {
        return cicerone.getNavigatorHolder();
    }

    public Router getRouter() {
        return cicerone.getRouter();
    }

    public Style getStyle() {
        return style;
    }

    public List<Booru> getBooruList() {
        return boorus;
    }
}

