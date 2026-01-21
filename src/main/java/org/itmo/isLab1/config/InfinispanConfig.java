// package org.itmo.isLab1.config;

// import org.infinispan.manager.DefaultCacheManager;
// import org.infinispan.manager.EmbeddedCacheManager;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;

// @Configuration
// public class InfinispanConfig {

//     @Bean
//     public EmbeddedCacheManager embeddedCacheManager() throws Exception {
//         // Загружаем ваш XML конфиг Hibernate L2 cache
//         return new DefaultCacheManager(getClass().getResourceAsStream("org/infinispan/hibernate/cache/commons/builder/infinispan-configs-local.xml"));
//     }
// }