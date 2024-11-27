package negocioImpl;

import Entidades.Movimiento;
import dao.MovimientoDao;
import daoImp.MovimientoDaoImp;
import negocio.MovimientoNegocio;

public class MovimientoNegocioImpl implements MovimientoNegocio
{
	
	private MovimientoDao mov = new MovimientoDaoImp();

	@Override
	public boolean insertar(Movimiento movi) 
	{
		return mov.insertar(movi);
	}
	
}
