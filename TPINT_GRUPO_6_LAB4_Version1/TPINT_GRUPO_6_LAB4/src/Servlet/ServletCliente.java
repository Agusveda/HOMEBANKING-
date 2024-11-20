package Servlet;

import java.io.IOException;
import negocio.ClienteNegocio;
import negocioImpl.ClienteNegocioImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Entidades.Cliente;
import Entidades.Usuario;
import daoImp.ClienteDaoImp;

@WebServlet("/ServletBanco")
public class ServletCliente extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ServletCliente() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("btnPrestamos");
        String idClienteParam = request.getParameter("idCliente");

      
        if ("Prestamos".equals(action)) {
            response.sendRedirect("prestamoCliente.jsp");
            return; 
        }

        if (idClienteParam != null) {
            try {
                int idCliente = Integer.parseInt(idClienteParam);
                ClienteNegocioImpl banco = new ClienteNegocioImpl();          
                boolean bajaExitosa = banco.eliminarCliente(idCliente);
                if (bajaExitosa) {
                    System.out.println("Cliente eliminado exitosamente.");
                    response.sendRedirect("ListarCliente.jsp");
                    return;
                } else {
                    System.out.println("Hubo un error al eliminar al cliente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("ID de cliente no válido.");
            }
        } else {
            System.out.println("No se ha proporcionado un ID de cliente.");
        }


    
        request.getRequestDispatcher("/Administrador.jsp").forward(request, response);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Modificar de Cliente
        if (request.getParameter("btnModificarCliente") != null) {
            Cliente cli = new Cliente();
            Usuario usu = new Usuario();
            ClienteNegocioImpl bandolero = new ClienteNegocioImpl();

            cli.setId(Integer.parseInt(request.getParameter("txtId")));
            cli.setNombre(request.getParameter("txtNombre"));
            cli.setApellido(request.getParameter("txtApellido"));
            cli.setDni(Integer.parseInt(request.getParameter("txtDNI")));
            cli.setCuil(Integer.parseInt(request.getParameter("txtCUIL")));
            cli.setSexo(request.getParameter("txtSexo"));
            cli.setNacionalidad(request.getParameter("txtNacionalidad"));
            cli.setFechaNacimiento(request.getParameter("txtFechaNacimiento"));
            cli.setDireccion(request.getParameter("txtDireccion"));
            cli.setLocalidad(request.getParameter("txtLocalidad"));
            cli.setProvincia(request.getParameter("txtProvincia"));
            cli.setCorreoElectronico(request.getParameter("txtEmail"));
            cli.setTelefono(Integer.parseInt(request.getParameter("txtTelefono")));

            usu.setUsuario(request.getParameter("txtUsuario"));
            usu.setContraseña(request.getParameter("txtContrasena"));

            bandolero.ModificarCliente(cli, usu);
            RequestDispatcher vari = request.getRequestDispatcher("/Administrador.jsp");
            vari.forward(request, response);  
            return;
        }

        // Manejo de alta de cliente
        if (request.getParameter("btnAltaCliente") != null) {
            String contrasena1 = request.getParameter("txtContrasena1");
            String contrasena2 = request.getParameter("txtContrasena2");

            if (contrasena1 == null || contrasena2 == null || !contrasena1.equals(contrasena2)) {
                request.setAttribute("mensajeError", "Las contraseñas no coinciden. Por favor, intente nuevamente.");
                request.getRequestDispatcher("/AltaCliente.jsp").forward(request, response);
                return;
            }

            ClienteNegocioImpl bandolero = new ClienteNegocioImpl();
            
            
             
            Usuario usu = new Usuario();
            Cliente cli = new Cliente();
            cli.setNombre(request.getParameter("txtNombre"));
            cli.setApellido(request.getParameter("txtApellido"));
            cli.setDni(Integer.parseInt(request.getParameter("txtDNI")));
            cli.setCuil(Integer.parseInt(request.getParameter("txtCUIL")));
            cli.setSexo(request.getParameter("txtSexo"));
            cli.setNacionalidad(request.getParameter("txtNacionalidad"));
            cli.setFechaNacimiento(request.getParameter("txtFechaNacimiento"));
            cli.setDireccion(request.getParameter("txtDireccion"));
            cli.setLocalidad(request.getParameter("txtLocalidad"));
            cli.setProvincia(request.getParameter("txtProvincia"));
            cli.setCorreoElectronico(request.getParameter("txtEmail"));
            cli.setTelefono(Integer.parseInt(request.getParameter("txtTelefono")));

            usu.setUsuario(request.getParameter("txtUsuario"));
            usu.setContraseña(contrasena1);
            int tipoUsuario = 0;
            try {
                tipoUsuario = Integer.parseInt(request.getParameter("txtTipoUsuario"));
            } catch (NumberFormatException e) {
                
            }
            usu.setTipoUsuario(tipoUsuario);

            ClienteNegocioImpl bandao = new ClienteNegocioImpl();
            
            int dni = Integer.parseInt(request.getParameter("txtDNI"));
            int cuil = Integer.parseInt(request.getParameter("txtCUIL"));
            
            if (bandolero.ValidacionDni(dni)) {
                request.setAttribute("mensajeError", "El DNI ya existe en la base de datos. Por favor, intente con otro DNI.");
                request.getRequestDispatcher("/AltaCliente.jsp").forward(request, response);
                return;
            }
            if (bandolero.ValidacionDni(cuil)) {
                request.setAttribute("mensajeError", "El CUIL ya existe en la base de datos. Por favor, intente con otro CUIL.");
                request.getRequestDispatcher("/AltaCliente.jsp").forward(request, response);
                return;
            }
            
            
            boolean insertado = bandao.insertCliente(cli, usu);
            

            String mensaje = insertado ? "Cliente registrado exitosamente." : "Hubo un error al registrar el cliente.";
            request.setAttribute("mensaje", mensaje);
            request.getRequestDispatcher("/Administrador.jsp").forward(request, response);
        }

        // Validación del login
        String username = request.getParameter("txtuser");
        String password = request.getParameter("txtpass");

        if (username != null && password != null) {
            ClienteNegocioImpl bandolero = new ClienteNegocioImpl();
            Usuario usuario = bandolero.verificarCredenciales(username, password);
            
            if (usuario != null) {
            	request.removeAttribute("mensajeError");
                int tipoUsuario = usuario.getTipoUsuario();  
                
                
                if (tipoUsuario == 1) {
                    
                    response.sendRedirect("Administrador.jsp");
                } else {
                
                    response.sendRedirect("Cliente.jsp");
                }
            } else {
                request.setAttribute("mensajeError", "Usuario o contraseña incorrectos");
                request.getRequestDispatcher("/Login.jsp").forward(request, response);
               
            }
        } else {
        	
            request.setAttribute("mensajeError", "Usuario o contraseña incorrectos");
            request.getRequestDispatcher("/Login.jsp").forward(request, response);
        }
        
}
}
