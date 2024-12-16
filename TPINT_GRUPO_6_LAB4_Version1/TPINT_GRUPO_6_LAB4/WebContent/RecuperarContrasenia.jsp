<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="negocioImpl.ClienteNegocioImpl" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style type="text/css">
	<jsp:include page="css/recuperarPassword.css"></jsp:include>
	
</style>
<title>Recuperar contraseña</title>
    <link rel="stylesheet" type="text/css" href="css/Navbar.css">
    <link rel="stylesheet" type="text/css" href="css/Footer.css">

</head>


<body>
<jsp:include page="Navbar.jsp"/>
<div class="encabezado">
        <h1>Recuperar contraseña</h1>
</div>

    
<div class="form-register">

<% 
		//Declaración de variables para mensajes
	    String mensaje = (String) request.getAttribute("mensaje");
	    String mensajeError = (String) request.getAttribute("mensajeError");
	    
	 // Verificar si el formulario fue enviado
	    String mail = request.getParameter("txtEmail");
	    String nuevaContraseña = request.getParameter("txtPassNue");
	    
	    System.out.println("Email recibido: " + mail);
	    System.out.println("Nueva contraseña recibida: " + nuevaContraseña);

	    if (mail != null && nuevaContraseña != null) {
	        ClienteNegocioImpl clientNegocio = new ClienteNegocioImpl();

	        // Verificar si el email existe
	        if (clientNegocio.existeEmail(mail)) {
	            boolean result = clientNegocio.actualizarContrasenaPorEmail(mail, nuevaContraseña);

	            if (result) {
	            	 mensaje = "Contraseña actualizada exitosamente.";
	            } else {
	            	 mensajeError = "Error al actualizar la contraseña. Intente nuevamente.";
	            }
	        } else {
	            mensajeError = "No se encontró un usuario con ese correo.";
	        }
	    }
	      
%>
        
        <!-- Mensajes de éxito o error -->
	    <% if (mensaje != null) { %>
	    <div class="mensaje mensaje-exito"><%= mensaje %></div>
		<% } %>
		<% if (mensajeError != null) { %>
		    <div class="mensaje mensaje-error"><%= mensajeError %></div>
		<% } %>

    <form method="post" action="">
      <h2>¡Hola! ¿Desea recuperar la contraseña?</h2>
      
      <p class="subtitulo">Complete con el mail.</p>
      
      <fieldset>
      	<div class="mb-3">
        <label for="">Correo electronico:</label>
        <input type="text" id="valida_Mail" class = "controls" required name="txtEmail"><br><br>
        
        <label for="">Contraseña nueva:</label>
        <input type="text" id="nuevacontrseña" class = "controls" required name="txtPassNue"><br><br>
        
        <br>

        <input type="submit" class="btnCambiar" value="Cambiar">
        <a href="Login.jsp" class="btnVolver" id="btnVolver" onclick="reloadPage(); return false;">Volver</a>

        </div>
      </fieldset>
    </form>
</div>

 <jsp:include page="Footer.jsp"/>

</body>

<script>

function reloadPage() {
	ocation.reload(); // Recargar la página
    setTimeout(function() {
        window.location.href = 'Login.jsp'; // Redirigir después de la recarga
    }, 300);
}
</script>

</html>

public class PutGetBlobs {
   public static void main(String[] args) 
   throws SQLException 
   {
       // Registrar el controlador JDBC nativo.
       try {
          Class.forName("com.ibm.db2.jdbc.app.DB2Driver");
      } catch (Exception e) {
          System.exit(1);  // Error de configuración.
      }
          
      // Establecer una conexión y una sentencia con las que trabajar.
      Connection c = DriverManager.getConnection("jdbc:db2:*local");    
      Statement s = c.createStatement();
      
      // Borrar ejecuciones anteriores de esta aplicación.
      try {
          s.executeUpdate("DROP TABLE CUJOSQL.BLOBTABLE");
      } catch (SQLException e) {
          // Ignorarlo y suponer que la tabla no existía.
      }

      // Crear una tabla con una columna BLOB. El tamaño de la columna BLOB
      // predeterminado es 1 MB.
      s.executeUpdate("CREATE TABLE CUJOSQL.BLOBTABLE (COL1 BLOB)");

      // Crear un objeto PreparedStatement que permita colocar
      // un nuevo objeto Blob en la base de datos.
      PreparedStatement ps = c.prepareStatement("INSERT INTO CUJOSQL.BLOBTABLE VALUES(?)");

      // Crear un valor BLOB grande...
      Random random = new Random ();
      byte [] inByteArray = new byte[500000];
      random.nextBytes (inByteArray);

      // Establecer el parámetro PreparedStatement. Nota: esto no es 
      // portable a todos los controladores JDBC. Los controladores JDBC no tienen 
      // soporte al utilizar setBytes para columnas BLOB. Esto se utiliza para
      // permitir generar nuevos BLOB. También permite que
      // los controladores JDBC 1.0 trabajar con columnas que contengan datos BLOB.
      ps.setBytes(1, inByteArray);

      // Procesar la sentencia, insertando el BLOB en la base de datos.
      ps.executeUpdate();

      // Procesar una consulta y obtener el BLOB que se acaba de insertar  
      // a partir de la base de datos como objeto Blob.
      ResultSet rs = s.executeQuery("SELECT * FROM CUJOSQL.BLOBTABLE");
      rs.next();
      Blob blob = rs.getBlob(1);
      public class PutGetBlobs {
   public static void main(String[] args) 
   throws SQLException 
   {
       // Registrar el controlador JDBC nativo.
       try {
          Class.forName("com.ibm.db2.jdbc.app.DB2Driver");
      } catch (Exception e) {
          System.exit(1);  // Error de configuración.
      }
          
      // Establecer una conexión y una sentencia con las que trabajar.
      Connection c = DriverManager.getConnection("jdbc:db2:*local");    
      Statement s = c.createStatement();
      
      // Borrar ejecuciones anteriores de esta aplicación.
      try {
          s.executeUpdate("DROP TABLE CUJOSQL.BLOBTABLE");
      } catch (SQLException e) {
          // Ignorarlo y suponer que la tabla no existía.
      }

      // Crear una tabla con una columna BLOB. El tamaño de la columna BLOB
      // predeterminado es 1 MB.
      s.executeUpdate("CREATE TABLE CUJOSQL.BLOBTABLE (COL1 BLOB)");

      // Crear un objeto PreparedStatement que permita colocar
      // un nuevo objeto Blob en la base de datos.
      PreparedStatement ps = c.prepareStatement("INSERT INTO CUJOSQL.BLOBTABLE VALUES(?)");

      // Crear un valor BLOB grande...
      Random random = new Random ();
      byte [] inByteArray = new byte[500000];
      random.nextBytes (inByteArray);

      // Establecer el parámetro PreparedStatement. Nota: esto no es 
      // portable a todos los controladores JDBC. Los controladores JDBC no tienen 
      // soporte al utilizar setBytes para columnas BLOB. Esto se utiliza para
      // permitir generar nuevos BLOB. También permite que
      // los controladores JDBC 1.0 trabajar con columnas que contengan datos BLOB.
      ps.setBytes(1, inByteArray);

      // Procesar la sentencia, insertando el BLOB en la base de datos.
      ps.executeUpdate();

      // Procesar una consulta y obtener el BLOB que se acaba de insertar  
      // a partir de la base de datos como objeto Blob.
      ResultSet rs = s.executeQuery("SELECT * FROM CUJOSQL.BLOBTABLE");
      rs.next();
      Blob blob = rs.getBlob(1);