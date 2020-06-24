package br.com.clminfo.southsystem.config;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.clminfo.southsystem.services.BatchFilesService;

@Configuration
public class FolderWatcherConfig {
	
	private static final String IN_FOLDER = "/data/in";

	private String homePath;
	
	@Autowired
	private BatchFilesService service;
	
	public FolderWatcherConfig(@Value("${HOMEPATH}") String homePath) {
		this.homePath = homePath;
	}
	
	@Bean
	public void fileSystemWatcher() throws IOException, InterruptedException {
		
		WatchService watchService = FileSystems.getDefault().newWatchService();
		Path path = Paths.get(homePath + IN_FOLDER);
		
		path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
		
		WatchKey key;
		while ((key = watchService.take()) != null) {
			for (WatchEvent<?> event : key.pollEvents()) {
				if(event.context().toString().endsWith(".dat")) {					
					service.processBatch();
				}
			}
			key.reset();
		}
		
	}

}
