package com.pucmm.practica4.main;

import org.eclipse.jetty.websocket.server.WebSocketHandler;
import org.json.JSONException;
import org.json.JSONObject;
import com.pucmm.practica4.WebSocket.webSocketHandler;
import com.pucmm.practica4.entidades.*;
import com.pucmm.practica4.services.*;
import freemarker.template.Configuration;

import spark.ModelAndView;
import spark.Session;


import spark.Spark;
import spark.template.freemarker.FreeMarkerEngine;

import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import static j2html.TagCreator.*;
import static java.lang.Class.forName;
import static spark.Spark.*;
//import static spark.debug.DebugScreen.enableDebugScreen;
//import static org.eclipse.jetty.websocket.api.Session.*;

/**
 * Created by john on 03/06/17.
 */
public class Main {
    //public static List<org.eclipse.jetty.websocket.api.Session> usuariosConectados = new ArrayList<>();
    public static Map<String, org.eclipse.jetty.websocket.api.Session> usuariosConectados = new HashMap<>();
    public static Map<String, String> messages = new HashMap<>();

    public static void main(String[] args)throws SQLException {

        //Seteando el puerto en Heroku
        //port(getHerokuAssignedPort());
        //enableDebugScreen();

        //indicando los recursos publicos.
        staticFiles.location("/publico");
        //Debe ir antes de abrir alguna ruta.
        webSocket("/mensajeServidor", webSocketHandler.class);
        init();


        //Starting database
        BootStrapServices.getInstancia().init();


        //Adding admin user
        UsuarioServices usuarioServices = UsuarioServices.getInstancia();

        Usuario insertar = new Usuario();
        insertar.setAdministrador(true);
        insertar.setId(1);
        insertar.setAutor(true);
        insertar.setNombre("Jhon Ridore");
        insertar.setPassword("1234");
        insertar.setUsername("anyderre");

        if(usuarioServices.getUsuario("anyderre").isEmpty()){
            usuarioServices.crear(insertar);

        }


        //Indicando la carpeta por defecto que estaremos usando.
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
        configuration.setClassForTemplateLoading(Main.class, "/templates");
        FreeMarkerEngine freeMarkerEngine = new FreeMarkerEngine(configuration);


        get("/", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();

            ArticuloServices articuloServices = ArticuloServices.getInstancia();
            int size =articuloServices.findAll().size();
            ArrayList<Articulo> articulos = (ArrayList<Articulo>) articuloServices.findAllArticuloBetweenTwoIds(size-5);
            EtiquetaServices etiquetaServices = EtiquetaServices.getInstancia();
            ComentarioServices comentarioServices = ComentarioServices.getInstancia();
            List<Etiqueta> etiquetas = null;
            List<Articulo> articulosTemp=new ArrayList<>();
            for(Articulo articulo: articulos){
                etiquetas= etiquetaServices.findAllByArticulo(articulo);

                articulo.setEtiquetas(etiquetas);

                articulosTemp.add(articulo);
            }
            //inversing ArrayList order
            Collections.reverse(articulosTemp);
            attributes.put("articulos", articulosTemp);

            if (articulos.size()==0) {
                attributes.put("noDatos", "Todavia no hay Articulos en la base de datos");

            };

            attributes.put("size",size);
            attributes.put("titulo", "Welcome");
            // attributes.put("articulos", articulos);
            return new ModelAndView(attributes, "index.ftl");
        }, freeMarkerEngine);

        get("/page/galery/:size/:number", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            ArticuloServices articuloServices = ArticuloServices.getInstancia();
            int number = Integer.parseInt(request.params("number"));
            int size = Integer.parseInt(request.params("size"));
            ArrayList<Articulo> articulos = (ArrayList<Articulo>) articuloServices.findAllArticuloBetweenTwoIds(size-(number*5));
            EtiquetaServices etiquetaServices = EtiquetaServices.getInstancia();
            ComentarioServices comentarioServices = ComentarioServices.getInstancia();
            List<Etiqueta> etiquetas = null;
            List<Comentario>comentarios=null;
            List<Articulo> articulosTemp=new ArrayList<>();
            for(Articulo articulo: articulos){
                etiquetas= etiquetaServices.findAllByArticulo(articulo);
                articulo.setEtiquetas(etiquetas);

                articulosTemp.add(articulo);
            }
            //inversing ArrayList order
            Collections.reverse(articulosTemp);
            attributes.put("articulos", articulosTemp);

            if (articulos.size()==0) {
                attributes.put("noDatos", "Todavia no hay Articulos en la base de datos");

            };

            attributes.put("size", size);
            attributes.put("titulo", "Welcome");
            // attributes.put("articulos", articulos);
            return new ModelAndView(attributes, "pages.ftl");
        }, freeMarkerEngine);



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
            if(usuarios.get(0).getNombre()!=null){

                if(usuarios.get(0).getUsername().equals(username) && usuarios.get(0).getPassword().equals(password)) {
                    usuario.setId(usuarios.get(0).getId());
                    usuario.setAutor(usuarios.get(0).getAutor());
                    usuario.setAdministrador(usuarios.get(0).getAdministrador());
                    usuario.setNombre(usuarios.get(0).getNombre());
                    session.attribute("usuario", usuario);
                    response.redirect("/");
                }
            }
            attributes.put("message", "Lo siento no tienes cuenta registrada solo un admin puede registrarte");
            attributes.put("titulo", "login");

            return new ModelAndView(attributes, "login.ftl");
        }, freeMarkerEngine);


        //------------------------------------------//Admin task------------------------------------------------------------
        before("/registrar", (request, response) -> {
            Usuario usuario = request.session(true).attribute("usuario");

            if (usuario == null) {
                response.redirect("/login");
            }

        });
        get("/registrar", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();

            attributes.put("titulo", "Registrar");
            return new ModelAndView(attributes, "registrar.ftl");
        }, freeMarkerEngine);


        post("/registrar/usuario", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();

            if(request.queryParams("password").matches(request.queryParams("password-confirm"))){
                Usuario usuario = request.session(true).attribute("usuario");

                if(usuario.getAdministrador()){
                    Usuario newUsuario = new Usuario();
                    newUsuario.setNombre(request.queryParams("nombre"));
                    newUsuario.setAdministrador(false);
                    newUsuario.setAutor(true);
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


//<--------------------------------------------------Etiquetas crud------------------------------------------------------------------------------------------------------------------->
        delete("/etiqueta/:articulo/borrar/:id",(request, response)->{
            long id=0,articulo=0;
            try{
                id = Long.parseLong(request.params("id"));
                articulo = Long.parseLong(request.params("articulo"));
            }catch (Exception ex){
                ex.printStackTrace();
            }
            EtiquetaServices etiquetaServices = EtiquetaServices.getInstancia();
            etiquetaServices.delete(id);
            response.redirect("/ver/articulo/"+articulo);
            return "";
        });

        get("/etiqueta/:etiqueta/articulos",(request, response) -> {
            Map<String, Object> model = new HashMap<>();
            String etiqueta =request.params("etiqueta");
            EtiquetaServices etiquetaServices = EtiquetaServices.getInstancia();
            List<Etiqueta> etiquetas= etiquetaServices.findAllByEtiqueta(etiqueta);
            List<Articulo> articulos = new ArrayList<>();
            for(Etiqueta et : etiquetas){
                articulos.add(et.getArticulo());
            }

            model.put("titulo", "Articulos con misma etiqueta");
            model.put("articulos", articulos);
            return new ModelAndView(model, "Articulos.ftl");
        },freeMarkerEngine);
//<--------------------------------------------------Comentario crud------------------------------------------------------------------------------------------------------------------->

        post("/agregar/comentario/:articulo", (request, response)->{
            // long articulo=0;

            long articulo= Long.parseLong((request.params("articulo")));

            Session session = request.session(true);
            Usuario usuario = session.attribute("usuario");

            ArticuloServices articuloServices = ArticuloServices.getInstancia();
            Comentario comentario1=new Comentario();
            comentario1.setAutor(usuario);
            comentario1.setComentario(request.queryParams("comentario"));

            comentario1.setArticulo(articuloServices.find(articulo));

            ComentarioServices comentarioServices = ComentarioServices.getInstancia();
            comentarioServices.crear(comentario1);

            response.redirect("/ver/articulo/"+articulo);

            return "";
        });

        before("/agregar/comentario/:articulo", (request, response) -> {
            Usuario usuario = request.session(true).attribute("usuario");
            if (usuario == null) {
                response.redirect("/login");
            }
        });

//<--------------------------------------------------Articulo Crud------------------------------------------------------------------------------------------------------------------->
        get("/agregar/articulo", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            Usuario usuario = request.session(true).attribute("usuario");

            model.put("titulo", "registrar articulo");
            return new ModelAndView(model, "registrarArticulo.ftl");
        },freeMarkerEngine);

        //checking if user have a session
        before("/agregar/articulo", (request, response) -> {
            Usuario usuario = request.session(true).attribute("usuario");

            if (usuario == null) {
                response.redirect("/login");
            }
        });

        post("/agregar/articulo",(request, response)->{
            String []etiquetas=request.queryParams("etiquetas").split(",");
            //String autor = request.queryParams("username");
            ArticuloServices articuloServices=ArticuloServices.getInstancia();
            Session session = request.session(true);

            Usuario usuario = session.attribute("usuario");



            Articulo articulo = new Articulo();
            articulo.setTitulo( request.queryParams("titulo"));
            articulo.setCuerpo(request.queryParams("cuerpo"));

            articulo.setAutor(usuario);
            articulo.setFecha(new Date());


            articuloServices.crear(articulo);

            ArticuloServices articuloServices1=ArticuloServices.getInstancia();
            List <Articulo>articulos= articuloServices1.findAll();

            long id = articulos.get(articulos.size()-1).getId();

            if(etiquetas.length!=0){
                EtiquetaServices etiquetaServices = EtiquetaServices.getInstancia();
                articulo.setId(id);
                for(String et: etiquetas){
                    etiquetaServices.crear(new Etiqueta(et,articulo));
                }
            }else{
                System.out.println("Error al entrar las etiquetas");
            }

            response.redirect("/");
            return "";
        });

        before("/ver/articulo/:id", (request, response) -> {
            Usuario usuario = request.session(true).attribute("usuario");

            if (usuario == null) {
                response.redirect("/login");
            }
        });
        get("/ver/articulo/:id", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            Usuario usuario = request.session(true).attribute("usuario");

            long id= (long)Integer.parseInt(request.params("id"));
            ArticuloServices articuloServices = ArticuloServices.getInstancia();
            Articulo articulo = articuloServices.find(id);
            EtiquetaServices etiquetaServices =EtiquetaServices.getInstancia();
            ComentarioServices comentarioServices =ComentarioServices.getInstancia();
            LikesServices likesServices =LikesServices.getInstancia();
            DislikeServices dislikeServices = DislikeServices.getInstancia();
            ArticleLikeService articleLikeService = ArticleLikeService.getInstancia();
            AriticleDislikeService ariticleDislikeService = AriticleDislikeService.getInstancia();


            List<Etiqueta> etiquetas = null;
            List<Comentario>comentarios=null;

            etiquetas= etiquetaServices.findAllByArticulo(articulo);
            comentarios= comentarioServices.findAllByArticulo(articulo);

            articulo.setEtiquetas(etiquetas);
            articulo.setComentarios(comentarios);

            List<Comentario> comentarioList =new ArrayList<>();
            for(Comentario comentario : comentarios){
                comentario.setLikes(likesServices.findAllByComentario(comentario));
                comentario.setDislikes(dislikeServices.findAllByComentario(comentario));
                comentarioList.add(comentario);
            }
            articulo.setArticleDislikes(ariticleDislikeService.findAllByArticulo(articulo));
            articulo.setArticleLikes(articleLikeService.findAllByArticulo(articulo));
            model.put("titulo", "Welcome");
            model.put("articulo", articulo);
            model.put("titulo", "Ver articulo");
            model.put("comments", comentarioList);
            return new ModelAndView(model, "verArticulo.ftl");
        },freeMarkerEngine);

//______________________________________modifying articulo__________________________________________

        get("/modificar/articulo/:id", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            Usuario usuario = request.session(true).attribute("usuario");
            ArticuloServices articuloServices =ArticuloServices.getInstancia();
            Articulo articulo = articuloServices.find(Long.parseLong(request.params("id")));
            EtiquetaServices etiquetaServices = EtiquetaServices.getInstancia();

            List<Etiqueta> etiquetas =etiquetaServices.findAllByArticulo(articulo);

            model.put("articulo",articulo);
            model.put("etiquetas",etiquetas);
            model.put("titulo", "registrar articulo");
            return new ModelAndView(model, "modificarArticulo.ftl");
        },freeMarkerEngine);

        //checking if user have a session
        before("/modificar/articulo/:id", (request, response) -> {
            Usuario usuario = request.session(true).attribute("usuario");
            if (usuario == null) {
                response.redirect("/login");
            }
        });

        post("/modificar/:id/articulo",(request, response)->{
            Long id = Long.parseLong(request.params("id"));
            String []etiquetas=request.queryParams("etiquetas").split(",");

            ArticuloServices articuloServices=ArticuloServices.getInstancia();
            Session session = request.session(true);

            Usuario usuario = session.attribute("usuario");

            Articulo articulo = new Articulo();
            articulo.setTitulo( request.queryParams("titulo"));
            articulo.setCuerpo(request.queryParams("cuerpo"));

            articulo.setAutor(usuario);
            articulo.setFecha(new Date());
            articulo.setId(id);

            if(etiquetas.length!=0){
                EtiquetaServices etiquetaServices = EtiquetaServices.getInstancia();

                List<Etiqueta>etiquetas1 = etiquetaServices.findAllByArticulo(articulo);
                for(Etiqueta etiqueta:etiquetas1){
                    etiquetaServices.delete(etiqueta.getId());
                }

                for(String et: etiquetas){
                    etiquetaServices.crear(new Etiqueta(et,articulo));
                }
            }else{
                System.out.println("Error al entrar las etiquetas");
            }

            articuloServices.editar(articulo);

            response.redirect("/ver/articulo/"+id);
            return "";
        });

        before("/borrar/articulo/:articulo", (request, response) -> {
            Usuario usuario = request.session(true).attribute("usuario");
            if (usuario == null) {
                response.redirect("/login");
            }
        });



        get("/borrar/articulo/:articulo",(request, response)->{
            long articulo=0;
            try{
                articulo = Long.parseLong(request.params("articulo"));
            }catch (Exception ex){
                ex.printStackTrace();
            }

            ArticuloServices articuloServices = ArticuloServices.getInstancia();
            Articulo articulo1 = articuloServices.find(articulo);


            EtiquetaServices etiquetaServices =EtiquetaServices.getInstancia();
            ComentarioServices comentarioServices = ComentarioServices.getInstancia();
            ArticleLikeService articleLikeService=ArticleLikeService.getInstancia();
            LikesServices likesServices=LikesServices.getInstancia();
            AriticleDislikeService ariticleDislikeService=AriticleDislikeService.getInstancia();
            DislikeServices dislikeServices=DislikeServices.getInstancia();

            List<Etiqueta> etiquetas = null;
            List<Comentario>comentarios=null;
            List<Likes> likes=null;
            List<ArticleLike> articleLikes=null;
            List<ArticleDislike> articleDislikes=null;
            List<Dislike> dislikes=null;


            etiquetas= etiquetaServices.findAllByArticulo(articulo1);
            articleLikes=articleLikeService.findAllByArticulo(articulo1);
            articleDislikes=ariticleDislikeService.findAllByArticulo(articulo1);

            for(ArticleDislike articleDislike:articleDislikes){
                ariticleDislikeService.delete(articleDislike.getId());
            }

            for(ArticleLike articleLike:articleLikes){
                articleLikeService.delete(articleLike.getId());
            }

            comentarios= comentarioServices.findAllByArticulo(articulo1);
            for(Etiqueta etiqueta:etiquetas){
                etiquetaServices.delete(etiqueta.getId());
            }

            for(Comentario comentario:comentarios){
                likes=likesServices.findAllByComentario(comentario);
                dislikes=dislikeServices.findAllByComentario(comentario);
                for(Likes likes1:likes){
                    likesServices.delete(likes1.getId());
                }
                for (Dislike dislike: dislikes){
                    dislikeServices.delete(dislike.getId());
                }
                comentarioServices.delete(comentario.getId());
            }

            articuloServices.delete(articulo1.getId());
            response.redirect("/");
            return "";
        });
//-________________________________________________likes && dislikes for Comentario______________________________________________
        before("/likes/:id/:articulo", (request, response) -> {
            Usuario usuario = request.session(true).attribute("usuario");
            if (usuario == null) {
                response.redirect("/login");
            }
        });

        get("/likes/:id/:articulo",(request, response) -> {
            Usuario usuario = request.session(true).attribute("usuario");
            Boolean esta=false;
            ComentarioServices comentarioServices = ComentarioServices.getInstancia();
            Comentario comentario= comentarioServices.find(Long.parseLong(request.params("id")));


            LikesServices likesServices = LikesServices.getInstancia();
            DislikeServices dislikeServices =DislikeServices.getInstancia();

            Likes like = new Likes();
            List<Likes> likes = likesServices.findAllByComentario(comentario);
            List<Dislike> dislikes = dislikeServices.findAllByComentario(comentario);

            for (Likes like1 : likes){
                if (like1.getAutor().getUsername().equals(usuario.getUsername())){
                    esta=true;
                }
            }

            for(Dislike dislike : dislikes){
                if (dislike.getAutor().getUsername().equals(usuario.getUsername())){
                    dislikeServices.delete(dislike.getId());
                }
            }
            if(!esta){
                like.setAutor(usuario);
                like.setComentario(comentario);
                likesServices.crear(like);
            }

            response.redirect("/ver/articulo/"+request.params("articulo"));
            return "";
        });

        before("/dislikes/:id/:articulo", (request, response) -> {
            Usuario usuario = request.session(true).attribute("usuario");
            if (usuario == null) {
                response.redirect("/login");
            }
        });

        get("/dislikes/:id/:articulo",(request, response) -> {
            Usuario usuario = request.session(true).attribute("usuario");
            Boolean esta=false;
            ComentarioServices comentarioServices = ComentarioServices.getInstancia();
            Comentario comentario= comentarioServices.find(Long.parseLong(request.params("id")));

            LikesServices likesServices = LikesServices.getInstancia();
            DislikeServices dislikeServices =DislikeServices.getInstancia();

            Dislike disLike = new Dislike();
            List<Likes> likes = likesServices.findAllByComentario(comentario);
            List<Dislike> dislikes = dislikeServices.findAllByComentario(comentario);

            for (Dislike dislike1 : dislikes){
                if (dislike1.getAutor().getUsername().equals(usuario.getUsername())){
                    esta = true;
                }
            }

            for(Likes like : likes){
                if (like.getAutor().getUsername().equals(usuario.getUsername())){
                    likesServices.delete(like.getId());
                }
            }
            if(!esta){
                disLike.setAutor(usuario);
                disLike.setComentario(comentario);
                dislikeServices.crear(disLike);
            }

            response.redirect("/ver/articulo/"+request.params("articulo"));
            return "";
        });
//-________________________________________________likes && dislikes for Articulo______________________________________________

        before("/articulo/likes/:articulo", (request, response) -> {
            Usuario usuario = request.session(true).attribute("usuario");
            if (usuario == null) {
                response.redirect("/login");
            }
        });

        get("/articulo/likes/:articulo",(request, response) -> {
            Usuario usuario = request.session(true).attribute("usuario");
            Boolean esta=false;
            ArticuloServices articuloServices = ArticuloServices.getInstancia();
            Articulo articulo= articuloServices.find(Long.parseLong(request.params("articulo")));


            ArticleLikeService articleLikeService = ArticleLikeService.getInstancia();
            AriticleDislikeService ariticleDislikeService = AriticleDislikeService.getInstancia();

            ArticleLike like = new ArticleLike();
            List<ArticleLike> likes = articleLikeService.findAllByArticulo(articulo);
            List<ArticleDislike> dislikes = ariticleDislikeService.findAllByArticulo(articulo);

            for (ArticleLike like1 : likes){
                if (like1.getAutor().getUsername().equals(usuario.getUsername())){
                    esta = true;
                }
            }
            for(ArticleDislike dislike : dislikes){
                if (dislike.getAutor().getUsername().equals(usuario.getUsername())){
                    ariticleDislikeService.delete(dislike.getId());
                }
            }
            if(!esta){
                like.setAutor(usuario);
                like.setArticulo(articulo);
                articleLikeService.crear(like);
            }


            response.redirect("/ver/articulo/"+request.params("articulo"));
            return "";
        });

        before("/articulo/dislikes/:articulo", (request, response) -> {
            Usuario usuario = request.session(true).attribute("usuario");
            if (usuario == null) {
                response.redirect("/login");
            }
        });

        get("/articulo/dislikes/:articulo",(request, response) -> {
            Usuario usuario = request.session(true).attribute("usuario");
            Boolean esta= false;
            ArticuloServices articuloServices = ArticuloServices.getInstancia();
            Articulo articulo= articuloServices.find(Long.parseLong(request.params("articulo")));

            ArticleLikeService articleLikeService = ArticleLikeService.getInstancia();
            AriticleDislikeService ariticleDislikeService = AriticleDislikeService.getInstancia();

            ArticleDislike articleDislike = new ArticleDislike();
            List<ArticleLike> likes = articleLikeService.findAllByArticulo(articulo);
            List<ArticleDislike> dislikes = ariticleDislikeService.findAllByArticulo(articulo);

            for (ArticleDislike dislike1 : dislikes){
                if (dislike1.getAutor().getUsername().equals(usuario.getUsername())){
                    esta= true;
                }
            }

            for(ArticleLike like : likes){
                if (like.getAutor().getUsername().equals(usuario.getUsername())){
                    articleLikeService.delete(like.getId());
                }
            }
            if(!esta){
                articleDislike.setAutor(usuario);
                articleDislike.setArticulo(articulo);
                ariticleDislikeService.crear(articleDislike);
            }

            response.redirect("/ver/articulo/"+request.params("articulo"));
            return "";
        });
//_____________________________________________Mensaje Servidor_______________________________________

//---------------------------------------------------------------------------------------------------------------------
        before("/espacio/administrador", (request, response) -> {
            Usuario usuario = request.session(true).attribute("usuario");
            if (usuario == null) {
                response.redirect("/login");
            }
        });

        get("/espacio/administrador",(request, response) -> {
            Map<String, Object> model = new HashMap<>();
            Usuario usuario = request.session(true).attribute("usuario");
            model.put("usuario", usuario);
            model.put("titulo", "registrar articulo");
            return new ModelAndView(model, "espacioAdmin.ftl");
        },freeMarkerEngine);

    }  /**
     * Metodo para setear el puerto en Heroku
     * @return
     */
    private static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }

    public static void broadCastMessage(String message){
        webSocketHandler.usuariosSimple.keySet().stream().filter(org.eclipse.jetty.websocket.api.Session::isOpen).forEach(session ->{
            try {
                session.getRemote().sendString(String.valueOf(new JSONObject()
//                    .put("us")
                        .put("userlist", webSocketHandler.usuariosSimple.values())
                ));
            }catch (Exception ex){
                ex.printStackTrace();
            }
        });
    }

    public static void createHtmlMessageFromSender(String message, boolean isAdmin) {
        String [] mes= message.split("~");


            if(isAdmin){
                if(mes[1].equals("connect-120lk./,o/h")){
                    try{
                        usuariosConectados.get(mes[0]).getRemote().sendString(String.valueOf(new JSONObject()
                                .put("userMessage", messages.values())
                                .put("userList",messages.keySet())
                                //messages.rem
                        ));
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                }else{
                    try {///////a gerer*************************************************************
                        usuariosConectados.get(mes[3]).getRemote().sendString(message);
                        usuariosConectados.get(mes[0]).getRemote().sendString(String.valueOf(new JSONObject()
                                .put("userMes", message)
                        ));
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }else{

                if(webSocketHandler.usuariosAdmin.size()!=0){

                    System.out.println("Tamano lista admin "+webSocketHandler.usuariosAdmin.size());
                    if(mes[3].equals("")){
                        if(messages.get(mes[0])==null){
                            String theMessages="";
                            theMessages =  mes[1];
                            messages.put(mes[0],theMessages);
                        }else {
                            String theMessages= messages.get(mes[0]);
                            theMessages =  theMessages+"~"+mes[1];
                            messages.put(mes[0],theMessages);
                        }
                        try {
                            usuariosConectados.get(mes[0]).getRemote().sendString(message);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }else{

                        try{
                            usuariosConectados.get(mes[0]).getRemote().sendString(message);
                             webSocketHandler.usuariosAdmin.get(mes[3]).getRemote().sendString(String.valueOf(new JSONObject()
                                            .put("userMes", message)


                            ));


                        }catch (IOException ex){
                            ex.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }else{
                    try{
                        message= message+"~No hay Administrador en linea ahora";

                        if(messages.get(mes[0])==null){
                            String theMessages="";
                            theMessages =  mes[1];
                            messages.put(mes[0],theMessages);
                        }else {
                            String theMessages= messages.get(mes[0]);
                            theMessages =  theMessages+"~"+mes[1];
                            messages.put(mes[0],theMessages);
                        }
                        usuariosConectados.get(mes[0]).getRemote().sendString(message);

                }catch (Exception ex){
                        ex.printStackTrace();
                    }

                }

            }
    }


}