/**
 * Copyright 2010-2018 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package spring.annotation;

import org.mybatis.spring.annotation.MapperScannerRegistrar;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Use this annotation to register MyBatis mapper interfaces when using Java
 * Config. It performs when same work as {@link MapperScannerConfigurer} via
 * {@link MapperScannerRegistrar}.
 *
 * <p>Configuration example:</p>
 * <pre class="code">
 * &#064;Configuration
 * &#064;AutoMapperScan("org.mybatis.spring.sample.mapper")
 * public class AppConfig {
 *
 *   &#064;Bean
 *   public DataSource dataSource() {
 *     return new EmbeddedDatabaseBuilder()
 *              .addScript("schema.sql")
 *              .build();
 *   }
 *
 *   &#064;Bean
 *   public DataSourceTransactionManager transactionManager() {
 *     return new DataSourceTransactionManager(dataSource());
 *   }
 *
 *   &#064;Bean
 *   public SqlSessionFactory sqlSessionFactory() throws Exception {
 *     SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
 *     sessionFactory.setDataSource(dataSource());
 *     return sessionFactory.getObject();
 *   }
 * }
 * </pre>
 *
 * @author Michael Lanyon
 * @author Eduardo Macarron
 * @see MapperScannerRegistrar
 * @see MapperFactoryBean
 * @since 1.2.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(AutoMapperScannerRegistrar.class)
@Repeatable(AutoMapperScans.class)
public @interface AutoMapperScan {

    /**
     * Alias for the {@link #basePackages()} attribute. Allows for more concise
     * annotation declarations e.g.:
     * {@code @AutoMapperScan("org.my.pkg")} instead of {@code @AutoMapperScan(basePackages = "org.my.pkg"})}.
     *
     * @return base package names
     */
    String[] value() default {};

    /**
     * Base packages to scan for MyBatis interfaces. Note that only interfaces
     * with at least one method will be registered; concrete classes will be
     * ignored.
     *
     * @return base package names for scanning mapper interface
     */
    String[] basePackages() default {};


    /**
     * 新增一个 bean类的目录， 在加载之前 扫描程序会先扫描 该bean下面的类然后动态生mapper类
     *
     * @return
     */
    String[] beanPackages() default {};

    /**
     * 生成的mapper类所存放的位置，目前只能存放在一个地方
     *
     * @return
     */
    String makeMapperPackage() default "";


    /**
     * Type-safe alternative to {@link #basePackages()} for specifying the packages
     * to scan for annotated components. The package of each class specified will be scanned.
     * <p>Consider creating a special no-op marker class or interface in each package
     * that serves no purpose other than being referenced by this attribute.
     *
     * @return classes that indicate base package for scanning mapper interface
     */
    Class<?>[] basePackageClasses() default {};

    /**
     * The {@link BeanNameGenerator} class to be used for naming detected components
     * within the Spring container.
     *
     * @return the class of {@link BeanNameGenerator}
     */
    Class<? extends BeanNameGenerator> nameGenerator() default BeanNameGenerator.class;

    /**
     * This property specifies the annotation that the scanner will search for.
     * <p>
     * The scanner will register all interfaces in the base package that also have
     * the specified annotation.
     * <p>
     * Note this can be combined with markerInterface.
     *
     * @return the annotation that the scanner will search for
     */
    Class<? extends Annotation> annotationClass() default Annotation.class;

    /**
     * This property specifies the parent that the scanner will search for.
     * <p>
     * The scanner will register all interfaces in the base package that also have
     * the specified interface class as a parent.
     * <p>
     * Note this can be combined with annotationClass.
     *
     * @return the parent that the scanner will search for
     */
    Class<?> markerInterface() default Class.class;

    /**
     * Specifies which {@code SqlSessionTemplate} to use in the case that there is
     * more than one in the spring context. Usually this is only needed when you
     * have more than one datasource.
     *
     * @return the bean name of {@code SqlSessionTemplate}
     */
    String sqlSessionTemplateRef() default "";

    /**
     * Specifies which {@code SqlSessionFactory} to use in the case that there is
     * more than one in the spring context. Usually this is only needed when you
     * have more than one datasource.
     *
     * @return the bean name of {@code SqlSessionFactory}
     */
    String sqlSessionFactoryRef() default "";

    /**
     * Specifies a custom MapperFactoryBean to return a mybatis proxy as spring bean.
     *
     * @return the class of {@code MapperFactoryBean}
     */
    Class<? extends MapperFactoryBean> factoryBean() default MapperFactoryBean.class;

}
