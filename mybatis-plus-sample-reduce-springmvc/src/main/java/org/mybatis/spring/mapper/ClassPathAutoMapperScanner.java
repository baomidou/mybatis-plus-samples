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
package org.mybatis.spring.mapper;

import com.baomidou.mybatisplus.annotation.TableName;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import javassist.bytecode.ClassFile;
import javassist.bytecode.SignatureAttribute;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.*;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.*;
import java.util.stream.Collectors;

/**
 * A {@link ClassPathBeanDefinitionScanner} that registers Mappers by
 * {@code basePackage}, {@code annotationClass}, or {@code markerInterface}. If
 * an {@code annotationClass} and/or {@code markerInterface} is specified, only
 * the specified types will be searched (searching for all interfaces will be
 * disabled).
 * <p>
 * This functionality was previously a private class of
 * {@link MapperScannerConfigurer}, but was broken out in version 1.2.0.
 *
 * @author Hunter Presnall
 * @author Eduardo Macarron
 * @see MapperFactoryBean
 * @since 1.2.0
 */
public class ClassPathAutoMapperScanner extends ClassPathBeanDefinitionScanner {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassPathAutoMapperScanner.class);

    private boolean addToConfig = true;

    private SqlSessionFactory sqlSessionFactory;

    private SqlSessionTemplate sqlSessionTemplate;

    private String sqlSessionTemplateBeanName;

    private String sqlSessionFactoryBeanName;

    private Class<? extends Annotation> annotationClass;

    private Class<?> markerInterface;

    private Class<? extends MapperFactoryBean> mapperFactoryBeanClass = MapperFactoryBean.class;


    private String[] beanPackages;

    private String makeMapperPackage;

    private String[] excludedBeans;

    private String superMapperName;

    public ClassPathAutoMapperScanner(BeanDefinitionRegistry registry) {
        super(registry, false);
    }

    public void setAddToConfig(boolean addToConfig) {
        this.addToConfig = addToConfig;
    }

    public void setAnnotationClass(Class<? extends Annotation> annotationClass) {
        this.annotationClass = annotationClass;
    }

    public void setMarkerInterface(Class<?> markerInterface) {
        this.markerInterface = markerInterface;
    }

    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    public void setSqlSessionTemplateBeanName(String sqlSessionTemplateBeanName) {
        this.sqlSessionTemplateBeanName = sqlSessionTemplateBeanName;
    }

    public void setSqlSessionFactoryBeanName(String sqlSessionFactoryBeanName) {
        this.sqlSessionFactoryBeanName = sqlSessionFactoryBeanName;
    }


    public String[] getBeanPackages() {
        return beanPackages;
    }

    public void setBeanPackages(String[] beanPackages) {
        this.beanPackages = beanPackages;
    }

    public String getMakeMapperPackage() {
        return makeMapperPackage;
    }

    public void setMakeMapperPackage(String makeMapperPackage) {
        this.makeMapperPackage = makeMapperPackage;
    }

    public String[] getExcludedBeans() {
        return excludedBeans;
    }

    public void setExcludedBeans(String[] excludedBeans) {
        this.excludedBeans = excludedBeans;
    }

    public String getSuperMapperName() {
        return superMapperName;
    }

    public void setSuperMapperName(String superMapperName) {
        this.superMapperName = superMapperName;
    }

    /**
     * @deprecated Since 2.0.1, Please use the {@link #setMapperFactoryBeanClass(Class)}.
     */
    @Deprecated
    public void setMapperFactoryBean(MapperFactoryBean<?> mapperFactoryBean) {
        this.mapperFactoryBeanClass = mapperFactoryBean == null ? MapperFactoryBean.class : mapperFactoryBean.getClass();
    }

    /**
     * Set the {@code MapperFactoryBean} class.
     *
     * @param mapperFactoryBeanClass the {@code MapperFactoryBean} class
     * @since 2.0.1
     */
    public void setMapperFactoryBeanClass(Class<? extends MapperFactoryBean> mapperFactoryBeanClass) {
        this.mapperFactoryBeanClass = mapperFactoryBeanClass == null ? MapperFactoryBean.class : mapperFactoryBeanClass;
    }

    /**
     * Configures parent scanner to search for the right interfaces. It can search
     * for all interfaces or just for those that extends a markerInterface or/and
     * those annotated with the annotationClass
     */
    public void registerFilters() {
        boolean acceptAllInterfaces = true;

        // if specified, use the given annotation and / or marker interface
        if (this.annotationClass != null) {
            addIncludeFilter(new AnnotationTypeFilter(this.annotationClass));
            acceptAllInterfaces = false;
        }

        // override AssignableTypeFilter to ignore matches on the actual marker interface
        if (this.markerInterface != null) {
            addIncludeFilter(new AssignableTypeFilter(this.markerInterface) {
                @Override
                protected boolean matchClassName(String className) {
                    return false;
                }
            });
            acceptAllInterfaces = false;
        }

        if (acceptAllInterfaces) {
            // default include filter that accepts all classes
            addIncludeFilter((metadataReader, metadataReaderFactory) -> true);
        }

        // exclude package-info.java
        addExcludeFilter((metadataReader, metadataReaderFactory) -> {
            String className = metadataReader.getClassMetadata().getClassName();
            return className.endsWith("package-info");
        });
    }

    /**
     * 在搜索之前为bean类
     */
    public Set<ScannedGenericBeanDefinition> scanBeans(String makeMapperPackage, String... beanPackages) {

        HashSet<ScannedGenericBeanDefinition> beans = new HashSet<>();

        try {
            for (String beanPackage : beanPackages) {
                String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                        resolveBasePackage(beanPackage) + '/' + "**/*.class";

                Resource[] resources = ((ResourcePatternResolver) getResourceLoader()).getResources(packageSearchPath);
                boolean traceEnabled = logger.isTraceEnabled();
                boolean debugEnabled = logger.isDebugEnabled();
                for (Resource resource : resources) {
                    if (traceEnabled) {
                        logger.trace("Scanning " + resource);
                    }
                    if (resource.isReadable()) {
                        try {
                            MetadataReader metadataReader = getMetadataReaderFactory().getMetadataReader(resource);
                            if (isCandidateBean(metadataReader)) {
                                ScannedGenericBeanDefinition sbd = new ScannedGenericBeanDefinition(metadataReader);
                                sbd.setResource(resource);
                                sbd.setSource(resource);
                                if (isCandidateBean(sbd)) {
                                    if (debugEnabled) {
                                        logger.debug("Identified candidate bean class: " + resource);
                                    }
                                    beans.add(sbd);
                                } else {
                                    if (debugEnabled) {
                                        logger.debug("Ignored because not a concrete top-level class: " + resource);
                                    }
                                }
                            } else {
                                if (traceEnabled) {
                                    logger.trace("Ignored because not matching any filter: " + resource);
                                }
                            }
                        } catch (Throwable ex) {
                            throw new BeanDefinitionStoreException(
                                    "Failed to read candidate component class: " + resource, ex);
                        }
                    } else {
                        if (traceEnabled) {
                            logger.trace("Ignored because not readable: " + resource);
                        }
                    }
                }
            }
        } catch (IOException ex) {
            throw new BeanDefinitionStoreException("I/O failure during classpath scanning", ex);
        }

        return beans;
    }

    /**
     * 获取目标类的类名
     *
     * @param beanClassName
     * @param makeMapperPackage
     * @return
     */
    public String getTargetClassName(String beanClassName, String makeMapperPackage) {

        int index = beanClassName.lastIndexOf(".");
        String beanSimpleName = beanClassName.substring(index + 1);
        String targetClassName = makeMapperPackage.replace(";", "") + "." + beanSimpleName + "Mapper";
        return targetClassName;

    }

    public String getTargetClassSimpleName(String beanClassName) {
        int index = beanClassName.lastIndexOf(".");
        String beanSimpleName = beanClassName.substring(index + 1);
        String targetClassSimpleName = beanSimpleName + "Mapper";
        return targetClassSimpleName;
    }

    /**
     *  从bean类构造Mapper类
     * @param sbd 找到的entity类，被包装为beanDefintion了
     * @param makeMapperPackage  存放的包
     * @param superInterfaceClazz 构造时继承的类，默认情况下是集成 BaseMapper
     * @return
     */
    private MapperClass makeMapperFromBean(ScannedGenericBeanDefinition sbd, String makeMapperPackage,Class  superInterfaceClazz ) {

        //Class superInterfaceClazz = BaseMapper.class;

        MapperClass retMapperClass = new MapperClass();
        String beanClassName = sbd.getMetadata().getClassName();
        String targetClassName = getTargetClassName(beanClassName, makeMapperPackage);


        Class mapperClass = null;
        try {
            mapperClass = Class.forName(targetClassName);
        } catch (ClassNotFoundException e) {
            logger.debug(targetClassName + " class not found", e);
        }

        if (mapperClass == null) {

            try {

                ClassPool classPool = ClassPool.getDefault();

                CtClass superInterface = classPool.getCtClass(superInterfaceClazz.getName());
                CtClass targetInterFace = classPool.makeInterface(targetClassName, superInterface);
                ClassFile classFile = targetInterFace.getClassFile();

                // 目标串形式: "Ljava/lang/Object;Lcom/baomidou/mybatisplus/core/mapper/BaseMapper<Lcom/baomidou/mybatisplus/samples/reduce/springmvc/entity/User;>;"

               String superInterfaceClazzSignature = "L"+superInterfaceClazz.getName().replace(".class", "").replace('.', '/');
                String signatureReturnType = "L"+"java/lang/Object;";

                //String paramterClassSignature ="com/baomidou/mybatisplus/samples/reduce/springmvc/entity/User";
                String paramterClassSignature = "L"+beanClassName.replace(".class", "").replace('.', '/') +";";
                String signature = signatureReturnType+ superInterfaceClazzSignature+"<" + paramterClassSignature + ">;";

                SignatureAttribute signatureAttribute = new SignatureAttribute(
                        classFile.getConstPool(),
                        signature);
                classFile.addAttribute(signatureAttribute);

                try {
                    byte[] bytecode = targetInterFace.toBytecode();
                    retMapperClass.setByteCodes(bytecode);
                } catch (IOException e) {
                    e.printStackTrace();
                }


                mapperClass = targetInterFace.toClass();

                retMapperClass.setMapperClass(mapperClass);

                //手工解除绑定，释放内存
                targetInterFace.detach();

            } catch (NotFoundException | CannotCompileException e) {
                e.printStackTrace();
            }

        }
        return retMapperClass;
    }

    /**
     * Calls the parent search that will search and register all the candidates.
     * Then the registered objects are post processed to set them as
     * MapperFactoryBeans
     */
    @Override
    public Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);


        boolean b = beanDefinitions.stream().anyMatch(x -> x.getBeanName().equals("businessUpdateMapper"));

        //扫描beanmap 如果没有相关的 beanDefinition 则进行添加

        Set<ScannedGenericBeanDefinition> entityDefinitions = scanBeans(this.getMakeMapperPackage(), this.getBeanPackages());

        //计算出 entityDefinitions  中不存在在 beanDefinitions 的bean

        Set<String> beanDefinitionSet = beanDefinitions.stream().map(x -> x.getBeanName()).collect(Collectors.toSet());

        List<ScannedGenericBeanDefinition> missMapperBeans = entityDefinitions.stream().filter(x -> !beanDefinitionSet.contains(StringUtils.uncapitalize(getTargetClassSimpleName(x.getMetadata().getClassName())))).collect(Collectors.toList());


       //对于明确不需要生成mapper类的bean，过滤的掉
        List<ScannedGenericBeanDefinition> finalMissMapperBeans  = null;
       if(this.excludedBeans ==null) {
           finalMissMapperBeans = missMapperBeans;
       }else{
           // 使用正则的方式会不方便，转换成支持通配符的uri
           AntPathMatcher pathMatcher = new AntPathMatcher();

           finalMissMapperBeans = missMapperBeans.stream().filter(  x ->
                        ! Arrays.stream(this.excludedBeans).anyMatch(
                           excludingBean -> pathMatcher.match(excludingBean.replace(".","/"),x.getBeanClassName().replace(".","/")) )
                   ) .collect(Collectors.toList());
       }

        Class superMapperClazz = getSuperMapperByName(this.superMapperName);

        //处理这些丢失的mapper，从bean中转换成相应的mapper 的beanDefinition;
        Set<BeanDefinitionHolder> missingMapper = makeMapperDefinitionsFromBeans(finalMissMapperBeans, this.getMakeMapperPackage(),superMapperClazz );

        beanDefinitions.addAll(missingMapper);

        if (beanDefinitions.isEmpty()) {
            LOGGER.warn(() -> "No MyBatis mapper was found in '" + Arrays.toString(basePackages) + "' package. Please check your configuration.");
        } else {
            processBeanDefinitions(beanDefinitions);
        }

        return beanDefinitions;
    }

    /**
     * @param missMapperBeans
     * @param makeMapperPackage
     * @return
     */
    private Set<BeanDefinitionHolder> makeMapperDefinitionsFromBeans(List<ScannedGenericBeanDefinition> missMapperBeans, String makeMapperPackage,Class superInterfaceClazz ) {

        Set<BeanDefinitionHolder> beanDefinitions = new LinkedHashSet<>();

        for (ScannedGenericBeanDefinition sbd : missMapperBeans) {
            MapperClass mapperClass = makeMapperFromBean(sbd, makeMapperPackage,superInterfaceClazz);
            String targetClassPath = getTargetClassName(sbd.getMetadata().getClassName(),
                    makeMapperPackage).replace('.', '/');
            //Resource resource =  new ClassPathResource(targetClassPath,mapperClass);
            Resource resource = new ByteArrayResource(mapperClass.getByteCodes());

            try {
                MetadataReader metadataReader = getMetadataReaderFactory().getMetadataReader(resource);

                if (isCandidateComponent(metadataReader)) {
                    ScannedGenericBeanDefinition candidate = new ScannedGenericBeanDefinition(metadataReader);
                    candidate.setResource(resource);
                    candidate.setSource(resource);

                    ScopeMetadata scopeMetadata = (new AnnotationScopeMetadataResolver()).resolveScopeMetadata(candidate);
                    candidate.setScope(scopeMetadata.getScopeName());
                    String beanName = (new AnnotationBeanNameGenerator()).generateBeanName(candidate, getRegistry());
                    if (candidate instanceof AbstractBeanDefinition) {
                        postProcessBeanDefinition((AbstractBeanDefinition) candidate, beanName);
                    }
                    if (candidate instanceof AnnotatedBeanDefinition) {
                        AnnotationConfigUtils.processCommonDefinitionAnnotations((AnnotatedBeanDefinition) candidate);
                    }
                    if (checkCandidate(beanName, candidate)) {
                        BeanDefinitionHolder definitionHolder = new BeanDefinitionHolder(candidate, beanName);

                        //todo:使用反射绕过 权限来执行
                        //definitionHolder =
                        //        AnnotationConfigUtils.applyScopedProxyMode(scopeMetadata, definitionHolder, getRegistry());
                        beanDefinitions.add(definitionHolder);
                        registerBeanDefinition(definitionHolder, getRegistry());
                    }


                }

            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        return beanDefinitions;

    }

    private void processBeanDefinitions(Set<BeanDefinitionHolder> beanDefinitions) {
        GenericBeanDefinition definition;
        for (BeanDefinitionHolder holder : beanDefinitions) {
            definition = (GenericBeanDefinition) holder.getBeanDefinition();
            String beanClassName = definition.getBeanClassName();
            LOGGER.debug(() -> "Creating MapperFactoryBean with name '" + holder.getBeanName()
                    + "' and '" + beanClassName + "' mapperInterface");

            // the mapper interface is the original class of the bean
            // but, the actual class of the bean is MapperFactoryBean
            definition.getConstructorArgumentValues().addGenericArgumentValue(beanClassName); // issue #59
            definition.setBeanClass(this.mapperFactoryBeanClass);

            definition.getPropertyValues().add("addToConfig", this.addToConfig);

            boolean explicitFactoryUsed = false;
            if (StringUtils.hasText(this.sqlSessionFactoryBeanName)) {
                definition.getPropertyValues().add("sqlSessionFactory", new RuntimeBeanReference(this.sqlSessionFactoryBeanName));
                explicitFactoryUsed = true;
            } else if (this.sqlSessionFactory != null) {
                definition.getPropertyValues().add("sqlSessionFactory", this.sqlSessionFactory);
                explicitFactoryUsed = true;
            }

            if (StringUtils.hasText(this.sqlSessionTemplateBeanName)) {
                if (explicitFactoryUsed) {
                    LOGGER.warn(() -> "Cannot use both: sqlSessionTemplate and sqlSessionFactory together. sqlSessionFactory is ignored.");
                }
                definition.getPropertyValues().add("sqlSessionTemplate", new RuntimeBeanReference(this.sqlSessionTemplateBeanName));
                explicitFactoryUsed = true;
            } else if (this.sqlSessionTemplate != null) {
                if (explicitFactoryUsed) {
                    LOGGER.warn(() -> "Cannot use both: sqlSessionTemplate and sqlSessionFactory together. sqlSessionFactory is ignored.");
                }
                definition.getPropertyValues().add("sqlSessionTemplate", this.sqlSessionTemplate);
                explicitFactoryUsed = true;
            }

            if (!explicitFactoryUsed) {
                LOGGER.debug(() -> "Enabling autowire by type for MapperFactoryBean with name '" + holder.getBeanName() + "'.");
                definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
            }
        }
    }

    /**
     * 是否是指定的 生成bean 要求要有 tablename 注解的
     */
    protected boolean isCandidateBean(MetadataReader metadataReader) {
        return metadataReader.getAnnotationMetadata().hasAnnotation(TableName.class.getName());
    }


    /**
     * 是否是指定的 生成bean 要求要有 tablename 注解的
     */
    protected boolean isCandidateBean(AnnotatedBeanDefinition beanDefinition) {
        return beanDefinition.getMetadata().hasAnnotation(TableName.class.getName());
    }


    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().isIndependent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean checkCandidate(String beanName, BeanDefinition beanDefinition) {
        if (super.checkCandidate(beanName, beanDefinition)) {
            return true;
        } else {
            LOGGER.warn(() -> "Skipping MapperFactoryBean with name '" + beanName
                    + "' and '" + beanDefinition.getBeanClassName() + "' mapperInterface"
                    + ". Bean already defined with the same name!");
            return false;
        }
    }


    /**
     * 从配置的名字获取父类mapper接口的名字
     * 如果没有特定配置，默认应该是BaseMapper
     * @param superMapperName  父mapper类名称
     * @return
     */
    private Class getSuperMapperByName(String superMapperName){
        Class clazz = null;
        try {
            clazz = Class.forName(superMapperName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return clazz;
    }

}
