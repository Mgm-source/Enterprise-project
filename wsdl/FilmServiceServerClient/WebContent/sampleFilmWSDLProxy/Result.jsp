<%@page contentType="text/html;charset=UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<HTML>
<HEAD>
<TITLE>Result</TITLE>
</HEAD>
<BODY>
<H1>Result</H1>

<jsp:useBean id="sampleFilmWSDLProxyid" scope="session" class="wdsl.FilmWSDLProxy" />
<%
if (request.getParameter("endpoint") != null && request.getParameter("endpoint").length() > 0)
sampleFilmWSDLProxyid.setEndpoint(request.getParameter("endpoint"));
%>

<%
String method = request.getParameter("method");
int methodID = 0;
if (method == null) methodID = -1;

if(methodID != -1) methodID = Integer.parseInt(method);
boolean gotMethod = false;

try {
switch (methodID){ 
case 2:
        gotMethod = true;
        java.lang.String getEndpoint2mtemp = sampleFilmWSDLProxyid.getEndpoint();
if(getEndpoint2mtemp == null){
%>
<%=getEndpoint2mtemp %>
<%
}else{
        String tempResultreturnp3 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(getEndpoint2mtemp));
        %>
        <%= tempResultreturnp3 %>
        <%
}
break;
case 5:
        gotMethod = true;
        String endpoint_0id=  request.getParameter("endpoint8");
            java.lang.String endpoint_0idTemp = null;
        if(!endpoint_0id.equals("")){
         endpoint_0idTemp  = endpoint_0id;
        }
        sampleFilmWSDLProxyid.setEndpoint(endpoint_0idTemp);
break;
case 10:
        gotMethod = true;
        wdsl.FilmWSDL getFilmWSDL10mtemp = sampleFilmWSDLProxyid.getFilmWSDL();
if(getFilmWSDL10mtemp == null){
%>
<%=getFilmWSDL10mtemp %>
<%
}else{
%>
<TABLE>
<TR>
<TD COLSPAN="3" ALIGN="LEFT">returnp:</TD>
</TABLE>
<%
}
break;
case 15:
        gotMethod = true;
        String id_1id=  request.getParameter("id18");
        int id_1idTemp  = Integer.parseInt(id_1id);
        java.lang.String getFilmByID15mtemp = sampleFilmWSDLProxyid.getFilmByID(id_1idTemp);
if(getFilmByID15mtemp == null){
%>
<%=getFilmByID15mtemp %>
<%
}else{
        String tempResultreturnp16 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(getFilmByID15mtemp));
        %>
        <%= tempResultreturnp16 %>
        <%
}
break;
case 20:
        gotMethod = true;
        String title_2id=  request.getParameter("title23");
            java.lang.String title_2idTemp = null;
        if(!title_2id.equals("")){
         title_2idTemp  = title_2id;
        }
        java.lang.String getFilmbyTitle20mtemp = sampleFilmWSDLProxyid.getFilmbyTitle(title_2idTemp);
if(getFilmbyTitle20mtemp == null){
%>
<%=getFilmbyTitle20mtemp %>
<%
}else{
        String tempResultreturnp21 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(getFilmbyTitle20mtemp));
        %>
        <%= tempResultreturnp21 %>
        <%
}
break;
case 25:
        gotMethod = true;
        String year_3id=  request.getParameter("year28");
        int year_3idTemp  = Integer.parseInt(year_3id);
        java.lang.String getFilmByYear25mtemp = sampleFilmWSDLProxyid.getFilmByYear(year_3idTemp);
if(getFilmByYear25mtemp == null){
%>
<%=getFilmByYear25mtemp %>
<%
}else{
        String tempResultreturnp26 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(getFilmByYear25mtemp));
        %>
        <%= tempResultreturnp26 %>
        <%
}
break;
case 30:
        gotMethod = true;
        String stars_5id=  request.getParameter("stars35");
            java.lang.String stars_5idTemp = null;
        if(!stars_5id.equals("")){
         stars_5idTemp  = stars_5id;
        }
        String review_6id=  request.getParameter("review37");
            java.lang.String review_6idTemp = null;
        if(!review_6id.equals("")){
         review_6idTemp  = review_6id;
        }
        String director_7id=  request.getParameter("director39");
            java.lang.String director_7idTemp = null;
        if(!director_7id.equals("")){
         director_7idTemp  = director_7id;
        }
        String year_8id=  request.getParameter("year41");
        int year_8idTemp  = Integer.parseInt(year_8id);
        String title_9id=  request.getParameter("title43");
            java.lang.String title_9idTemp = null;
        if(!title_9id.equals("")){
         title_9idTemp  = title_9id;
        }
        String id_10id=  request.getParameter("id45");
        int id_10idTemp  = Integer.parseInt(id_10id);
        %>
        <jsp:useBean id="film1Film_4id" scope="session" class="film.Film" />
        <%
        film1Film_4id.setStars(stars_5idTemp);
        film1Film_4id.setReview(review_6idTemp);
        film1Film_4id.setDirector(director_7idTemp);
        film1Film_4id.setYear(year_8idTemp);
        film1Film_4id.setTitle(title_9idTemp);
        film1Film_4id.setId(id_10idTemp);
        boolean updateFilm30mtemp = sampleFilmWSDLProxyid.updateFilm(film1Film_4id);
        String tempResultreturnp31 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(updateFilm30mtemp));
        %>
        <%= tempResultreturnp31 %>
        <%
break;
case 47:
        gotMethod = true;
        String stars_12id=  request.getParameter("stars52");
            java.lang.String stars_12idTemp = null;
        if(!stars_12id.equals("")){
         stars_12idTemp  = stars_12id;
        }
        String review_13id=  request.getParameter("review54");
            java.lang.String review_13idTemp = null;
        if(!review_13id.equals("")){
         review_13idTemp  = review_13id;
        }
        String director_14id=  request.getParameter("director56");
            java.lang.String director_14idTemp = null;
        if(!director_14id.equals("")){
         director_14idTemp  = director_14id;
        }
        String year_15id=  request.getParameter("year58");
        int year_15idTemp  = Integer.parseInt(year_15id);
        String title_16id=  request.getParameter("title60");
            java.lang.String title_16idTemp = null;
        if(!title_16id.equals("")){
         title_16idTemp  = title_16id;
        }
        String id_17id=  request.getParameter("id62");
        int id_17idTemp  = Integer.parseInt(id_17id);
        %>
        <jsp:useBean id="film1Film_11id" scope="session" class="film.Film" />
        <%
        film1Film_11id.setStars(stars_12idTemp);
        film1Film_11id.setReview(review_13idTemp);
        film1Film_11id.setDirector(director_14idTemp);
        film1Film_11id.setYear(year_15idTemp);
        film1Film_11id.setTitle(title_16idTemp);
        film1Film_11id.setId(id_17idTemp);
        boolean insertFilm47mtemp = sampleFilmWSDLProxyid.insertFilm(film1Film_11id);
        String tempResultreturnp48 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(insertFilm47mtemp));
        %>
        <%= tempResultreturnp48 %>
        <%
break;
case 64:
        gotMethod = true;
        String id_18id=  request.getParameter("id67");
        int id_18idTemp  = Integer.parseInt(id_18id);
        boolean deleteFilm64mtemp = sampleFilmWSDLProxyid.deleteFilm(id_18idTemp);
        String tempResultreturnp65 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(deleteFilm64mtemp));
        %>
        <%= tempResultreturnp65 %>
        <%
break;
case 69:
        gotMethod = true;
        java.lang.String getAllFilms69mtemp = sampleFilmWSDLProxyid.getAllFilms();
if(getAllFilms69mtemp == null){
%>
<%=getAllFilms69mtemp %>
<%
}else{
        String tempResultreturnp70 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(getAllFilms69mtemp));
        %>
        <%= tempResultreturnp70 %>
        <%
}
break;
}
} catch (Exception e) { 
%>
Exception: <%= org.eclipse.jst.ws.util.JspUtils.markup(e.toString()) %>
Message: <%= org.eclipse.jst.ws.util.JspUtils.markup(e.getMessage()) %>
<%
return;
}
if(!gotMethod){
%>
result: N/A
<%
}
%>
</BODY>
</HTML>