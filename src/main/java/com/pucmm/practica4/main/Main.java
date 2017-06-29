package com.pucmm.practica4.main;

import com.pucmm.practica4.entidades.*;
import com.pucmm.practica4.services.*;
import freemarker.template.Configuration;

import spark.ModelAndView;
import spark.Session;


import spark.template.freemarker.FreeMarkerEngine;

import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.concurrent.TimeUnit;


import static spark.Spark.*;
import static spark.debug.DebugScreen.enableDebugScreen;


/**
 * Created by john on 03/06/17.
 */
public class Main {

    public static void main(String[] args)throws SQLException {

        //Seteando el puerto en Heroku
        port(getHerokuAssignedPort());
        enableDebugScreen();

        //indicando los recursos publicos.
        staticFiles.location("/publico");

        //Starting thread
        new DeletePasteThread().run();

        //Starting database
        BootStrapServices.getInstancia().init();


        //Adding admin user
        UsuarioServices usuarioServices = UsuarioServices.getInstancia();

        Usuario insertar = new Usuario();
        insertar.setAdministrador(true);
        insertar.setId(1);
        insertar.setName("Jhon Ridore");
        insertar.setPassword("1234");
        insertar.setUsername("anyderre");

        if (usuarioServices.getUsuario("anyderre").isEmpty()) {
            usuarioServices.crear(insertar);

        }


        //Indicando la carpeta por defecto que estaremos usando.
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
        configuration.setClassForTemplateLoading(Main.class, "/templates");
        FreeMarkerEngine freeMarkerEngine = new FreeMarkerEngine(configuration);
//----------------------------Paste stuff ----------------------------------------
      path("/paste",()->{

          get("/", (request, response) -> {
              Map<String, Object> model = new HashMap<>();
              model.put("titulo", "Welcome Page| Copy & Paste");

              return new ModelAndView(model, "Paste.ftl");
          },freeMarkerEngine);

          //Deleting paste
          delete("/delete/:id", (request, response) -> {
            PasteServices pasteServices = PasteServices.getInstancia();
            pasteServices.delete(Long.parseLong(request.params("id")));
            Usuario usuario = request.session(true).attribute("usuario");

            if(usuario==null){
                response.redirect("/user/login");
            }
            response.redirect("/paste/list/"+usuario.getId());
              return "";
          });

          //Updating Paste
          get("/modify/:id", (request, response) -> {
              Map <String, Object> model = new HashMap<>();
              PasteServices pasteServices = PasteServices.getInstancia();
             Paste paste= pasteServices.find(Long.parseLong(request.params("id")));

             model.put("titulo", "Update Paste");
             model.put("paste", paste);

             return modelAndView(model, "updatePaste.ftl");
          }, freeMarkerEngine);

          put("/modify/:id", (request, response) -> {
             PasteServices pasteServices = PasteServices.getInstancia();
             Paste paste= pasteServices.find(Long.parseLong(request.params("id")));
             paste.setBloqueDeCodigo(request.queryParams("bloqueDeTexto"));
             paste.setTitulo(request.queryParams("title"));
             paste.setSintaxis(request.queryParams("syntax"));
             long fechaDeHoy = new Date().getTime();
             switch (request.queryParams("expirationDate")){
                 case "10 minutes":
                     paste.setFechaExpiracion((10*60)+TimeUnit.MILLISECONDS.toSeconds(fechaDeHoy));
                     break;
                 case "15 minutes":
                     paste.setFechaExpiracion((15*60)+TimeUnit.MILLISECONDS.toSeconds(fechaDeHoy));
                     break;
                 case "20 minutes":
                     paste.setFechaExpiracion((20*60)+TimeUnit.MILLISECONDS.toSeconds(fechaDeHoy));
                     break;
                 case "30 minutes":
                     paste.setFechaExpiracion((30*60)+TimeUnit.MILLISECONDS.toSeconds(fechaDeHoy));
                     break;
                 case "1 hour":
                     paste.setFechaExpiracion((60*60)+TimeUnit.MILLISECONDS.toSeconds(fechaDeHoy));
                     break;
                 case "1 day":
                     paste.setFechaExpiracion((24*60*60)+TimeUnit.MILLISECONDS.toSeconds(fechaDeHoy));
                     break;
                 case "1 week":
                     paste.setFechaExpiracion(TimeUnit.DAYS.toSeconds(7)+TimeUnit.MILLISECONDS.toSeconds(fechaDeHoy));
                     break;
                 case "never":
                     //paste.setFechaExpiracion(); Case to be determined
                     break;

             }
             paste.setFechaPublicacion(TimeUnit.MILLISECONDS.toSeconds(new Date().getTime()));
             paste.setTipoExposicion(request.queryParams("expositionType"));
             pasteServices.editar(paste);
             response.redirect("/show/list");
              return "";

          });

          get("/show/list",(request, response) -> {
              Map<String, Object>model = new HashMap<>();
              model.put("titulo", "Show list Of user Paste");
              //Method to continue
              return modelAndView(model, "verPasteBin.ftl");
          },freeMarkerEngine);
      });


//---------------------------User stuff-------------------------------------------
        path("/user", ()->{

            get("/login", (request, response) -> {
                Map<String, Object> attributes = new HashMap<>();
                attributes.put("titulo", "Login");
                return new ModelAndView(attributes, "login.ftl");
            }, freeMarkerEngine);

            post("/login", (request, response) -> {
                Map<String, Object> attributes = new HashMap<>();
                //Usuario currentUserLogin = new Usuario();
                Session session = request.session(true);
                Usuario usuario = new Usuario();
                String username = request.queryParams("username");
                String password = request.queryParams("password");
                usuario.setUsername(username);
                usuario.setPassword(password);
                UsuarioServices usuarioServices1 = UsuarioServices.getInstancia();

                List<Usuario> usuarios = usuarioServices1.getUsuario(username);
                if (usuarios.get(0).getName() != null) {

                    if (usuarios.get(0).getUsername().equals(username) && usuarios.get(0).getPassword().equals(password)) {
                        usuario.setId(usuarios.get(0).getId());
                        usuario.setAdministrador(usuarios.get(0).getAdministrador());
                        usuario.setName(usuarios.get(0).getName());
                        session.attribute("usuario", usuario);
                        response.redirect("/");
                    }
                }
                attributes.put("message", "Lo siento no tienes cuenta registrada solo un admin puede registrarte");
                attributes.put("titulo", "login");

                return new ModelAndView(attributes, "login.ftl");
            }, freeMarkerEngine);

            get("/register", (request, response) -> {
                Map<String, Object> attributes = new HashMap<>();

                attributes.put("titulo", "Registrar");
                return new ModelAndView(attributes, "registrar.ftl");
            }, freeMarkerEngine);

            post("/register", (request, response) -> {
                Map<String, Object> attributes = new HashMap<>();

                if(request.queryParams("password").matches(request.queryParams("password-confirm"))){
                    Usuario usuario = request.session(true).attribute("usuario");

                    if(usuario.getAdministrador()){
                        Usuario newUsuario = new Usuario();
                        newUsuario.setName(request.queryParams("nombre"));
                        newUsuario.setAdministrador(false);
                        newUsuario.setPassword(request.queryParams("password"));
                        newUsuario.setUsername(request.queryParams("username"));
                        UsuarioServices usuarioServices1 = UsuarioServices.getInstancia();
                        usuarioServices1.crear(newUsuario);
                        response.redirect("/");
                    }else{
                        attributes.put("message", "Solo administrador puede crear usario");
                    }
                }else{
                    attributes.put("confirm", "password doesn't match");
                }
                attributes.put("titulo", "Registrar");
                return new ModelAndView(attributes,"registrar.ftl");
            },freeMarkerEngine);
        });


//--------------------------
        before("/agregar/comentario/:articulo", (request, response) -> {
            Usuario usuario = request.session(true).attribute("usuario");
            if (usuario == null) {
                response.redirect("/login");
            }
        });



    }
    private static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }




}