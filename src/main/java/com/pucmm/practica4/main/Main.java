package com.pucmm.practica4.main;

import com.pucmm.practica4.entidades.*;
import com.pucmm.practica4.services.*;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import freemarker.template.Configuration;
import spark.ModelAndView;
import spark.Session;
import spark.template.freemarker.FreeMarkerEngine;
import javax.imageio.ImageIO;
import javax.servlet.MultipartConfigElement;
import javax.servlet.http.Part;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        // new DeletePasteThread().run();

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


//----------------------------Paste CRUD ----------------------------------------
        get("/", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("titulo", "Welcome Page| Copy & Paste");
            Usuario usuario = request.session(true).attribute("usuario");
            if (usuario != null) {
                model.put("usuario", usuario.getUsername());
            }
            PasteServices pasteServices = PasteServices.getInstancia();
            List<Paste> pastes = pasteServices.findLastPaste(pasteServices.findAll().size() - 13);
            Collections.reverse(pastes);
            model.put("publicPaste", pastes);
            return new ModelAndView(model, "Paste.ftl");
        }, freeMarkerEngine);

        path("/paste", () -> {
            //Saving Paste
            post("/", (request, response) -> {
                Map<String, Object> model = new HashMap<>();
                PasteServices pasteServices = PasteServices.getInstancia();
                Paste paste = new Paste();
                String title = request.queryParams("title");
                System.out.println(title);
                if (title.isEmpty()) {
                    title = "untitled";
                }
                paste.setTitulo(title);
                paste.setTipoExposicion(request.queryParams("expositionType"));
                paste.setBloqueDeCodigo(request.queryParams("bloqueDeTexto"));
                paste.setSintaxis(request.queryParams("syntax"));
                paste.setCantidadVista(0);
                long fechaDeHoy = new Date().getTime();
                String expirationDate = request.queryParams("expirationDate");
                switch (expirationDate) {
                    case "10 minutes":
                        paste.setFechaExpiracion((10 * 60) + TimeUnit.MILLISECONDS.toSeconds(fechaDeHoy));
                        break;
                    case "15 minutes":
                        paste.setFechaExpiracion((15 * 60) + TimeUnit.MILLISECONDS.toSeconds(fechaDeHoy));
                        break;
                    case "20 minutes":
                        paste.setFechaExpiracion((20 * 60) + TimeUnit.MILLISECONDS.toSeconds(fechaDeHoy));
                        break;
                    case "30 minutes":
                        paste.setFechaExpiracion((30 * 60) + TimeUnit.MILLISECONDS.toSeconds(fechaDeHoy));
                        break;
                    case "1 hour":
                        paste.setFechaExpiracion((60 * 60) + TimeUnit.MILLISECONDS.toSeconds(fechaDeHoy));
                        break;
                    case "1 day":
                        paste.setFechaExpiracion((24 * 60 * 60) + TimeUnit.MILLISECONDS.toSeconds(fechaDeHoy));
                        break;
                    case "1 week":
                        paste.setFechaExpiracion(TimeUnit.DAYS.toSeconds(7) + TimeUnit.MILLISECONDS.toSeconds(fechaDeHoy));
                        break;
                    case "never":
                        String sDate1 = "31/12/9999";
                        Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
                        paste.setFechaExpiracion(TimeUnit.MILLISECONDS.toSeconds(date1.getTime()));
                        break;
                }

                paste.setFechaPublicacion(TimeUnit.MILLISECONDS.toSeconds(fechaDeHoy));
                int sizePaste = pasteServices.findAll().size();
                if (sizePaste == 0) {
                    paste.setUrl("http://localhost:4567/paste/show/embed/1");
                } else {
                    paste.setUrl("http://localhost:4567/paste/show/embed/" + (sizePaste + 1));
                }

                Usuario usuario = request.session(true).attribute("usuario");

                if (usuario != null) {
                    UsuarioServices usuarioServices1 = UsuarioServices.getInstancia();
                    Usuario usuario1 = usuarioServices1.find(usuario.getId());
                    usuario1.getPastes().add(paste);
                    usuario1.setPastes(usuario1.getPastes());
                    usuarioServices1.editar(usuario1);
                    model.put("usuarioId", usuario1.getId());
                    model.put("user", usuario1.getUsername());
                    model.put("usuario", usuario1.getUsername());
                } else {
                    pasteServices.crear(paste);
                    model.put("user", "guest");
                }

                Date date = new Date(); // your date
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                model.put("fecha", day + "/" + month + "/" + year);
                paste.setId(sizePaste);
                model.put("paste", paste);

                model.put("titulo", "Actual Paste");
                return new ModelAndView(model, "actualPaste.ftl");
            }, freeMarkerEngine);


            //Updating Paste
            get("/modify/:id", (request, response) -> {
                Map<String, Object> model = new HashMap<>();
                PasteServices pasteServices = PasteServices.getInstancia();
                Paste paste = pasteServices.find(Long.parseLong(request.params("id")));

                model.put("titulo", "Update Paste");
                model.put("paste", paste);
                Usuario usuario = request.session(true).attribute("usuario");
                if (usuario != null) {
                    model.put("usuario", usuario.getUsername());
                }
                model.put("publicPaste", pasteServices.findLastPaste(pasteServices.findAll().size() - 13));
                return new ModelAndView(model, "updatePaste.ftl");
            }, freeMarkerEngine);

            post("/modify/:id", (request, response) -> {
                PasteServices pasteServices = PasteServices.getInstancia();
                Paste paste = pasteServices.find(Long.parseLong(request.params("id")));
                paste.setBloqueDeCodigo(request.queryParams("bloqueDeTexto"));
                paste.setTitulo(request.queryParams("title"));
                paste.setSintaxis(request.queryParams("syntax"));
                long fechaDeHoy = new Date().getTime();
                switch (request.queryParams("expirationDate")) {
                    case "10 minutes":
                        paste.setFechaExpiracion((10 * 60) + TimeUnit.MILLISECONDS.toSeconds(fechaDeHoy));
                        break;
                    case "15 minutes":
                        paste.setFechaExpiracion((15 * 60) + TimeUnit.MILLISECONDS.toSeconds(fechaDeHoy));
                        break;
                    case "20 minutes":
                        paste.setFechaExpiracion((20 * 60) + TimeUnit.MILLISECONDS.toSeconds(fechaDeHoy));
                        break;
                    case "30 minutes":
                        paste.setFechaExpiracion((30 * 60) + TimeUnit.MILLISECONDS.toSeconds(fechaDeHoy));
                        break;
                    case "1 hour":
                        paste.setFechaExpiracion((60 * 60) + TimeUnit.MILLISECONDS.toSeconds(fechaDeHoy));
                        break;
                    case "1 day":
                        paste.setFechaExpiracion((24 * 60 * 60) + TimeUnit.MILLISECONDS.toSeconds(fechaDeHoy));
                        break;
                    case "1 week":
                        paste.setFechaExpiracion(TimeUnit.DAYS.toSeconds(7) + TimeUnit.MILLISECONDS.toSeconds(fechaDeHoy));
                        break;
                    case "never":
                        String sDate1 = "31/12/9999";
                        Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
                        paste.setFechaExpiracion(TimeUnit.MILLISECONDS.toSeconds(date1.getTime()));
                        break;

                }
                paste.setFechaPublicacion(TimeUnit.MILLISECONDS.toSeconds(new Date().getTime()));
                paste.setTipoExposicion(request.queryParams("expositionType"));
                pasteServices.editar(paste);

                response.redirect("/user/show/paste");
                return "";
            });

            get("/raw/:id", (request, response) -> {
                Map<String, Object> model = new HashMap<>();
                model.put("titulo", "Raw Text");
                PasteServices pasteServices = PasteServices.getInstancia();
                Paste paste = pasteServices.find(Long.parseLong(request.params("id")));
                model.put("text", paste.getBloqueDeCodigo());
                return new ModelAndView(model, "raw.ftl");
            }, freeMarkerEngine);

            //Deleting paste
            get("/delete/:id", (request, response) -> {
                PasteServices pasteServices = PasteServices.getInstancia();
                Paste paste = pasteServices.find(Long.parseLong(request.params("id")));
                pasteServices.delete(paste.getId());
                response.redirect("/");
                return "";
            });
            //embedding paste
            get("/embed/:id", (request, response) -> {
                Map<String, Object> model = new HashMap<>();
                PasteServices pasteServices = PasteServices.getInstancia();
                Paste paste = pasteServices.find(Long.parseLong(request.params("id")));
                System.out.println(paste.getId());
                model.put("titulo", "embed Paste");
                model.put("paste", paste);
                Usuario usuario = request.session(true).attribute("usuario");
                if (usuario != null) {
                    model.put("usuario", usuario.getUsername());
                }
                return new ModelAndView(model, "embededPage.ftl");
            }, freeMarkerEngine);

            get("/show/embed/:id", (request, response) -> {
                Map<String, Object> model = new HashMap<>();
                PasteServices pasteServices = PasteServices.getInstancia();
                Paste paste = pasteServices.find(Long.parseLong(request.params("id")));
                System.out.println(paste.getId());
                model.put("titulo", "embed Paste");
                model.put("paste", paste);
                Usuario usuario = request.session(true).attribute("usuario");
                if (usuario != null) {
                    model.put("usuario", usuario.getUsername());
                }
                return new ModelAndView(model, "showEmbed.ftl");
            }, freeMarkerEngine);

            get("/update/hits/:id", (request, response) -> {
                PasteServices pasteServices = PasteServices.getInstancia();
                Paste paste = pasteServices.find(Long.parseLong(request.params("id")));
                paste.setCantidadVista(paste.getCantidadVista() + 1);
                pasteServices.editar(paste);
                return paste.getCantidadVista();
            });
            //-----------------------------------------------------------------------------------------------
            get("/eliminar", (request, response) -> {
                PasteServices pasteServices = PasteServices.getInstancia();
                List<Paste> pastes= pasteServices.selectByDate();
                for(Paste paste: pastes){
                    pasteServices.delete(paste.getId());
                }
                return "deleted";
            });

            path("/show", () -> {

                get("/list", (request, response) -> {
                    Map<String, Object> model = new HashMap<>();
                    model.put("titulo", "Show all User");
                    PasteServices pasteServices = PasteServices.getInstancia();

                    model.put("pastes", pasteServices.getPasteByCantAccAndPublic(0));

                    model.put("pasteSize", pasteServices.findAll().size());
                    Usuario usuario = request.session(true).attribute("usuario");
                    if (usuario != null) {
                        model.put("usuario", usuario.getUsername());
                    }
                    return new ModelAndView(model, "PasteConMasHits.ftl");
                }, freeMarkerEngine);

                get("/list/:size/:page", (request, response) -> {
                    Map<String, Object> model = new HashMap<>();
                    int page = Integer.parseInt(request.params("page"));
                    int size = Integer.parseInt(request.params("size"));
                    PasteServices pasteServices = PasteServices.getInstancia();
                    model.put("pastes", pasteServices.getPasteByCantAccAndPublic((10 * page) - 10));
                    Usuario usuario = request.session(true).attribute("usuario");
                    if (usuario != null) {
                        model.put("usuario", usuario.getUsername());
                    }
                    model.put("pasteSize", size);
                    return new ModelAndView(model, "pages.ftl");
                }, freeMarkerEngine);


                //ver un paste publico
                get("/:id", (request, response) -> {
                    Map<String, Object> model = new HashMap<>();
                    long id = Long.parseLong(request.params("id"));
                    PasteServices pasteServices = PasteServices.getInstancia();
                    Paste paste = pasteServices.find(id);

                    Usuario usuario = request.session(true).attribute("usuario");

                    if (usuario != null) {
                        Usuario usuario1 = usuarioServices.find(usuario.getId());
                        List<Paste> pastes = usuario1.getPastes();
                        boolean canEdit = false;
                        for (Paste paste1 : pastes) {
                            if (paste1.getId() == Long.parseLong(request.params("id"))) {
                                canEdit = true;
                                break;
                            }
                        }
                        if (canEdit) {
                            model.put("canEditAndDelete", "yes");
                        } else {
                            model.put("canEditAndDelete", "no");
                        }

                        model.put("user", usuario.getUsername());
                        model.put("usuario", usuario.getUsername());
                        System.out.println("was there");
                        model.put("usuarioId", usuario.getId());
                        System.out.println(usuario.getId());

                    } else {
                        model.put("user", "guest");
                    }

                    long fecha = paste.getFechaPublicacion();
                    fecha = fecha * 1000;
                    Date date = new Date(fecha);
                    SimpleDateFormat sdf = new SimpleDateFormat("EEEE,MMMM d,yyyy", Locale.getDefault());
                    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
                    String formattedDate = sdf.format(date);

                    model.put("fecha", formattedDate);
                    model.put("paste", paste);
                    model.put("titulo", "Public paste");

                    return new ModelAndView(model, "actualPaste.ftl");
                }, freeMarkerEngine);


            });
        });
//---------------------------User Crud-------------------------------------------
        path("/user", () -> {

            get("/signIn", (request, response) -> {
                Map<String, Object> attributes = new HashMap<>();
                attributes.put("titulo", "Login");
                Usuario usuario = request.session(true).attribute("usuario");
                if (usuario != null) {
                    attributes.put("usuario", usuario.getUsername());
                }
                return new ModelAndView(attributes, "login.ftl");
            }, freeMarkerEngine);

            post("/signIn", (request, response) -> {
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
                if (!usuarios.isEmpty()) {
                    if (usuarios.get(0).getUsername().equals(username) && usuarios.get(0).getPassword().equals(password)) {
                        usuario.setId(usuarios.get(0).getId());
                        usuario.setAdministrador(usuarios.get(0).getAdministrador());
                        usuario.setName(usuarios.get(0).getName());
                        session.attribute("usuario", usuario);
                        response.redirect("/");
                    }
                }
                attributes.put("message", "Error while signing in, please verify username and password");
                attributes.put("titulo", "Sign In");

                return new ModelAndView(attributes, "login.ftl");
            }, freeMarkerEngine);

            get("/signUp", (request, response) -> {
                Map<String, Object> attributes = new HashMap<>();

                attributes.put("titulo", "Registrar");

                return new ModelAndView(attributes, "registrar.ftl");
            }, freeMarkerEngine);

            post("/signUp", (request, response) -> {
                Map<String, Object> attributes = new HashMap<>();

                if (request.queryParams("password").matches(request.queryParams("password-confirm"))) {
                    Usuario newUsuario = new Usuario();
                    newUsuario.setName(request.queryParams("nombre"));
                    newUsuario.setAdministrador(false);
                    newUsuario.setPassword(request.queryParams("password"));
                    newUsuario.setUsername(request.queryParams("username"));
                    UsuarioServices usuarioServices1 = UsuarioServices.getInstancia();
                    usuarioServices1.crear(newUsuario);
                    response.redirect("/");
                } else {
                    attributes.put("confirm", "password doesn't match");
                }
                attributes.put("titulo", "Register User");
                return new ModelAndView(attributes, "registrar.ftl");
            }, freeMarkerEngine);

            get("/signOut", (request, response) -> {
               request.session().invalidate();
               response.redirect("/");
               return "";
            });

            get("/update/profile", (request, response) -> {
                Map<String, Object> model = new HashMap<>();
                model.put("titulo", "update user profile");

                Usuario usuario = request.session(true).attribute("usuario");
                if (usuario == null) {
                    response.redirect("/user/signIn");
                } else {
                    model.put("usuario", usuario.getUsername());
                }
                model.put("usuarioUpdate", usuario);

                return new ModelAndView(model, "UpdateUser.ftl");
            }, freeMarkerEngine);

            post("/update/profile", (request, response) -> {
                if (request.raw().getAttribute("org.eclipse.jetty.multipartConfig") == null) {
                    MultipartConfigElement multipartConfigElement = new MultipartConfigElement(System.getProperty("java.io.tmpdir"));
                    request.raw().setAttribute("org.eclipse.jetty.multipartConfig", multipartConfigElement);
                }
                Map<String, Object> model = new HashMap<>();
                model.put("titulo", "update user profile");

                Usuario usuario = request.session(true).attribute("usuario");
                usuario.setName(request.queryParams("name"));
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                usuario.setDateOfBirth(formatter.parse(request.queryParams("dateOfBirth")).getTime());
                usuario.setEmail(request.queryParams("email"));
                usuario.setOccupation(request.queryParams("occupation"));
                usuario.setPhoneNumber(request.queryParams("phoneNumber"));
                String out = "/home/anyderre/IdeaProjects/parcial#2/src/main/resources/publico/images/users/user" + usuario.getId() + ".png";


                String location = "/publico/images/users/";  // the directory location where files will be stored
                long maxFileSize = 100000000;  // the maximum size allowed for uploaded files
                long maxRequestSize = 100000000;  // the maximum size allowed for multipart/form-data requests
                int fileSizeThreshold = 1024;  // the size threshold after which files will be written to disk

                MultipartConfigElement multipartConfigElement = new MultipartConfigElement(location, maxFileSize, maxRequestSize, fileSizeThreshold);
                Part file = request.raw().getPart("photo");

                Path filePath = Paths.get(out);
                System.out.println(filePath);
                File f = new File(out);

                if (f.exists() && filePath.toString().equals(out)) {
                    Files.delete(filePath);
                }

                Files.copy(file.getInputStream(), filePath);

                if (usuario == null) {
                    response.redirect("/user/signIn");
                }

                response.redirect("/");
                return "";
            });


            get("/show/paste", (request, response) -> {
                Map<String, Object> model = new HashMap<>();
                Usuario usuario = request.session(true).attribute("usuario");
                if (usuario == null) {
                    response.redirect("/user/signIn");
                } else {
                    model.put("usuario", usuario.getUsername());
                }
                UsuarioServices usuarioServices1 = UsuarioServices.getInstancia();
                Usuario usuario1 = usuarioServices1.find(usuario.getId());
                List<Paste> pastes = usuario1.getPastes();
                model.put("titulo", "Show list Of user Paste");
                model.put("pastes", pastes);

                return new ModelAndView(model, "verPasteBin.ftl");
            }, freeMarkerEngine);


        get("/listar/user", (request, response) -> {
            ArrayList<Usuario> eTemp = new ArrayList<>();
            Map<String, Object> model = new HashMap<>();
            model.put("titulo", "Create an admin");

            Usuario usuario = request.session(true).attribute("usuario");
            if (usuario == null || !usuario.getAdministrador()) {
                response.redirect("/user/signIn");
                return new ModelAndView("message", "Sorry you're not an admin");
            }

            UsuarioServices usuarioServices1 = UsuarioServices.getInstancia();
            List<Usuario> usuarios = usuarioServices1.findAll();

            System.out.println(usuarios);
            for (Usuario us : usuarios) {
                if (!us.getAdministrador()) {
                    eTemp.add(us);
                    // usuarios.remove(us);
                }
            }
            model.put("usuarios", eTemp);

            return new ModelAndView(model, "createUserAdmin.ftl");
        }, freeMarkerEngine);

        //Deleting paste
        get("/delete/:id", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            Usuario usuario = request.session(true).attribute("usuario");
            UsuarioServices usuarioServices1 = UsuarioServices.getInstancia();
            PasteServices pasteServices = PasteServices.getInstancia();
            List<Paste> tempPaste = new ArrayList<>();


            Usuario usuario1 = usuarioServices1.find(usuario.getId());
            List<Paste> pastes = usuario1.getPastes();

            for (Paste paste : pastes) {
                if (paste.getId() != Long.parseLong(request.params("id"))) {
                    tempPaste.add(paste);
                }
            }
            usuario1.setPastes(tempPaste);
            usuarioServices1.editar(usuario1);
            response.redirect("/user/show/paste");
            return "";
        });

        get("/add/admin/:id", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("titulo", "Create an admin");
            UsuarioServices usuarioServices1 = UsuarioServices.getInstancia();
            List<Usuario> usuarios = usuarioServices1.findAll();

            for (Usuario us : usuarios) {
                if (us.getId() == Long.parseLong(request.params("id"))) {
                    us.setAdministrador(true);
                    usuarioServices1.editar(us);
                }
            }
            response.redirect("/user/listar/user");
            return "";
        });

    });
}
    private static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }


    public static byte[] extractBytes2(String ImageName) throws IOException {
        File imgPath = new File(ImageName);
        BufferedImage bufferedImage = ImageIO.read(imgPath);

        ByteOutputStream bos = null;
        try {
            bos = new ByteOutputStream();
            ImageIO.write(bufferedImage, "png", bos);
        } finally {
            try {
                bos.close();
            } catch (Exception e) {
            }
        }

        return bos == null ? null : bos.getBytes();

    }
}