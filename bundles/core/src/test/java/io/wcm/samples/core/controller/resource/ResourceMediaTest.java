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
package io.wcm.samples.core.controller.resource;

import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;

import io.wcm.samples.core.testcontext.AppAemContext;
import io.wcm.testing.mock.aem.junit.AemContext;

public class ResourceMediaTest {

  @Rule
  public final AemContext context = AppAemContext.newAemContext();

  @Test
  public void testMedia() {
    context.currentResource("/content/wcm-io-samples/en/jcr:content/teaserbar/teaserbaritem");
    context.request().setAttribute("mediaFormat", "content_480");

    ResourceMedia underTest = context.request().adaptTo(ResourceMedia.class);
    assertTrue(underTest.isValid());
  }

}
