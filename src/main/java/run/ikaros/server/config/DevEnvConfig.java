package run.ikaros.server.config;

import run.ikaros.server.utils.FileUtils;
import run.ikaros.server.utils.SystemVarUtils;
import java.io.File;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

/**
 * @author guohao
 * @date 2022/10/04
 */
@Configuration
@ConditionalOnProperty(name = "ikaros.env", havingValue = "dev")
public class DevEnvConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(DevEnvConfig.class);

    // 开发环境下，启动时清空upload文件夹
    @PostConstruct
    public void cleanUploadDirWhenExist() {
        String uploadDirPath =
            SystemVarUtils.getCurrentAppDirPath() + File.separatorChar + "upload";
        FileUtils.deleteDirByRecursion(uploadDirPath);
        LOGGER.debug("clear upload dir after start.");
    }
}