package com.hotel.dao;

import com.hotel.interfaces.IRecepcionDAO;
import com.hotel.modelo.Recepcion;
import com.hotel.util.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RecepcionDAO implements IRecepcionDAO {

    private Connection con;
    private PreparedStatement ps;

public boolean agregar(Recepcion r) {
    String sql = "INSERT INTO RECEPCION (IdCliente, IdHabitacion, FechaSalida, PrecioInicial, Adelanto, PrecioRestante, TotalPagado, Estado, IdEstadoReserva) "
               + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    try {
        con = Conexion.getConexion();
        ps = con.prepareStatement(sql);
        ps.setInt(1, r.getIdCliente());
        ps.setInt(2, r.getIdHabitacion());
        ps.setTimestamp(3, r.getFechaSalida());
        ps.setDouble(4, r.getPrecioInicial());
        ps.setDouble(5, r.getAdelanto());
        ps.setDouble(6, r.getPrecioRestante());
        ps.setDouble(7, r.getTotalPagado());
        ps.setBoolean(8, r.isEstado());
        ps.setInt(9, r.getIdEstadoReserva());
        ps.executeUpdate();
        return true;
    } catch (Exception e) {
        e.printStackTrace(); // Ver en consola cualquier error de SQL
        return false;
    }
}

    
   public List<Recepcion> listar() {
    List<Recepcion> lista = new ArrayList<>();
    String sql = "SELECT r.*, " +
       "u.Nombre AS nombreCliente, " +
       "h.Numero AS numeroHabitacion, " +
       "c.Descripcion AS categoriaDescripcion, " +
       "e.Descripcion AS estadoReservaDescripcion " +
       "FROM RECEPCION r " +
       "JOIN PERSONA u ON r.IdCliente = u.IdPersona " +
       "JOIN HABITACION h ON r.IdHabitacion = h.IdHabitacion " +
       "JOIN CATEGORIA c ON h.IdCategoria = c.IdCategoria " +
       "JOIN ESTADO_RESERVA e ON r.IdEstadoReserva = e.IdEstadoReserva";

    try (Connection con = Conexion.getConexion();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            Recepcion r = new Recepcion();
            r.setIdRecepcion(rs.getInt("IdRecepcion"));
            r.setIdCliente(rs.getInt("IdCliente"));
            r.setIdHabitacion(rs.getInt("IdHabitacion"));
            r.setFechaEntrada(rs.getTimestamp("FechaEntrada"));
            r.setFechaSalida(rs.getTimestamp("FechaSalida"));
            r.setPrecioInicial(rs.getDouble("PrecioInicial"));
            r.setAdelanto(rs.getDouble("Adelanto"));
            r.setPrecioRestante(rs.getDouble("PrecioRestante"));
            r.setTotalPagado(rs.getDouble("TotalPagado"));
            r.setEstado(rs.getBoolean("Estado"));
            r.setIdEstadoReserva(rs.getInt("IdEstadoReserva"));
            r.setEstadoReservaDescripcion(rs.getString("estadoReservaDescripcion"));

            r.setNombreCliente(rs.getString("nombreCliente"));
            r.setNumeroHabitacion(rs.getString("numeroHabitacion"));
            r.setCategoriaDescripcion(rs.getString("categoriaDescripcion"));

            lista.add(r);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return lista;
}

public boolean anular(int idRecepcion) {
    String sql = "UPDATE RECEPCION SET Estado = 0, IdEstadoReserva = 5 WHERE IdRecepcion = ?"; // 5 = CANCELADA
    try (Connection con = Conexion.getConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, idRecepcion);
        ps.executeUpdate();
        return true;
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}


public Recepcion obtener(int idRecepcion) {
    Recepcion r = null;
    String sql = """
        SELECT r.*, 
               CONCAT(p.Nombre, ' ', p.Apellido) AS nombreCliente,
               h.Numero AS numeroHabitacion,
               c.Descripcion AS categoriaDescripcion,
               er.Descripcion AS estadoReservaDescripcion
        FROM RECEPCION r
        JOIN PERSONA p ON r.IdCliente = p.IdPersona
        JOIN HABITACION h ON r.IdHabitacion = h.IdHabitacion
        JOIN CATEGORIA c ON h.IdCategoria = c.IdCategoria
        JOIN ESTADO_RESERVA er ON r.IdEstadoReserva = er.IdEstadoReserva
        WHERE r.IdRecepcion = ?
    """;

    try (Connection con = Conexion.getConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, idRecepcion);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            r = new Recepcion();
            r.setIdRecepcion(rs.getInt("IdRecepcion"));
            r.setIdCliente(rs.getInt("IdCliente"));
            r.setIdHabitacion(rs.getInt("IdHabitacion"));
            r.setFechaEntrada(rs.getTimestamp("FechaEntrada"));
            r.setFechaSalida(rs.getTimestamp("FechaSalida"));
            r.setPrecioInicial(rs.getDouble("PrecioInicial"));
            r.setAdelanto(rs.getDouble("Adelanto"));
            r.setPrecioRestante(rs.getDouble("PrecioRestante"));
            r.setTotalPagado(rs.getDouble("TotalPagado"));
            r.setEstado(rs.getBoolean("Estado"));

            // Atributos adicionales para mostrar
            r.setNombreCliente(rs.getString("nombreCliente"));
            r.setNumeroHabitacion(rs.getString("numeroHabitacion"));
            r.setCategoriaDescripcion(rs.getString("categoriaDescripcion"));
            r.setIdEstadoReserva(rs.getInt("IdEstadoReserva"));
            r.setEstadoReservaDescripcion(rs.getString("estadoReservaDescripcion"));


        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return r;
}


public boolean actualizarEstado(int idHabitacion, int nuevoEstado) {
    String sql = "UPDATE HABITACION SET IdEstadoHabitacion = ? WHERE IdHabitacion = ?";
    try (Connection con = Conexion.getConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, nuevoEstado); // 1: DISPONIBLE, 2: OCUPADO, etc.
        ps.setInt(2, idHabitacion);
        ps.executeUpdate();
        return true;

    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}


public boolean checkout(int idRecepcion, int idHabitacion) {
    String sqlRecepcion = "UPDATE RECEPCION SET Estado = 0, IdEstadoReserva = 4, FechaSalidaConfirmacion = NOW() WHERE IdRecepcion = ?"; // 4 = FINALIZADA
    String sqlHabitacion = "UPDATE HABITACION SET IdEstadoHabitacion = 3 WHERE IdHabitacion = ?"; // 3 = LIMPIEZA

    try (Connection con = Conexion.getConexion();
         PreparedStatement psRecepcion = con.prepareStatement(sqlRecepcion);
         PreparedStatement psHabitacion = con.prepareStatement(sqlHabitacion)) {

        psRecepcion.setInt(1, idRecepcion);
        psRecepcion.executeUpdate();

        psHabitacion.setInt(1, idHabitacion);
        psHabitacion.executeUpdate();

        return true;

    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}


public boolean reactivar(int idRecepcion, int idHabitacion) {
    String sqlRecepcion = "UPDATE RECEPCION SET Estado = 1, IdEstadoReserva = 3 WHERE IdRecepcion = ?"; // 3 = ACTIVA
    String sqlHabitacion = "UPDATE HABITACION SET IdEstadoHabitacion = 2 WHERE IdHabitacion = ?"; // 2 = OCUPADO

    try (Connection con = Conexion.getConexion();
         PreparedStatement psRecepcion = con.prepareStatement(sqlRecepcion);
         PreparedStatement psHabitacion = con.prepareStatement(sqlHabitacion)) {

        psRecepcion.setInt(1, idRecepcion);
        psRecepcion.executeUpdate();

        psHabitacion.setInt(1, idHabitacion);
        psHabitacion.executeUpdate();

        return true;

    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}

public List<Recepcion> listarPorCliente(int idCliente) {
    List<Recepcion> lista = new ArrayList<>();
    String sql = """
        SELECT r.*, 
               h.Numero AS numeroHabitacion,
               c.Descripcion AS categoriaDescripcion,
               p.Nombre, p.Apellido
        FROM RECEPCION r
        INNER JOIN HABITACION h ON r.IdHabitacion = h.IdHabitacion
        INNER JOIN CATEGORIA c ON h.IdCategoria = c.IdCategoria
        INNER JOIN PERSONA p ON r.IdCliente = p.IdPersona
        WHERE r.IdCliente = ?
    """;

    try (Connection con = Conexion.getConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, idCliente);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Recepcion r = new Recepcion();
            r.setIdRecepcion(rs.getInt("IdRecepcion"));
            r.setIdCliente(rs.getInt("IdCliente"));
            r.setIdHabitacion(rs.getInt("IdHabitacion"));
            r.setFechaEntrada(rs.getTimestamp("FechaEntrada"));
            r.setFechaSalida(rs.getTimestamp("FechaSalida"));
            r.setFechaSalidaConfirmacion(rs.getTimestamp("FechaSalidaConfirmacion"));
            r.setPrecioInicial(rs.getDouble("PrecioInicial"));
            r.setAdelanto(rs.getDouble("Adelanto"));
            r.setPrecioRestante(rs.getDouble("PrecioRestante"));
            r.setTotalPagado(rs.getDouble("TotalPagado"));
            r.setCostoPenalidad(rs.getDouble("CostoPenalidad"));
            r.setObservacion(rs.getString("Observacion"));
            r.setIdEstadoReserva(rs.getInt("IdEstadoReserva")); 

            

            r.setNumeroHabitacion(rs.getString("numeroHabitacion"));
            r.setCategoriaDescripcion(rs.getString("categoriaDescripcion"));
            r.setNombreCliente(rs.getString("Nombre") + " " + rs.getString("Apellido"));

            lista.add(r);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return lista;
}


public Recepcion obtenerActivaPorCliente(int idCliente) {
    Recepcion r = null;
    String sql = """
        SELECT r.*, 
               h.Numero AS numeroHabitacion,
               c.Descripcion AS categoriaDescripcion,
               p.Nombre, p.Apellido
        FROM RECEPCION r
        INNER JOIN HABITACION h ON r.IdHabitacion = h.IdHabitacion
        INNER JOIN CATEGORIA c ON h.IdCategoria = c.IdCategoria
        INNER JOIN PERSONA p ON r.IdCliente = p.IdPersona
        WHERE r.IdCliente = ? AND r.Estado = 1 AND r.IdEstadoReserva = 3
        ORDER BY r.IdRecepcion DESC LIMIT 1
    """;

    try (Connection con = Conexion.getConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, idCliente);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            r = new Recepcion();
            r.setIdRecepcion(rs.getInt("IdRecepcion"));
            r.setIdCliente(rs.getInt("IdCliente"));
            r.setIdHabitacion(rs.getInt("IdHabitacion"));
            r.setFechaEntrada(rs.getTimestamp("FechaEntrada"));
            r.setFechaSalida(rs.getTimestamp("FechaSalida"));
            r.setFechaSalidaConfirmacion(rs.getTimestamp("FechaSalidaConfirmacion"));
            r.setPrecioInicial(rs.getDouble("PrecioInicial"));
            r.setAdelanto(rs.getDouble("Adelanto"));
            r.setPrecioRestante(rs.getDouble("PrecioRestante"));
            r.setTotalPagado(rs.getDouble("TotalPagado"));
            r.setCostoPenalidad(rs.getDouble("CostoPenalidad"));
            r.setObservacion(rs.getString("Observacion"));
            r.setEstado(rs.getBoolean("Estado"));
            

            r.setNumeroHabitacion(rs.getString("numeroHabitacion"));
            r.setCategoriaDescripcion(rs.getString("categoriaDescripcion"));
            r.setNombreCliente(rs.getString("Nombre") + " " + rs.getString("Apellido"));
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return r;
}
public int obtenerIdRecepcionPorHabitacion(int idHabitacion) {
    int idRecepcion = 0;
    String sql = """
        SELECT IdRecepcion 
        FROM RECEPCION 
        WHERE IdHabitacion = ? AND Estado = 1 AND IdEstadoReserva = 3
        ORDER BY FechaEntrada DESC LIMIT 1
    """;
    try (Connection con = Conexion.getConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, idHabitacion);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            idRecepcion = rs.getInt("IdRecepcion");
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return idRecepcion;
}


public boolean actualizarEstadoReserva(int idRecepcion, int idEstadoReserva) {
    String sql = "UPDATE RECEPCION SET IdEstadoReserva = ? WHERE IdRecepcion = ?";
    try (Connection con = Conexion.getConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, idEstadoReserva);
        ps.setInt(2, idRecepcion);
        return ps.executeUpdate() > 0;
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}

public boolean actualizarEstado(int idRecepcion, boolean nuevoEstado) {
    String sql = "UPDATE RECEPCION SET Estado = ? WHERE IdRecepcion = ?";
    try (Connection con = Conexion.getConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setBoolean(1, nuevoEstado); // true o false
        ps.setInt(2, idRecepcion);
        return ps.executeUpdate() > 0;

    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}

public List<Recepcion> filtrarRecepciones(String estado, String cliente, String orden) {
    List<Recepcion> lista = new ArrayList<>();

    StringBuilder sql = new StringBuilder("SELECT * FROM recepcion_view WHERE 1=1");

    if (estado != null && !estado.isEmpty()) {
        if (estado.equals("ACTIVO") || estado.equals("CHECKOUT") || estado.equals("ANULADO")) {
            sql.append(" AND estadoFinal = ?");
        } else {
            sql.append(" AND estadoReservaDescripcion = ?");
        }
    }

    if (cliente != null && !cliente.isEmpty()) {
        sql.append(" AND nombreCliente LIKE ?");
    }

    if ("reciente".equals(orden)) {
        sql.append(" ORDER BY fechaEntrada DESC");
    } else if ("antiguo".equals(orden)) {
        sql.append(" ORDER BY fechaEntrada ASC");
    }

    try (Connection con = Conexion.getConexion();
         PreparedStatement ps = con.prepareStatement(sql.toString())) {

        int i = 1;
        if (estado != null && !estado.isEmpty()) {
            ps.setString(i++, estado);
        }

        if (cliente != null && !cliente.isEmpty()) {
            ps.setString(i++, "%" + cliente + "%");
        }

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Recepcion r = new Recepcion();
            r.setIdRecepcion(rs.getInt("idRecepcion"));
            r.setNombreCliente(rs.getString("nombreCliente"));
            r.setNumeroHabitacion(rs.getString("numeroHabitacion"));
            r.setCategoriaDescripcion(rs.getString("categoriaDescripcion"));
            r.setFechaEntrada(rs.getTimestamp("fechaEntrada"));
            r.setFechaSalida(rs.getTimestamp("fechaSalida"));
            r.setPrecioInicial(rs.getDouble("precioInicial"));
            r.setAdelanto(rs.getDouble("adelanto"));
            r.setPrecioRestante(rs.getDouble("precioRestante"));
            r.setEstadoReservaDescripcion(rs.getString("estadoReservaDescripcion"));
            r.setIdHabitacion(rs.getInt("idHabitacion"));
            r.setEstado(rs.getBoolean("estado"));
            r.setIdCliente(rs.getInt("idCliente"));
            r.setIdEstadoReserva(rs.getInt("idEstadoReserva"));
            lista.add(r);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return lista;
}


}
