/**
 * Copyright 2010-2017 the original author or authors.
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

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * The Container annotation that aggregates several {@link MapperScan} annotations.
 *
 * <p>Can be used natively, declaring several nested {@link MapperScan} annotations.
 * Can also be used in conjunction with Java 8's support for repeatable annotations,
 * where {@link MapperScan} can simply be declared several times on the same method,
 * implicitly generating this container annotation.
 *
 * @author Kazuki Shimizu
 * @see MapperScan
 * @since 2.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(AutoMapperScannerRegistrar.RepeatingRegistrar.class)
public @interface AutoMapperScans {
    AutoMapperScan[] value();
}
