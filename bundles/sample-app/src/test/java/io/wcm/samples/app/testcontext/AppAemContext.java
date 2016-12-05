/*
 * #%L
 * wcm.io
 * %%
 * Copyright (C) 2014 wcm.io
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package io.wcm.samples.app.testcontext;

import static io.wcm.testing.mock.wcmio.config.ContextPlugins.WCMIO_CONFIG;
import static io.wcm.testing.mock.wcmio.handler.ContextPlugins.WCMIO_HANDLER;
import static io.wcm.testing.mock.wcmio.sling.ContextPlugins.WCMIO_SLING;

import java.io.IOException;

import org.apache.sling.api.resource.PersistenceException;

import io.wcm.config.spi.ApplicationProvider;
import io.wcm.config.spi.ConfigurationFinderStrategy;
import io.wcm.handler.media.spi.MediaFormatProvider;
import io.wcm.samples.app.config.impl.ApplicationProviderImpl;
import io.wcm.samples.app.config.impl.ConfigurationFinderStrategyImpl;
import io.wcm.samples.app.config.impl.MediaFormatProviderImpl;
import io.wcm.testing.mock.aem.junit.AemContext;
import io.wcm.testing.mock.aem.junit.AemContextBuilder;
import io.wcm.testing.mock.aem.junit.AemContextCallback;

/**
 * Sets up {@link AemContext} for unit tests in this application.
 */
public final class AppAemContext {

  public static final String CONTENT_ROOT = "/content/wcm-io-samples/en";
  public static final String DAM_ROOT = "/content/dam/wcm-io-samples";

  private AppAemContext() {
    // static methods only
  }

  public static AemContext newAemContext() {
    return new AemContextBuilder()
        .plugin(WCMIO_SLING, WCMIO_CONFIG, WCMIO_HANDLER)
        .afterSetUp(SETUP_CALLBACK)
        .build();
  }

  /**
   * Custom set up rules required in all unit tests.
   */
  private static final AemContextCallback SETUP_CALLBACK = new AemContextCallback() {
    @Override
    public void execute(AemContext context) throws PersistenceException, IOException {

      // setup handler
      context.registerService(ApplicationProvider.class, new ApplicationProviderImpl());
      context.registerService(ConfigurationFinderStrategy.class, new ConfigurationFinderStrategyImpl());
      context.registerService(MediaFormatProvider.class, new MediaFormatProviderImpl());

      // register sling models
      context.addModelsForPackage("io.wcm.samples.app");

      // import sample content
      context.load().json("/sample-content.json", CONTENT_ROOT);
      context.load().json("/sample-content-dam.json", DAM_ROOT);

      // set default current page
      context.currentPage("/content/wcm-io-samples/en");
    }
  };

}
