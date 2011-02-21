/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2010, Red Hat Middleware LLC, and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jboss.test.har2arq.deployment.test;

import org.jboss.arquillian.spi.ApplicationArchiveGenerator;
import org.jboss.arquillian.spi.TestClass;
import org.jboss.har2arq.Har2ArqApplicationArchiveGenerator;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.test.har2arq.deployment.support.EarTestClass;
import org.jboss.test.har2arq.deployment.support.WarTestClass;

import org.junit.Assert;
import org.junit.Test;

/**
 * Deployment type tests.
 *
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
public class DeploymentTest
{
   @Test
   public void testJars() throws Exception
   {
   }

   @Test
   public void testWars() throws Exception
   {
      WebArchive archive = toArchive(WarTestClass.class, WebArchive.class);
      // TODO
   }

   @Test
   public void testEars() throws Exception
   {
      EnterpriseArchive archive = toArchive(EarTestClass.class, EnterpriseArchive.class);
      // TODO
   }

   protected <T extends Archive> T toArchive(Class<?> testClass, Class<T> expectedArchiveType)
   {
      ApplicationArchiveGenerator aag = new Har2ArqApplicationArchiveGenerator();
      Archive<?> archive = aag.generateApplicationArchive(new TestClass(testClass));
      Assert.assertTrue(expectedArchiveType.isInstance(archive));
      return expectedArchiveType.cast(archive);
   }
}
