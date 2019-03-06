package com.admin.utils;

import com.sun.tools.classfile.Dependency;
import org.apache.commons.compress.utils.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StringUtils;
import sun.nio.ch.IOUtil;

import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @Auther: chaoqiang.zhou
 * @Date: 2019/3/6 15:09
 * @Description:
 */

public class Analyzer {
    private static final Logger logger = LoggerFactory.getLogger(Analyzer.class);

    private static List<PomInfo> getAllJarPomInfo() throws IOException {
        List<PomInfo> pomInfos = Lists.newArrayList();
        String metaPath = "META-INF";
        Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources(metaPath);
        while (urls.hasMoreElements()) {
            URL url = urls.nextElement();
            if (url != null && "jar".equals(url.getProtocol())) {
                String urlStr = url.toString();
                logger.debug("url-str: " + urlStr);
                String location = urlStr.substring(urlStr.indexOf('f'), urlStr.lastIndexOf('!'));
                logger.debug("location: " + location);
                readPomInfo(location, pomInfos);
            }
        }

        return pomInfos;
    }

    public static JarDependencies getAllPomInfo(String key) throws Exception {
        JarDependencies jarDependencies = new JarDependencies();
        List<PomInfo> pomInfos = getAllJarPomInfo();
        jarDependencies.setPomInfos(pomInfos);
        Optional<PomInfo> optionalPomInfo = pomInfos.stream().filter( //
                x -> (StringUtils.isNotEmpty(x.groupId) && x.groupId.equals("com.bkjk.platform.summerframework")))
                .findFirst();
        setSummerframeworkDeps(optionalPomInfo, jarDependencies);
        return jarDependencies;
    }

    private static PomInfo readPom(InputStream is) throws SAXException, IOException, ParserConfigurationException {
        if (null != is) {
            MavenXpp3Reader reader = new MavenXpp3Reader();
            try {
                Model model = reader.read(is);
                PomInfo pomInfo = new PomInfo();
                pomInfo.setArtifactId(model.getArtifactId());
                if (StringUtils.isEmpty(model.getGroupId())) {
                    pomInfo.setGroupId(model.getParent().getGroupId());
                } else {
                    pomInfo.setGroupId(model.getGroupId());
                }
                if (StringUtils.isEmpty(model.getVersion())) {
                    pomInfo.setVersion(model.getParent().getVersion());
                } else {
                    pomInfo.setVersion(model.getVersion());
                }
                List<Dependency> dependencies = model.getDependencies();
                List<PomDependency> pomDependencies = Lists.newArrayList();
                for (Dependency dependency : dependencies) {
                    PomDependency pomDependency = new PomDependency();
                    String groupId = dependency.getGroupId();
                    if (StringUtils.isNotEmpty(groupId) && (groupId.equals("${project.groupId}"))) {
                        groupId = pomInfo.groupId;
                    }
                    pomDependency.setGroupId(groupId);
                    pomDependency.setArtifactId(dependency.getArtifactId());
                    String version = dependency.getVersion();
                    if (StringUtils.isNotEmpty(version) && (version.startsWith("${") && version.endsWith("}"))) {
                        version = model.getProperties().getProperty(version.substring(2, version.length() - 1));
                    }
                    pomDependency.setVersion(version);

                    pomDependency.setScope(dependency.getScope());
                    pomDependencies.add(pomDependency);
                }
                pomInfo.setDependencies(pomDependencies);
                return pomInfo;
            } catch (XmlPullParserException e) {
                e.printStackTrace();
                logger.error("read pom failed!" + e.getMessage());
            }
        }

        return null;
    }

    private static void readPomInfo(String location, List<PomInfo> pomInfos) {
        Properties properties = new Properties();
        String metaPath = "META-INF";
        try {
            InputStream is;
            if (location.contains("!")) {
                // read inside jar's
                is = new ClassPathResource(location.substring(location.indexOf("!") + 1)).getInputStream();
            } else {
                URL realUrl = new URL(location);
                is = realUrl.openStream();
            }

            PomInfo pomInfo = null;
            ZipInputStream zip = new ZipInputStream(is);
            ZipEntry zipEntry;
            while ((zipEntry = zip.getNextEntry()) != null) {
                String zipEntryPath = zipEntry.getName();
                if (zipEntryPath.startsWith(metaPath + "/maven") && zipEntryPath.endsWith("pom.xml")) {
                    logger.debug("zipEntryPath: " + zipEntryPath);
                    pomInfo = readPom(zip);
                    break;
                }

                if (zipEntryPath.equals(metaPath + "/MANIFEST.MF")) {
                    properties.load(zip);
                }
            }

            // calculate jar size
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            IOUtil.copy(is, output);
            long jarSize = output.toByteArray().length;

            if (null != pomInfo) {
                pomInfo.setLocation(location);
                pomInfo.setSize(jarSize);
                pomInfos.add(pomInfo);
            }
        } catch (Exception e) {
            logger.error("get jar maven pom failed! location:" + location, e);
        }
    }

    private static void setSummerframeworkDeps(Optional<PomInfo> optionalPomInfo, //
                                               JarDependencies jarDependencies) throws Exception {
        if (optionalPomInfo.isPresent()) {
            PomInfo pomInfo = optionalPomInfo.get();
            String summerframeworkVersion = pomInfo.version;
            if (StringUtils.isNotEmpty(summerframeworkVersion)) {
                jarDependencies.setSummerframeworkVersion(summerframeworkVersion);
                MavenSearch mavenSearch = new NexusMavenSearch();
                InputStream is = mavenSearch.getPomInfoByFileName(//
                        new String[] {"summerframework-build-dependencies", summerframeworkVersion}, null);
                if (null != is) {
                    MavenXpp3Reader reader = new MavenXpp3Reader();
                    try {
                        Model model = reader.read(is);
                        Properties properties = model.getProperties();
                        jarDependencies.setSpringCloudVersion(properties.getProperty("spring-cloud.version"));
                        jarDependencies.setSpringBootVersion(properties.getProperty("spring-boot.version"));
                    } catch (XmlPullParserException e) {
                        e.printStackTrace();
                        logger.error("read pom failed!" + e.getMessage());
                    }
                }
            }
        }
    }
}