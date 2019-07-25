/**
 * Copyright 2010-2019 the original author or authors.
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
package org.mybatis.spring.annotation;

import org.mybatis.spring.mapper.ClassPathAutoMapperScanner;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * A {@link ImportBeanDefinitionRegistrar} to allow annotation configuration of
 * MyBatis mapper scanning. Using an @Enable annotation allows beans to be
 * registered via @Component configuration, whereas implementing
 * {@code BeanDefinitionRegistryPostProcessor} will work for XML configuration.
 *
 * @author Michael Lanyon
 * @author Eduardo Macarron
 * @author Putthiphong Boonphong
 * @author alan2lin
 * @see MapperFactoryBean
 * @see ClassPathAutoMapperScanner
 * @since 1.2.0
 */
public class AutoMapperScannerRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware {

    private ResourceLoader resourceLoader;

    /**
     * {@inheritDoc}
     */
    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        AnnotationAttributes mapperScanAttrs = AnnotationAttributes
                .fromMap(importingClassMetadata.getAnnotationAttributes(AutoMapperScan.class.getName()));
        if (mapperScanAttrs != null) {
            registerBeanDefinitions(mapperScanAttrs, registry);
        }
    }

    void registerBeanDefinitions(AnnotationAttributes annoAttrs, BeanDefinitionRegistry registry) {

        ClassPathAutoMapperScanner scanner = new ClassPathAutoMapperScanner(registry);

        // this check is needed in Spring 3.1
        Optional.ofNullable(resourceLoader).ifPresent(scanner::setResourceLoader);

        Class<? extends Annotation> annotationClass = annoAttrs.getClass("annotationClass");
        if (!Annotation.class.equals(annotationClass)) {
            scanner.setAnnotationClass(annotationClass);
        }

        Class<?> markerInterface = annoAttrs.getClass("markerInterface");
        if (!Class.class.equals(markerInterface)) {
            scanner.setMarkerInterface(markerInterface);
        }

        Class<? extends BeanNameGenerator> generatorClass = annoAttrs.getClass("nameGenerator");
        if (!BeanNameGenerator.class.equals(generatorClass)) {
            scanner.setBeanNameGenerator(BeanUtils.instantiateClass(generatorClass));
        }

        Class<? extends MapperFactoryBean> mapperFactoryBeanClass = annoAttrs.getClass("factoryBean");
        if (!MapperFactoryBean.class.equals(mapperFactoryBeanClass)) {
            scanner.setMapperFactoryBeanClass(mapperFactoryBeanClass);
        }

        scanner.setSqlSessionTemplateBeanName(annoAttrs.getString("sqlSessionTemplateRef"));
        scanner.setSqlSessionFactoryBeanName(annoAttrs.getString("sqlSessionFactoryRef"));

        List<String> basePackages = new ArrayList<>();
        basePackages.addAll(
                Arrays.stream(annoAttrs.getStringArray("value"))
                        .filter(StringUtils::hasText)
                        .collect(Collectors.toList()));

        basePackages.addAll(
                Arrays.stream(annoAttrs.getStringArray("basePackages"))
                        .filter(StringUtils::hasText)
                        .collect(Collectors.toList()));

        basePackages.addAll(
                Arrays.stream(annoAttrs.getClassArray("basePackageClasses"))
                        .map(ClassUtils::getPackageName)
                        .collect(Collectors.toList()));

        scanner.registerFilters();

        List<String> beanPackages = new ArrayList<>();
        beanPackages.addAll(
                Arrays.stream(annoAttrs.getStringArray("beanPackages"))
                        .filter(StringUtils::hasText)
                        .collect(Collectors.toList()));

        if (basePackages.isEmpty()) {
            return;
        }
        scanner.setBeanPackages(StringUtils.toStringArray(beanPackages));



       if( annoAttrs.get("excludedBeans") != null){
           List<String> excludingBeans = new ArrayList<>();
           excludingBeans.addAll(
                   Arrays.stream(annoAttrs.getStringArray("excludedBeans"))
                           .filter(StringUtils::hasText)
                           .collect(Collectors.toList()));
           scanner.setExcludedBeans(StringUtils.toStringArray(excludingBeans));
       }

        scanner.setSuperMapperName(annoAttrs.getString("superMapperClassName"));

        //获取生成目录
        String tmpPackage = annoAttrs.getString("makeMapperPackage");
        String makeMapperPackage = "".equals(tmpPackage) ? basePackages.get(0) : tmpPackage;

        //在开始扫描之前 先扫描所有的 beanPackages里面的bean类，然后在目标目录生成mapper类
        scanner.scanBeans(makeMapperPackage, StringUtils.toStringArray(beanPackages));


        scanner.setMakeMapperPackage(makeMapperPackage);


        scanner.doScan(StringUtils.toStringArray(basePackages));
    }

    /**
     * A {@link org.mybatis.spring.annotation.MapperScannerRegistrar} for {@link MapperScans}.
     *
     * @since 2.0.0
     */
    static class RepeatingRegistrar extends AutoMapperScannerRegistrar {
        /**
         * {@inheritDoc}
         */
        @Override
        public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata,
                                            BeanDefinitionRegistry registry) {
            AnnotationAttributes mapperScansAttrs = AnnotationAttributes
                    .fromMap(importingClassMetadata.getAnnotationAttributes(AutoMapperScans.class.getName()));
            if (mapperScansAttrs != null) {
                Arrays.stream(mapperScansAttrs.getAnnotationArray("value"))
                        .forEach(mapperScanAttrs -> registerBeanDefinitions(mapperScanAttrs, registry));
            }
        }
    }

}
