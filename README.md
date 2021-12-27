# Enterprise-project

This is the work i completed for my software engineering degree.

## Includes 

* Standard web application (local) and remote

* A restful web application

* WSDL description interface and SOAP web service

# HTTP Interface

|SERVLET-NAME | SERVLET-URL | PARAMETER-KEYS ({ERROR} AS WELL BUT ONLY WHEN PARAMETER KEY ISN’T RECOGNISED)|
| :---        | :---        | :---                                                                         |
|FILMSERVLET  | /Films      | {id}, {title}, {format}, {year}, {page}, {form},{card}, {search}             |
|DELETESERVLET| /Delete     | {id}                                                                         |
|INSERTSERVLET| /Insert     | {title, stars, director, review, year}                                       |
|UPDATESERVLET| /Update     | {id, title, stars, director, review, year                                    |

# Resful Interface

|PATHS                                            | METHOD                         | 
|:---                                             |:---                            |
|HTTP://LOCALHOST:8080/FILMSERVICEREST/FILMS/{ID} | Get – Film by ID               |
|HTTP://LOCALHOST:8080/FILMSERVICEREST/FILMS      |Get – All Films                 |
|HTTP://LOCALHOST:8080/FILMSERVICEREST/FILMS      |Post – Sends/Inserts a new Film |
|HTTP://LOCALHOST:8080/FILMSERVICEREST/FILMS/{ID} |Put – Updates a film by ID      |
|HTTP://LOCALHOST:8080/FILMSERVICEREST/FILMS/{ID} |Delete – Deletes a film by ID   |