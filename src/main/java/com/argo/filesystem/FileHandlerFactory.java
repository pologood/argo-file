package com.argo.filesystem;

import com.argo.filesystem.handler.FileRequestDateHandler;
import com.argo.filesystem.handler.FileRequestHashHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by yamingd on 9/10/15.
 */
@Component
public class FileHandlerFactory {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public static FileRequestHandler date;
    public static FileRequestHandler hash;

    public FileHandlerFactory(){
        try {
            FilesConfig.load();
        } catch (IOException e) {
            logger.error("Load filesConfig Error. Please check your " + FilesConfig.confName);
            return;
        }

        date = new FileRequestDateHandler();
        hash = new FileRequestHashHandler();
    }


}
