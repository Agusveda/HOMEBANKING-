package Servlet;

import java.io.IOException;
import java.util.ArrayList;

import negocioImpl.ClienteNegocioImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Entidades.Cliente;
import Entidades.Localidad;
import Entidades.Provincia;
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
                System.out.println("ID de cliente no v�lido.");
            }
        } else {
            System.out.println("No se ha proporcionado un ID de cliente.");
        }


    
        request.getRequestDispatcher("/Administrador.jsp").forward(request, response);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String action = request.getParameter("action");

    	if ("loadProvinces".equals(action)) {	 
 	 // Obtener el id de la nacionalidad seleccionada
     String nacionalidadId = request.getParameter("txtNacionalidad");
     String provinciaId = request.getParameter("txtProvincia");
     String localidadId = request.getParameter("txtLocalidad");

     System.out.println("Nacionalidad seleccionada: " + nacionalidadId);
     System.out.println("Provincia seleccionada: " + provinciaId);
     System.out.println("Localidad seleccionada: " + localidadId);


     if (nacionalidadId != null && !nacionalidadId.isEmpty()) {
         int idNacionalidad = Integer.parseInt(nacionalidadId);

         // Crear una instancia de ClienteDaoImp
         ClienteDaoImp clienteDao = new ClienteDaoImp();

         // Llamar al m�todo listProvincias() de ClienteDaoImp
         ArrayList<Provincia> provincias = clienteDao.listProvincias(idNacionalidad);

         // Poner la lista de provincias en el request
         request.setAttribute("provincias", provincias);
     }

     provinciaId = request.getParameter("txtProvincia");
     System.out.println("Provincia seleccionada: " + provinciaId);
     
     if (provinciaId != null && !provinciaId.isEmpty()) {
         // Verificar si se recibe el idProvincia correctamente
         System.out.println("Provincia seleccionada: " + provinciaId);

         int idProvincia = Integer.parseInt(provinciaId);

         // Crear una instancia de ClienteDaoImp (o la clase correspondiente)
         ClienteDaoImp clienteDao = new ClienteDaoImp();

         // Llamar al m�todo listLocalidades() de ClienteDaoImp
         ArrayList<Localidad> localidades = clienteDao.listLocalidades(idProvincia);

         // Verificar cu�ntas localidades se encuentran
         System.out.println("Cantidad de localidades encontradas: " + localidades.size());

         // Poner la lista de localidades en el request
         request.setAttribute("localidades", localidades);
     } else {
         // Si no se recibe idProvincia
         System.out.println("No se ha recibido idProvincia o est� vac�o.");
     }

     // Redirigir o forward a la p�gina JSP para mostrar las localidades
     request.getRequestDispatcher("AltaCliente.jsp").forward(request, response);
     

    	}
    	
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
            usu.setContrase�a(request.getParameter("txtContrasena"));
            
            int id = Integer.parseInt(request.getParameter("txtId"));
            int dni = Integer.parseInt(request.getParameter("txtDNI"));
            int cuil = Integer.parseInt(request.getParameter("txtCUIL"));
            String user = request.getParameter("txtUsuario");
            
            if (bandolero.ValidacionDniModificar(dni, id)) {
                request.setAttribute("mensajeError", "El DNI ya existe en la base de datos. Por favor, intente con otro DNI.");
                request.getRequestDispatcher("/ModificarCliente.jsp").forward(request, response);
                return;
            }
            if (bandolero.ValidacionCuilModificar(cuil, id)) {
                request.setAttribute("mensajeError", "El CUIL ya existe en la base de datos. Por favor, intente con otro CUIL.");
                request.getRequestDispatcher("/ModificarCliente.jsp").forward(request, response);
                return;
            }
            if (bandolero.ValidacionUsuarioModificar(user, id)) {
                request.setAttribute("mensajeError", "El Usuario ya existe en la base de datos. Por favor, intente con otro Usuario.");
                request.getRequestDispatcher("/ModificarCliente.jsp").forward(request, response);
                return;
            }

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
                request.setAttribute("mensajeError", "La contrase�a no coincide");
                System.out.println("Mensaje de error: Las contrase�as no coinciden.");
                /*
                RequestDispatcher dispatcher = request.getRequestDispatcher("/AltaCliente.jsp");
                dispatcher.forward(request, response)*/
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
            usu.setContrase�a(contrasena1);
            int tipoUsuario = 0;
            try {
                tipoUsuario = Integer.parseInt(request.getParameter("txtTipoUsuario"));
            } catch (NumberFormatException e) {
                
            }
            usu.setTipoUsuario(tipoUsuario);

            ClienteNegocioImpl bandao = new ClienteNegocioImpl();
            
            int dni = Integer.parseInt(request.getParameter("txtDNI"));
            int cuil = Integer.parseInt(request.getParameter("txtCUIL"));
            String user = request.getParameter("txtUsuario");
            
            if (bandolero.ValidacionDni(dni)) {
                request.setAttribute("mensajeError", "El DNI ya existe en la base de datos. Por favor, intente con otro DNI.");
                System.out.println("Mensaje de error: El DNI ya existe en la base de datos.");
                request.getRequestDispatcher("/AltaCliente.jsp").forward(request, response);
                return;
            }
            if (bandolero.ValidacionCuil(cuil)) {
                request.setAttribute("mensajeError", "El CUIL ya existe en la base de datos. Por favor, intente con otro CUIL.");
                System.out.println("Mensaje de error: El CUIL ya existe en la base de datos.");
                request.getRequestDispatcher("/AltaCliente.jsp").forward(request, response);
                return;
            }
            if (bandolero.ValidacionUsuario(user)) {
                request.setAttribute("mensajeError", "El Usuario ya existe en la base de datos. Por favor, intente con otro Usuario.");
                System.out.println("Mensaje de error: El USUARIO ya existe en la base de datos.");
                request.getRequestDispatcher("/AltaCliente.jsp").forward(request, response);	
                return;
            }
            
            
            boolean insertado = bandao.insertCliente(cli, usu);
            

            if (insertado) {
                request.setAttribute("mensaje", "Cliente registrado exitosamente.");
             // Restablecer valores de los desplegables
             // request.setAttribute("nacionalidad",null);
                request.setAttribute("provincias", null);
                request.setAttribute("localidades", null);
            } else {
                request.setAttribute("mensajeError", "Hubo un error al registrar el cliente.");
                System.out.println("Mensaje: Cliente registrado exitosamente.");
            }
            request.getRequestDispatcher("/AltaCliente.jsp").forward(request, response);
        }

        // Validaci�n del login
        String username = request.getParameter("txtuser");
        String password = request.getParameter("txtpass");

        if (username != null && password != null) 
        {
            ClienteNegocioImpl bandolero = new ClienteNegocioImpl();
            if (bandolero.verificarCredenciales(username, password) == null)
            {
            	request.getSession().setAttribute("Error", "Usuario o contrase�a incorrectos");
                request.getRequestDispatcher("/Error.jsp").forward(request, response);
            }
            Usuario usuario = bandolero.verificarCredenciales(username, password);
            
            Cliente cliente = bandolero.ObtenerDatosXid(usuario.getIdCliente()); //1. Cliente que inicia sesion
            
            if (usuario != null) {
            	request.removeAttribute("mensajeError");
                int tipoUsuario = usuario.getTipoUsuario();
                request.getSession().setAttribute("IdCliente", usuario.getIdCliente());
                request.getSession().setAttribute("nombreCliente", cliente.getNombre()); //2. Nombre cliente sesion
                request.getSession().setAttribute("tipoUsuario", tipoUsuario); //3. Tipo de usuario
                
                if (tipoUsuario == 1) 
                {
                    
                    response.sendRedirect("Administrador.jsp");
                } else {
                
                    response.sendRedirect("Cliente.jsp");
                }
            } 
        }
        
        String mail = request.getParameter("txtEmail");
		String nuevaContrase�a  = request.getParameter("txtPassNue");
		
		ClienteNegocioImpl ClientN = new ClienteNegocioImpl();
		
		if(request.getParameter("btnCambiar") != null){
			
		if(ClientN.existeEmail(mail)) {
			boolean result = ClientN.actualizarContrasenaPorEmail(mail, nuevaContrase�a);
			
			if (result) {
                request.setAttribute("mensaje", " Contrase�a actualizada exitosamente.");
            } else {
                request.setAttribute("mensaje","Error al actualizar la contrase�a.");
            }
            request.getRequestDispatcher("/RecuperarContrase�a.jsp").forward(request, response);	
	}
}
        
        
}
}
