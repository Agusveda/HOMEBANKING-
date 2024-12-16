<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link rel="stylesheet" type="text/css" href="css/Navbar.css">
    <link rel="stylesheet" type="text/css" href="css/Footer.css">
    
    <style>
		<jsp:include page="css/Reportes.css"></jsp:include>
    </style>
<title>Reportes</title>
</head>
<body>


<div class="encabezado">
    <h1>Seleccione el reporte que desee</h1>
</div>

    <form action="ServletReportes" method="get">
		
	<div class="botones-contenedor">
    	<input type="submit" class="BtnMovimientos" name="btnMovimientos" value="Movimientos" />
	</div>
	<br>
	<div class="botones-contenedor">
    	<input type="submit" class="BtnTransferencia" name="btnTransferencia" value="Transferencia" />
	</div>
	<div class="botones-contenedor">
    	<input type="submit" class="BtnCuentas" name="btnCuentas" value="Cuentas" />
	</div>
		
	
    </form>
    
   	 <a href="Administrador.jsp">
        <input class="BtnAtras" type="button" value="Atr�s" name="btnAtras">
     </a>




</body>
</html>

public class UseClobs {
   public static void main(String[] args) 
   throws SQLException 
   {
       // Registrar el controlador JDBC nativo.
       try {
          Class.forName("com.ibm.db2.jdbc.app.DB2Driver");
      } catch (Exception e) {
          System.exit(1);  // Error de configuraci�n.
      }
          
      Connection c = DriverManager.getConnection("jdbc:db2:*local");    
      Statement s = c.createStatement();
      
      ResultSet rs = s.executeQuery("SELECT * FROM CUJOSQL.CLOBTABLE");

      rs.next();
      Clob clob1 = rs.getClob(1);
      rs.next();
      Clob clob2 = rs.getClob(1);


      // Determinar la longitud de un LOB.
      long end = clob1.length();
      System.out.println("La longitud de Clob1 es " + clob1.length());

      // Al trabajar con objetos LOB, toda la indexaci�n relacionada con ellos
      // se basa en 1, y no en 0 como en las series y matrices.
      long startingPoint = 450;
      long endingPoint = 50;

      // Obtener parte del CLOB como matriz de bytes.
      String outString = clob1.getSubString(startingPoint, (int)endingPoint);
      System.out.println("La subserie de Clob es " + outString);

      // Buscar donde se encuentra primero un sub-CLOB o serie en un
      // CLOB. La configuraci�n de este programa ha colocado dos copias id�nticas 
      // de un CLOB repetido en la base de datos. Por tanto, la posici�n inicial 
      // de la serie extra�da de clob1 se puede encontrar en la
      // posici�n inicial de clob2 si la b�squeda empieza cerca de la posici�n 
	  // en la que empieza la serie.
      long startInClob2 = clob2.position(outString, 440);

      System.out.println("encontrado patr�n que empieza en la posici�n " + startInClob2);

      c.close(); // El cierre de la conexi�n tambi�n cierra stmt y rs.
   }
}
public class UseClobs {
   public static void main(String[] args) 
   throws SQLException 
   {
       // Registrar el controlador JDBC nativo.
       try {
          Class.forName("com.ibm.db2.jdbc.app.DB2Driver");
      } catch (Exception e) {
          System.exit(1);  // Error de configuraci�n.
      }
          
      Connection c = DriverManager.getConnection("jdbc:db2:*local");    
      Statement s = c.createStatement();
      
      ResultSet rs = s.executeQuery("SELECT * FROM CUJOSQL.CLOBTABLE");

      rs.next();
      Clob clob1 = rs.getClob(1);
      rs.next();
      Clob clob2 = rs.getClob(1);

public class PutGetBlobs {
   public static void main(String[] args) 
   throws SQLException 
   {
       // Registrar el controlador JDBC nativo.
       try {
          Class.forName("com.ibm.db2.jdbc.app.DB2Driver");
      } catch (Exception e) {
          System.exit(1);  // Error de configuraci�n.
      }
          
      // Establecer una conexi�n y una sentencia con las que trabajar.
      Connection c = DriverManager.getConnection("jdbc:db2:*local");    
      Statement s = c.createStatement();
      
      // Borrar ejecuciones anteriores de esta aplicaci�n.
      try {
          s.executeUpdate("DROP TABLE CUJOSQL.BLOBTABLE");
      } catch (SQLException e) {
          // Ignorarlo y suponer que la tabla no exist�a.
      }

      // Crear una tabla con una columna BLOB. El tama�o de la columna BLOB
      // predeterminado es 1 MB.
      s.executeUpdate("CREATE TABLE CUJOSQL.BLOBTABLE (COL1 BLOB)");

      // Crear un objeto PreparedStatement que permita colocar
      // un nuevo objeto Blob en la base de datos.
      PreparedStatement ps = c.prepareStatement("INSERT INTO CUJOSQL.BLOBTABLE VALUES(?)");

      // Crear un valor BLOB grande...
      Random random = new Random ();
      byte [] inByteArray = new byte[500000];
      random.nextBytes (inByteArray);

      // Establecer el par�metro PreparedStatement. Nota: esto no es 
      // portable a todospublic class PutGetBlobs {
   public static void main(String[] args) 
   throws SQLException 
   {
       // Registrar el controlador JDBC nativo.
       try {
          Class.forName("com.ibm.db2.jdbc.app.DB2Driver");
      } catch (Exception e) {
          System.exit(1);  // Error de configuraci�n.
      }
          
      // Establecer una conexi�n y una sentencia con las que trabajar.
      Connection c = DriverManager.getConnection("jdbc:db2:*local");    
      Statement s = c.createStatement();
      
      // Borrar ejecuciones anteriores de esta aplicaci�n.
      try {
          s.executeUpdate("DROP TABLE CUJOSQL.BLOBTABLE");
      } catch (SQLException e) {
          // Ignorarlo y suponer que la tabla no exist�a.
      }

      // Crear una tabla con una columna BLOB. El tama�o de la columna BLOB
      // predeterminado es 1 MB.
      s.executeUpdate("CREATE TABLE CUJOSQL.BLOBTABLE (COL1 BLOB)");

      // Crear un objeto PreparedStatement que permita colocar
      // un nuevo objeto Blob en la base de datos.
      PreparedStatement ps = c.prepareStatement("INSERT INTO CUJOSQL.BLOBTABLE VALUES(?)");

      // Crear un valor BLOB grande...
      Random random = new Random ();
      byte [] inByteArray = new byte[500000];
      random.nextBytes (inByteArray);

      // Establecer el par�metro PreparedStatement. Nota: esto no es 
      // portable a todos los controladores JDBC. Los controladores JDBC no tienen 
      // soporte al utilizar setBytes para columnas BLOB. Esto se utiliza para
      // permitir generar nuevos BLOB. Tambi�n permite que
      // los controladores JDBC 1.0 trabajar con columnas que contengan datos BLOB.
      ps.setBytes(1, inByteArray);

      // Procesar la sentencia, insertando el BLOB en la base de datos.
      ps.executeUpdate();

      // Procesar una consulta y obtener el BLOB que se acaba de insertar  
      // a partir de la base de datos como objeto Blob.
      ResultSet rs = s.executeQuery("SELECT * FROM CUJOSQL.BLOBTABLE");
      rs.next();
      Blob blob = rs.getBlob(1);uevos BLOB. Tambi�n permite que
      // los controladores JDBC 1.0 trabajar con columnas que contengan datos BLOB.
      ps.setBytes(1, inByteArray);

      // Procesar la sentencia, insertando el BLOB en la base de datos.
      ps.executeUpdate();

      // Procesar una consulta y obtener el BLOB que se acaba de insertar  
      // a partir de la base de datos como objeto Blob.
      ResultSet rs = s.executeQuery("SELECT * FROM CUJOSQL.BLOBTABLE");
      rs.next();
      Blob blob = rs.getBlob(1);
      // Determinar la longitud de un LOB.
      long end = clob1.length();
      System.out.println("La longitud de Clob1 es " + clob1.length());

      // Al trabajar con objetos LOB, toda la indexaci�n relacionada con ellos
      // se basa en 1, y no en 0 como en las series y matrices.
      long startingPoint = 450;
      long endingPoint = 50;

      // Obtener parte del CLOB como matriz de bytes.
      String outString = clob1.getSubString(startingPoint, (int)endingPoint);
      System.out.println("La subserie de Clob es " + outString);

      // Buscar donde se encuentra primero un sub-CLOB o serie en un
      // CLOB. La configuraci�n de este programa ha colocado dos copias id�nticas 
      // de un CLOB repetido en la base de datos. Por tanto, la posici�n inicial 
      // de la serie extra�da de clob1 se puede encontrar en la
      // posici�n inicial de clob2 si la b�squeda empieza cerca de la posici�n 
	  // en la que empieza la serie.
      long startInClob2 = clob2.position(outString, 440);

      System.out.println("encontrado patr�n que empieza en la posici�n " + startInClob2);

      c.close(); // El cierre de la conexi�n tambi�n cierra stmt y rs.
   }
}