package com.lucky.boot.flag;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @Author:chaoqiang.zhou
 * @Description:可以动态控制加载类
 * @Date:Create in 15:33 2017/12/12
 */
public class ContentImportSelector implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        Class<?> annotationType = EnableContentService.class;
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(
                annotationType.getName(), false));
        String policy = attributes.getString("policy");
        if ("core".equals(policy)) {
            return new String[]{CoreContentService.CoreContentConfiguration.class.getName()};
        } else {
            return new String[]{CoreContentService.SimpleContentService.class.getName()};
        }
    }

}
