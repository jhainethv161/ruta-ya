package com.ruta_ya.repository.reporte;

import com.ruta_ya.dto.response.reporte.ConductorViajesPromedioReporte;
import com.ruta_ya.dto.response.reporte.MetodoPagoMenosUsadoReporte;
import com.ruta_ya.dto.response.reporte.PagoComisionMayorReporte;
import com.ruta_ya.dto.response.reporte.RecaudoMetodoPagoReporte;
import com.ruta_ya.dto.response.reporte.UsuarioPagoPromedioReporte;
import com.ruta_ya.dto.response.reporte.UsuarioReporte;
import com.ruta_ya.dto.response.reporte.ViajeRangoFechasReporte;
import com.ruta_ya.dto.response.reporte.ViajesConductorVehiculoReporte;
import com.ruta_ya.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ReporteRepository extends JpaRepository<Usuario, String> {

    // REPORTES SIMPLES

    //Mostrar la cédula, nombres y apellidos de los usuarios cuyo estado sea el seleccionado en la tabla USUARIO.
    @Query(
            nativeQuery = true,
            value = """
                        SELECT
                            CEDULA AS cedula,
                            CONCAT(PRIMER_NOMBRE, ' ', SEGUNDO_NOMBRE) AS nombres,
                            CONCAT(PRIMER_APELLIDO, ' ', SEGUNDO_APELLIDO) AS apellidos
                        FROM USUARIO
                        WHERE ID_ESTADO = :estado
                    """
    )
    List<UsuarioReporte> getUsuariosPorEstado(@Param("estado") int estado);

    //Obtener los pagos realizados cuya comisión de plataforma sea superior al porcentaje proporcionado del monto total
    @Query(
            nativeQuery = true,
            value = """
                        SELECT
                            CODIGO AS codigo,
                            FECHA AS fecha,
                            MONTO_TOTAL AS montoTotal,
                            COMISION_PLATAFORMA AS comisionPlataforma,
                            ID_METODO_PAGO AS idMetodoPago,
                            CODIGO_VIAJE AS codigoViaje,
                            CODIGO_SUSCRIPCION AS codigoSuscripcion
                        FROM PAGO
                        WHERE COMISION_PLATAFORMA >= MONTO_TOTAL * :porcentaje
                    """
    )
    List<PagoComisionMayorReporte> obtenerPagosComisionMayor(@Param("porcentaje") double porcentaje);

    // Listar los viajes realizados en un rango de fechas junto con una clasificación del costo
    @Query(
            nativeQuery = true,
            value = """
                        SELECT
                            CODIGO AS codigo,
                            FECHA_HORA AS fechaHora,
                            VALOR_ESTIMADO AS valorEstimado,
                            CASE
                                WHEN VALOR_ESTIMADO < 15000 THEN 'Económico'
                                WHEN VALOR_ESTIMADO BETWEEN 15000 AND 25000 THEN 'Intermedio'
                                ELSE 'Costoso'
                            END AS clasificacionCosto
                        FROM VIAJE
                        WHERE FECHA_HORA BETWEEN :fechaInicio AND :fechaFin
                        ORDER BY VALOR_ESTIMADO DESC
                    """
    )
    List<ViajeRangoFechasReporte> obtenerViajesRangoFechas(@Param("fechaInicio") LocalDate fechaInicio,
                                                           @Param("fechaFin") LocalDate fechaFin);

    // REPORTES INTERMEDIOS. TODO: Falta un reporte intermedio

    // Obtener la cantidad de viajes realizados por el conductor en cada vehiculo, solo tener en cuanta los resultados
    // con mas de x viajes en el rango de fechas especificado
    @Query(
            nativeQuery = true,
            value = """
                        SELECT
                            C.CEDULA AS cedula,
                            CONCAT(C.PRIMER_NOMBRE, ' ', C.SEGUNDO_NOMBRE) AS nombres,
                            CONCAT(C.PRIMER_APELLIDO, ' ', C.SEGUNDO_APELLIDO) AS apellidos,
                            VC.PLACA_VEHICULO AS placaVehiculo,
                            COUNT(*) AS numeroViajes
                        FROM USUARIO U
                            JOIN CONDUCTOR C ON U.CEDULA = C.CEDULA
                            JOIN VEHICULO_CONDUCTOR VC ON VC.CEDULA_CONDUCTOR = C.CEDULA
                            JOIN VIAJE V ON V.CEDULA_CONDUCTOR = C.CEDULA AND V.PLACA_VEHICULO = VC.PLACA_VEHICULO
                        WHERE V.FECHA_HORA BETWEEN :fechaInicio AND :fechaFin
                        GROUP BY C.CEDULA, VC.PLACA_VEHICULO
                        HAVING numeroViajes >= :cantidadViajes
                    """
    )
    List<ViajesConductorVehiculoReporte> obtenerCantidadViajesConductorVehiculoRangoFechas(@Param("fechaInicio") LocalDate fechaInicio,
                                                                                           @Param("fechaFin") LocalDate fechaFin,
                                                                                           @Param("cantidadViajes") int cantidadViajes);

    // Obtener el total recaudado por metodo de pago en un periodo específico
    @Query(
            nativeQuery = true,
            value = """
                        SELECT
                            MP.ID AS id,
                            MP.NOMBRE AS nombre,
                            COUNT(*) AS cantidadPagos,
                            SUM(P.MONTO_TOTAL) AS recaudo
                        FROM PAGO P
                            JOIN METODO_PAGO MP ON P.ID_METODO_PAGO = MP.ID
                        WHERE P.FECHA BETWEEN :fechaInicio AND :fechaFin
                        GROUP BY MP.ID, MP.NOMBRE
                    """
    )
    List<RecaudoMetodoPagoReporte> obtenerRecaudoMetodoPagoRangoFechas(@Param("fechaInicio") LocalDate fechaInicio,
                                                                       @Param("fechaFin") LocalDate fechaFin);

    // Listar los usuarios que realizaron viajes por encima de un valor determinado.
    @Query(
            nativeQuery = true,
            value = """
                        SELECT
                            U.CEDULA AS cedula,
                            CONCAT(U.PRIMER_NOMBRE, ' ', U.SEGUNDO_NOMBRE) AS nombres,
                            CONCAT(U.PRIMER_APELLIDO, ' ', U.SEGUNDO_APELLIDO) AS apellidos
                        FROM USUARIO U
                            JOIN VIAJE V ON U.CEDULA = V.CEDULA_USUARIO
                            JOIN PAGO P ON V.CODIGO = P.CODIGO_VIAJE
                        WHERE P.MONTO_TOTAL > :valor
                    """
    )
    List<UsuarioReporte> obtenerUsuariosViajesMayoresAValor(@Param("valor") double valor);

    // REPORTES AVANZADOS
    //
    @Query(
            nativeQuery = true,
            value = """
                        SELECT
                            V.CEDULA_CONDUCTOR AS cedulaConductor,
                            V.PLACA_VEHICULO AS placaVehiculo,
                            COUNT(V.CODIGO) AS totalViajes
                        FROM VIAJE V
                        WHERE V.FECHA_HORA BETWEEN :fechaInicio AND :fechaFin
                        GROUP BY V.CEDULA_CONDUCTOR, V.PLACA_VEHICULO
                        HAVING COUNT(V.CODIGO) > (
                            SELECT AVG(CANTIDAD_VIAJES)
                            FROM (
                                SELECT COUNT(CODIGO) AS CANTIDAD_VIAJES
                                FROM VIAJE
                                WHERE FECHA_HORA BETWEEN :fechaInicio AND :fechaFin
                                GROUP BY CEDULA_CONDUCTOR
                            ) AS PROMEDIO_VIAJES
                        )
                    """
    )
    List<ConductorViajesPromedioReporte> obtenerConductoresConMasViajesQuePromedio(@Param("fechaInicio") LocalDate fechaInicio,
                                                                                   @Param("fechaFin") LocalDate fechaFin);

    //Obtener los usuarios cuyos pagos acumulados superen el promedio de pagos de la plataforma Een un rango de fechas
    @Query(
            nativeQuery = true,
            value = """
                        SELECT
                            U.CEDULA AS cedula,
                            CONCAT(U.PRIMER_NOMBRE, ' ', U.SEGUNDO_NOMBRE) AS nombres,
                            CONCAT(U.PRIMER_APELLIDO, ' ', U.SEGUNDO_APELLIDO) AS apellidos,
                            SUM(P.MONTO_TOTAL) AS totalPagado
                        FROM USUARIO U
                            INNER JOIN VIAJE V ON U.CEDULA = V.CEDULA_USUARIO
                            INNER JOIN PAGO P ON V.CODIGO = P.CODIGO_VIAJE
                        WHERE P.FECHA BETWEEN :fechaInicio AND :fechaFin
                        GROUP BY U.CEDULA
                        HAVING totalPagado > (
                            SELECT AVG(TOTAL_USUARIO)
                            FROM (
                                SELECT SUM(P.MONTO_TOTAL) AS TOTAL_USUARIO
                                FROM VIAJE V
                                    INNER JOIN PAGO P ON V.CODIGO = P.CODIGO_VIAJE
                                WHERE P.FECHA BETWEEN :fechaInicio AND :fechaFin
                                GROUP BY V.CEDULA_USUARIO
                            ) AS PROMEDIO_PAGOS
                        )
                    """
    )
    List<UsuarioPagoPromedioReporte> obtenerUsuariosConPagoMayoresQuePromedio(@Param("fechaInicio") LocalDate fechaInicio,
                                                                              @Param("fechaFin") LocalDate fechaFin);

    // Consultar los métodos de pago menos utilizados en un periodo específico
    @Query(
            nativeQuery = true,
            value = """
                        SELECT
                            MP.NOMBRE AS metodoPago,
                            COUNT(P.CODIGO) AS cantidadUsos
                        FROM METODO_PAGO MP
                            INNER JOIN PAGO P ON MP.ID = P.ID_METODO_PAGO
                        WHERE P.FECHA BETWEEN :fechaInicio AND :fechaFin
                        GROUP BY MP.ID, MP.NOMBRE
                        HAVING COUNT(P.CODIGO) = (
                            SELECT MIN(CANTIDAD_USOS)
                            FROM (
                                SELECT COUNT(P.CODIGO) AS CANTIDAD_USOS
                                FROM PAGO P
                                WHERE P.FECHA BETWEEN :fechaInicio AND :fechaFin
                                GROUP BY P.ID_METODO_PAGO
                            ) AS USOS_METODOS
                        )
                    """
    )
    List<MetodoPagoMenosUsadoReporte> ObtenerMetodosPagoMenosUsados(@Param("fechaInicio") LocalDate fechaInicio,
                                                                    @Param("fechaFin") LocalDate fechaFin);

}
