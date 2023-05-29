package com.onyx.onyxapi;

import com.onyx.onyxapi.config.OnyxApiBeans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Import;

@EnableAutoConfiguration
@Import(OnyxApiBeans.class)
public class OnyxApiApplication {

	public static void main(String args[]) {
		SpringApplication.run(OnyxApiApplication.class, args);
	}

}

//heroku logs --app onyx-api-v2 --tail