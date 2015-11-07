package modules;

import com.google.inject.AbstractModule;

/**
 * This module binds ApplicationStart and ApplicationStop as eager singelton.
 *
 * @author Sebastian Sachtleben
 */
public class ApplicationModule extends AbstractModule {

   @Override
   protected void configure() {
      bind(ApplicationStart.class).asEagerSingleton();
      bind(ApplicationStop.class).asEagerSingleton();
   }

}