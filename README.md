#Jersey 2 / Grizzly 2 Web Application Base#
##Getting Started##

TODO - gradle exec

###Run either class###
- HttpServer (currently broken for swagger)
- WebContainer

###URIs###
- /application.wadl
- /swagger
- / (Freemarker template and plain HTML)
- /bootstrap (Freemarker template using bootstrap 3.3.6)
- /mustache (mustache template and plain HTML)
- /angular (Freemarker template using angular.js 1.4.8)

###TODO###
- BDD (Cucumber)
- Add at least one provider
- JSP Provider still rather broken!
- Completely connect http://0.0.0.0:9999/application.wadl to swagger.io (only partially implemented)
- Correctly Hook up logging servlet for logback http://logback.qos.ch/manual/configuration.html
- Other MVC engines (jade?)
- Other HTML 5 Templates ?

### Testing the Swaggger endpoint (Using MarkLogic to get the JSON payload) ###
```xquery
declare variable $options := 
<options xmlns="xdmp:http">
  <headers>
    <content-type>application/json</content-type>
    <accept>application/json</accept>
  </headers>
</options>;

xdmp:http-get("http://localhost:9999/swagger.json",$options)
```