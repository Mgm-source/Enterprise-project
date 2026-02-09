package com.enterpriseproject.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.enterpriseproject.film.Films;
@Component
public class FilmRoute extends RouteBuilder{

    @Value("${ftpSetting.location}")
    String ftpLocation;
    @Value("${ftpSetting.user}")
    String userName;
    @Value("${ftpSetting.password}")
    String password;

    @Override
    public void configure() throws Exception {

        from("ftp://"+ ftpLocation + "?" + "username="+ userName+"&password="+ password +
             "&passiveMode=true" +
             "&move=.done/${file:name.noext}-${date:now:yyMMddHHmmss}.${file:ext}"
        ).to("jms:incomingFTPFilms");


        from("jms:incomingFTPFilms")
            .routeId("FilmsRouteQueue")
            .unmarshal().jacksonXml(Films.class)
            .split(body().method("getFilms"))
            .bean("filmProcessorBean", "process");
    }


}