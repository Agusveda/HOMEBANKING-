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
import Excepciones.ContraseñaDiferente;
import Excepciones.DniRepetido;
import Excepciones.UsuarioRepetido;
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
    	String action = request.getParameter("action");
    	  
           
    	if ("loadProvinces".equals(action)) {	 
 	 // Obtener el id de la nacionalidad seleccionada
     String nacionalidadId = request.getParameter("txtNacionalidad");
     String provinciaId = request.getParameter("txtProvincia");
     String localidadId = request.getParameter("txtLocalidad");

     System.out.println("Nacionalidad seleccionada: " + nacionalidadId);
     System.out.println("Provincia seleccionada: " + provinciaId);
     System.out.println("Localidad seleccionada: " + localidadId);


    
    	
    	
       

        // Manejo de alta de cliente
        if (request.getParameter("btnAltaCliente") != null) {
        	
            String contrasena1 = request.getParameter("txtContrasena1");
            String contrasena2 = request.getParameter("txtContrasena2");
            
            try {
            	if (contrasena1 == null || contrasena2 == null || !contrasena1.equals(contrasena2)) {
            		throw new ContraseñaDiferente();
            	}
				
			} catch (ContraseñaDiferente e) {
				// Manejo de la excepción personalizada
		        System.out.println("Excepción capturada: " + e.getMessage());
		        request.setAttribute("mensajeError", e.getMessage());
		        request.getRequestDispatcher("/AltaCliente.jsp").forward(request, response);
		        return;
			}
			
            ClienteNegocioImpl bandolero = new ClienteNegocioImpl();
            
            Usuario usu = new Usuario();
            Cliente cli = new Cliente();
            cli.setNombre(request.getParameter("txtNombre"));
            cli.setApellido(request.getParameter("txtApellido"));
            cli.setDni(Integer.parseInt(request.getParameter("txtDNI")));
            cli.setCuil(Long.parseLong(request.getParameter("txtCUIL")));
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
            long cuil = Long.parseLong(request.getParameter("txtCUIL"));
            String user = request.getParameter("txtUsuario");
            
            try {
            	if (bandolero.ValidacionDni(dni)) {
            		throw new DniRepetido("El DNI ya figura en la base de datos");
            	}
				
			} catch (DniRepetido e) {
				System.out.println("Excepción capturada: " + e.getMensajeError());
		        request.setAttribute("mensajeError", e.getMensajeError());
		        request.getRequestDispatcher("/AltaCliente.jsp").forward(request, response);
		        return;
			}
            
            
            if (bandolero.ValidacionCuil(cuil)) {
                request.setAttribute("mensajeError", "El CUIL ya existe en la base de datos. Por favor, intente con otro CUIL.");
                System.out.println("Mensaje de error: El CUIL ya existe en la base de datos.");
                request.getRequestDispatcher("/AltaCliente.jsp").forward(request, response);
                return;
            }
            
            try {
            	if (bandolero.ValidacionUsuario(user)) {
            		throw new UsuarioRepetido("El Usuario ya existe en la base de datos. Por favor, intente con otro Usuario.");
            	}
				
			} catch (UsuarioRepetido e) {
				System.out.println("Excepción capturada: " + e.getMensajeError());
		        request.setAttribute("mensajeError", e.getMensajeError());
		        request.getRequestDispatcher("/AltaCliente.jsp").forward(request, response);
		        return;
			}
            
            
            
            boolean insertado = bandao.insertCliente(cli, usu);
            

            if (insertado) {
                request.setAttribute("mensaje", "Cliente registrado exitosamente.");
             // Restablecer valores de los desplegables
                request.setAttribute("limpiarFormulario", true);
                request.setAttribute("txtNacionalidad", null);
                request.setAttribute("provincias", null);
                request.setAttribute("localidades", null);
               
            } else {
                request.setAttribute("mensajeError", "Hubo un error al registrar el cliente.");
                System.out.println("Mensaje: Cliente registrado exitosamente.");
            }
            request.getRequestDispatcher("/AltaCliente.jsp").forward(request, response);
        }
        
        if (nacionalidadId != null && !nacionalidadId.isEmpty()) {
            int idNacionalidad = Integer.parseInt(nacionalidadId);

            // Crear una instancia de ClienteDaoImp
            ClienteDaoImp clienteDao = new ClienteDaoImp();

            // Llamar al método listProvincias() de ClienteDaoImp
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

            // Llamar al método listLocalidades() de ClienteDaoImp
            ArrayList<Localidad> localidades = clienteDao.listLocalidades(idProvincia);

            // Verificar cuántas localidades se encuentran
            System.out.println("Cantidad de localidades encontradas: " + localidades.size());

            // Poner la lista de localidades en el request
            request.setAttribute("localidades", localidades);
        } else {
            // Si no se recibe idProvincia
            System.out.println("No se ha recibido idProvincia o está vacío.");
        }

        // Redirigir o forward a la página JSP para mostrar las localidades
        request.getRequestDispatcher("AltaCliente.jsp").forward(request, response);
        

       	}
    	
    	// Modificar cliente
    	 if (request.getParameter("btnModificarCliente") != null) {
             Cliente cli = new Cliente();
             Usuario usu = new Usuario();
             ClienteNegocioImpl bandolero = new ClienteNegocioImpl();

             cli.setId(Integer.parseInt(request.getParameter("txtId")));
             cli.setNombre(request.getParameter("txtNombre"));
             cli.setApellido(request.getParameter("txtApellido"));
             cli.setDni(Integer.parseInt(request.getParameter("txtDNI")));
             cli.setCuil(Long.parseLong(request.getParameter("txtCUIL")));
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
             
             int id = Integer.parseInt(request.getParameter("txtId"));
             int dni = Integer.parseInt(request.getParameter("txtDNI"));
             long cuil = Long.parseLong(request.getParameter("txtCUIL"));
             String user = request.getParameter("txtUsuario");
             
             
             if (bandolero.ValidacionDniModificar(dni, id)) {
                 request.setAttribute("mensajeError", "El DNI ya figura en la base de datos.");
                 request.getRequestDispatcher("/ModificarCliente.jsp").forward(request, response);
                 return;
             }
             
             if (bandolero.ValidacionCuilModificar(cuil, id)) {
                 request.setAttribute("mensajeError", "El CUIL ya existe en la base de datos. Por favor, intente con otro CUIL.");
                 request.getRequestDispatcher("/ModificarCliente.jsp").forward(request, response);
                 return;
             }
             
             try {
             	if (bandolero.ValidacionUsuarioModificar(user, id)) {
             		throw new UsuarioRepetido("El Usuario ya existe en la base de datos. Por favor, intente con otro Usuario.");
             	}
 				
 			} catch (UsuarioRepetido e) {
 				System.out.println("Excepción capturada: " + e.getMensajeError());
 		        request.setAttribute("mensajeError", e.getMensajeError());
 		        request.getRequestDispatcher("/AltaCliente.jsp").forward(request, response);
 		        return;
 			}
             
            
             
             boolean insertado2 = false;
             
             
             insertado2 = bandolero.ModificarCliente(cli, usu);
             if (insertado2) {
                 request.setAttribute("mensaje", "Cliente modificado exitosamente.");
              // Restablecer valores de los desplegables
                 request.setAttribute("limpiarFormulario", true);
                 request.setAttribute("provincias", null);
                 request.setAttribute("localidades", null);                
             } else 
             {
                 request.setAttribute("mensajeError", "Hubo un error al modificar el cliente.");
                 System.out.println("Mensaje: Cliente modificado exitosamente.");
             }
             request.getRequestDispatcher("/ModificarCliente.jsp").forward(request, response);
         }

        // Validación del login
        String username = request.getParameter("txtuser");
        String password = request.getParameter("txtpass");

        if (username != null && password != null) 
        {
            ClienteNegocioImpl bandolero = new ClienteNegocioImpl();
            if (bandolero.verificarCredenciales(username, password) == null)
            {
            	request.getSession().setAttribute("mensajeError", "Usuario o contraseña incorrectos");
                request.getRequestDispatcher("/Login.jsp").forward(request, response);
            }
            Usuario usuario = bandolero.verificarCredenciales(username, password);
            
            Cliente cliente = bandolero.ObtenerDatosXid(usuario.getIdCliente()); 
            
            if (usuario != null) {
            	request.removeAttribute("mensajeError");
                int tipoUsuario = usuario.getTipoUsuario();
                request.getSession().setAttribute("IdCliente", usuario.getIdCliente());
                request.getSession().setAttribute("nombreCliente", cliente.getNombre()); 
                request.getSession().setAttribute("tipoUsuario", tipoUsuario); 
                
                if (tipoUsuario == 1) 
                {
                    
                    response.sendRedirect("Administrador.jsp");
                } else {
                
                    response.sendRedirect("Cliente.jsp");
                }
            } 
        }
        
        
     
        
        String mail = request.getParameter("txtEmail");
		String nuevaContraseña  = request.getParameter("txtPassNue");
		
		ClienteNegocioImpl ClientN = new ClienteNegocioImpl();
		
		if(request.getParameter("btnCambiar") != null)
		{
			
			if(ClientN.existeEmail(mail)) 
			{
				boolean result = ClientN.actualizarContrasenaPorEmail(mail, nuevaContraseña);
			
				if (result) {
					request.setAttribute("mensaje", " Contraseña actualizada exitosamente.");
				} else 
				{
					request.setAttribute("mensaje","Error al actualizar la contraseña.");
				}	
				request.getRequestDispatcher("/RecuperarContraseña.jsp").forward(request, response);	
			}
		}
        
        
}
}
