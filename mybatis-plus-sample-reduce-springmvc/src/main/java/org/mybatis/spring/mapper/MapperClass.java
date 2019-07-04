/**
 * Copyright (C), 2005-2019, 深圳市珍爱网信息技术有限公司
 */

package org.mybatis.spring.mapper;

/**
 * @Description: TODO
 * @Author: Administrator
 * @Date: 2019/5/24 23:59
 * @Version V1.0
 */
public class MapperClass {

    Class mapperClass;
    byte[] byteCodes;

    public Class getMapperClass() {
        return mapperClass;
    }

    public void setMapperClass(Class mapperClass) {
        this.mapperClass = mapperClass;
    }

    public byte[] getByteCodes() {
        return byteCodes;
    }

    public void setByteCodes(byte[] byteCodes) {
        this.byteCodes = byteCodes;
    }
}
