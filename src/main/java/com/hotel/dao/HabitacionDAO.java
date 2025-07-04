package com.hotel.dao;

import com.hotel.interfaces.IHabitacionDAO;
import com.hotel.modelo.Habitacion;
import com.hotel.util.Conexion;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class HabitacionDAO implements IHabitacionDAO {

    Connection con;
    PreparedStatement ps;
    ResultSet rs;

@Override
public List<Habitacion> listar() {
    List<Habitacion> lista = new ArrayList<>();

    String sql = """
        SELECT h.*, 
               c.Descripcion AS categoriaDescripcion, 
               e.Descripcion AS estadoHabitacionDescripcion
        FROM HABITACION h
        JOIN CATEGORIA c ON h.IdCategoria = c.IdCategoria
        JOIN ESTADO_HABITACION e ON h.IdEstadoHabitacion = e.IdEstadoHabitacion
    """;

    try (Connection con = Conexion.getConexion();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            Habitacion h = new Habitacion();
            h.setIdHabitacion(rs.getInt("IdHabitacion"));
            h.setNumero(rs.getString("Numero"));
            h.setDetalle(rs.getString("Detalle"));
            h.setPrecio(rs.getDouble("Precio"));
            h.setIdEstadoHabitacion(rs.getInt("IdEstadoHabitacion"));
            h.setIdPiso(rs.getInt("IdPiso"));
            h.setIdCategoria(rs.getInt("IdCategoria"));
            h.setImagen(rs.getString("Imagen"));
            h.setEstado(rs.getBoolean("Estado"));

            // Descripciones adicionales
            h.setCategoriaDescripcion(rs.getString("categoriaDescripcion"));
            h.setEstadoHabitacionDescripcion(rs.getString("estadoHabitacionDescripcion"));

            lista.add(h);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return lista;
}


    @Override
    public Habitacion obtener(int id) {
        String sql = "SELECT * FROM HABITACION WHERE IdHabitacion=?";
        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                Habitacion h = new Habitacion();
                h.setIdHabitacion(rs.getInt("IdHabitacion"));
                h.setNumero(rs.getString("Numero"));
                h.setDetalle(rs.getString("Detalle"));
                h.setPrecio(rs.getDouble("Precio"));
                h.setIdEstadoHabitacion(rs.getInt("IdEstadoHabitacion"));
                h.setIdPiso(rs.getInt("IdPiso"));
                h.setIdCategoria(rs.getInt("IdCategoria"));
                h.setEstado(rs.getBoolean("Estado"));
                h.setImagen(rs.getString("Imagen")); // ✅ NUEVO
                return h;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean agregar(Habitacion h) {
        String sql = "INSERT INTO HABITACION (Numero, Detalle, Precio, IdEstadoHabitacion, IdPiso, IdCategoria, Estado, Imagen) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, h.getNumero());
            ps.setString(2, h.getDetalle());
            ps.setDouble(3, h.getPrecio());
            ps.setInt(4, h.getIdEstadoHabitacion());
            ps.setInt(5, h.getIdPiso());
            ps.setInt(6, h.getIdCategoria());
            ps.setBoolean(7, h.isEstado());
            ps.setString(8, h.getImagen()); // ✅ NUEVO
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean editar(Habitacion h) {
        String sql = "UPDATE HABITACION SET Numero=?, Detalle=?, Precio=?, IdEstadoHabitacion=?, IdPiso=?, IdCategoria=?, Estado=?, Imagen=? WHERE IdHabitacion=?";
        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, h.getNumero());
            ps.setString(2, h.getDetalle());
            ps.setDouble(3, h.getPrecio());
            ps.setInt(4, h.getIdEstadoHabitacion());
            ps.setInt(5, h.getIdPiso());
            ps.setInt(6, h.getIdCategoria());
            ps.setBoolean(7, h.isEstado());
            ps.setString(8, h.getImagen()); // ✅ NUEVO
            ps.setInt(9, h.getIdHabitacion());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean eliminar(int id) {
        String sql = "DELETE FROM HABITACION WHERE IdHabitacion=?";
        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
   public List<Habitacion> listarDisponibles() {
    List<Habitacion> lista = new ArrayList<>();
    String sql = "SELECT h.*, c.Descripcion AS CategoriaDescripcion " +
                 "FROM HABITACION h " +
                 "JOIN CATEGORIA c ON h.IdCategoria = c.IdCategoria " +
                 "WHERE h.IdEstadoHabitacion = 1 AND h.Estado = 1";

    try {
        con = Conexion.getConexion();
        ps = con.prepareStatement(sql);
        rs = ps.executeQuery();
        while (rs.next()) {
            Habitacion h = new Habitacion();
            h.setIdHabitacion(rs.getInt("IdHabitacion"));
            h.setNumero(rs.getString("Numero"));
            h.setDetalle(rs.getString("Detalle"));
            h.setPrecio(rs.getDouble("Precio"));
            h.setIdEstadoHabitacion(rs.getInt("IdEstadoHabitacion"));
            h.setIdPiso(rs.getInt("IdPiso"));
            h.setIdCategoria(rs.getInt("IdCategoria"));
            h.setEstado(rs.getBoolean("Estado"));
            h.setCategoriaDescripcion(rs.getString("CategoriaDescripcion")); // Asegúrate de tener este atributo en el modelo
            lista.add(h);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return lista;
}

public boolean actualizarEstado(int idHabitacion, int nuevoEstado) {
    String sql = "UPDATE HABITACION SET IdEstadoHabitacion = ? WHERE IdHabitacion = ?";
    try (Connection con = Conexion.getConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, nuevoEstado); // 2 = OCUPADO
        ps.setInt(2, idHabitacion);
        ps.executeUpdate();
        return true;

    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}


public List<Habitacion> filtrarPorCategoriaYPrecio(String categoria, double precioMax) {
    List<Habitacion> lista = new ArrayList<>();

    String sql = "SELECT h.*, c.Descripcion AS categoriaDescripcion " +
                 "FROM HABITACION h " +
                 "INNER JOIN CATEGORIA c ON h.IdCategoria = c.IdCategoria " +
                 "WHERE h.Precio <= ?" +
                 (categoria != null && !categoria.isEmpty() ? " AND c.Descripcion = ?" : "");

    try (Connection con = Conexion.getConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setDouble(1, precioMax);
        if (categoria != null && !categoria.isEmpty()) {
            ps.setString(2, categoria);
        }

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Habitacion h = new Habitacion();
            h.setIdHabitacion(rs.getInt("IdHabitacion"));
            h.setNumero(rs.getString("Numero"));
            h.setDetalle(rs.getString("Detalle"));
            h.setPrecio(rs.getDouble("Precio"));
            h.setIdPiso(rs.getInt("IdPiso"));
            h.setIdCategoria(rs.getInt("IdCategoria"));
            h.setCategoriaDescripcion(rs.getString("categoriaDescripcion"));
            h.setImagen(rs.getString("Imagen"));
            h.setEstado(rs.getBoolean("Estado"));
            // puedes agregar más campos si los necesitas

            lista.add(h);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return lista;
}
public List<Habitacion> filtrarPorCategoriaEstadoYPrecio(String categoria, String estadoId, double precioMax) {
    List<Habitacion> lista = new ArrayList<>();

    try (Connection con = Conexion.getConexion()) {
        String sql = """
            SELECT h.*, 
                   c.Descripcion AS categoriaDescripcion,
                   e.Descripcion AS estadoHabitacionDescripcion
            FROM HABITACION h
            JOIN CATEGORIA c ON h.IdCategoria = c.IdCategoria
            JOIN ESTADO_HABITACION e ON h.IdEstadoHabitacion = e.IdEstadoHabitacion
            WHERE h.Precio <= ?
        """;

        // Armado dinámico del WHERE
        if (categoria != null && !categoria.isEmpty()) {
            sql += " AND c.Descripcion = ?";
        }

        if (estadoId != null && !estadoId.isEmpty()) {
            sql += " AND e.IdEstadoHabitacion = ?";
        }

        PreparedStatement ps = con.prepareStatement(sql);

        int paramIndex = 1;
        ps.setDouble(paramIndex++, precioMax);

        if (categoria != null && !categoria.isEmpty()) {
            ps.setString(paramIndex++, categoria);
        }

        if (estadoId != null && !estadoId.isEmpty()) {
            ps.setInt(paramIndex++, Integer.parseInt(estadoId));
        }

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Habitacion h = new Habitacion();
            h.setIdHabitacion(rs.getInt("IdHabitacion"));
            h.setNumero(rs.getString("Numero"));
            h.setDetalle(rs.getString("Detalle"));
            h.setPrecio(rs.getDouble("Precio"));
            h.setIdEstadoHabitacion(rs.getInt("IdEstadoHabitacion"));
            h.setIdPiso(rs.getInt("IdPiso"));
            h.setIdCategoria(rs.getInt("IdCategoria"));
            h.setImagen(rs.getString("Imagen"));
            h.setEstado(rs.getBoolean("Estado"));
            h.setCategoriaDescripcion(rs.getString("categoriaDescripcion"));
            h.setEstadoHabitacionDescripcion(rs.getString("estadoHabitacionDescripcion"));
            lista.add(h);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return lista;
}
public List<Habitacion> filtrarPorMultiplesEstadosCategoriaPrecio(String[] estadoIds, String categoria, double precioMax) {
    List<Habitacion> lista = new ArrayList<>();

    try (Connection con = Conexion.getConexion()) {
        StringBuilder sql = new StringBuilder("""
            SELECT h.*, 
                   c.Descripcion AS categoriaDescripcion,
                   e.Descripcion AS estadoHabitacionDescripcion
            FROM HABITACION h
            JOIN CATEGORIA c ON h.IdCategoria = c.IdCategoria
            JOIN ESTADO_HABITACION e ON h.IdEstadoHabitacion = e.IdEstadoHabitacion
            WHERE h.Precio <= ?
        """);

        if (categoria != null && !categoria.isEmpty()) {
            sql.append(" AND c.Descripcion = ?");
        }

        if (estadoIds != null && estadoIds.length > 0) {
            sql.append(" AND h.IdEstadoHabitacion IN (");
            sql.append("?,".repeat(estadoIds.length));
            sql.setLength(sql.length() - 1); // eliminar la última coma
            sql.append(")");
        }

        PreparedStatement ps = con.prepareStatement(sql.toString());

        int paramIndex = 1;
        ps.setDouble(paramIndex++, precioMax);

        if (categoria != null && !categoria.isEmpty()) {
            ps.setString(paramIndex++, categoria);
        }

        if (estadoIds != null && estadoIds.length > 0) {
            for (String id : estadoIds) {
                ps.setInt(paramIndex++, Integer.parseInt(id));
            }
        }

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Habitacion h = new Habitacion();
            h.setIdHabitacion(rs.getInt("IdHabitacion"));
            h.setNumero(rs.getString("Numero"));
            h.setDetalle(rs.getString("Detalle"));
            h.setPrecio(rs.getDouble("Precio"));
            h.setIdEstadoHabitacion(rs.getInt("IdEstadoHabitacion"));
            h.setIdPiso(rs.getInt("IdPiso"));
            h.setIdCategoria(rs.getInt("IdCategoria"));
            h.setImagen(rs.getString("Imagen"));
            h.setEstado(rs.getBoolean("Estado"));
            h.setCategoriaDescripcion(rs.getString("categoriaDescripcion"));
            h.setEstadoHabitacionDescripcion(rs.getString("estadoHabitacionDescripcion"));
            lista.add(h);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return lista;
}
public List<Habitacion> filtrarMultiple(List<Integer> estados, String categoria, double precioMax) {
    List<Habitacion> lista = new ArrayList<>();

    StringBuilder sql = new StringBuilder("""
        SELECT h.*, 
               c.Descripcion AS categoriaDescripcion, 
               e.Descripcion AS estadoHabitacionDescripcion
        FROM HABITACION h
        JOIN CATEGORIA c ON h.IdCategoria = c.IdCategoria
        JOIN ESTADO_HABITACION e ON h.IdEstadoHabitacion = e.IdEstadoHabitacion
        WHERE h.Precio <= ?
    """);

    if (categoria != null && !categoria.isEmpty()) {
        sql.append(" AND c.Descripcion = ?");
    }

    if (estados != null && !estados.isEmpty()) {
        String estadoIds = estados.stream()
                                  .map(String::valueOf)
                                  .collect(Collectors.joining(","));
        sql.append(" AND h.IdEstadoHabitacion IN (").append(estadoIds).append(")");
    }

    try (Connection con = Conexion.getConexion();
         PreparedStatement ps = con.prepareStatement(sql.toString())) {

        int index = 1;
        ps.setDouble(index++, precioMax);

        if (categoria != null && !categoria.isEmpty()) {
            ps.setString(index++, categoria);
        }

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Habitacion h = new Habitacion();
            h.setIdHabitacion(rs.getInt("IdHabitacion"));
            h.setNumero(rs.getString("Numero"));
            h.setDetalle(rs.getString("Detalle"));
            h.setPrecio(rs.getDouble("Precio"));
            h.setIdPiso(rs.getInt("IdPiso"));
            h.setIdCategoria(rs.getInt("IdCategoria"));
            h.setIdEstadoHabitacion(rs.getInt("IdEstadoHabitacion"));
            h.setImagen(rs.getString("Imagen"));
            h.setEstado(rs.getBoolean("Estado"));
            h.setCategoriaDescripcion(rs.getString("categoriaDescripcion"));
            h.setEstadoHabitacionDescripcion(rs.getString("estadoHabitacionDescripcion"));
            lista.add(h);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return lista;
}

}
