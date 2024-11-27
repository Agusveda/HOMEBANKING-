package negocioImpl;

import Entidades.Movimiento;
import dao.MovimientoDao;
import daoImp.MovimientoDaoImp;
import negocio.MovimientoNegocio;

public class MovimientoNegocioImpl implements MovimientoNegocio
{
	
	private MovimientoDaoImp MovimientoDao = new MovimientoDaoImp();

	@Override
	public boolean insertar(Movimiento movi, int idCue) 
	{
		return MovimientoDao.insertar(movi, idCue);
	}

	@Override
	public int ObtenerIdCuentaPorCBU(int CBU) {
		
		return MovimientoDao.ObtenerIdCuentaPorCBU(CBU);
		
	}

	@Override
	public int ObtenerIdCuentaPorIdCliente(int IdCliente) {
		return MovimientoDao.ObtenerIdCuentaPorIdCliente(IdCliente);
	}
	
}