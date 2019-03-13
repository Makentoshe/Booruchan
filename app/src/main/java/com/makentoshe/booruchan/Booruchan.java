package com.makentoshe.booruchan;


import android.app.Application;
import com.makentoshe.booruchan.api.Booru;
import com.makentoshe.booruchan.api.gelbooru.Gelbooru;
import com.makentoshe.booruchan.navigation.Router;
import com.makentoshe.style.SotisStyle;
import com.makentoshe.style.Style;
import io.reactivex.exceptions.UndeliverableException;
import io.reactivex.functions.Consumer;
import io.reactivex.plugins.RxJavaPlugins;
import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.NavigatorHolder;

import java.util.ArrayList;
import java.util.List;

public final class Booruchan extends Application {

    public static Booruchan INSTANCE;
    private Cicerone<Router> cicerone;
    private Style style;
    private List<Class<? extends Booru>> boorus = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        cicerone = Cicerone.create(new Router());
        initRxErrorHandler();
        loadStyle();
        loadBooru();
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

    private void loadStyle() {
        style = new SotisStyle();
    }
//
    private void loadBooru() {
        boorus.add(Gelbooru.class);
    }
//
//    private void load() {
//        HttpClient client = new FuelClientFactory().buildClient();
//        boorus.add(new Gelbooru(client));
//    }

    public NavigatorHolder getNavigatorHolder() {
        return cicerone.getNavigatorHolder();
    }

    public Router getRouter() {
        return cicerone.getRouter();
    }

    public Style getStyle() {
        return style;
    }

    public List<Class<? extends Booru>> getBooruList() {
        return boorus;
    }
}

