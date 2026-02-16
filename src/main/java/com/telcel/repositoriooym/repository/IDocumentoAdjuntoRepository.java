package com.telcel.repositoriooym.repository;

import com.telcel.repositoriooym.entity.DocumentoAdjunto;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * @author marcos.hernandez
 */
public interface IDocumentoAdjuntoRepository extends JpaRepository<DocumentoAdjunto, Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM DocumentoAdjunto d WHERE d.idDocumentoAdjunto = :id")
    void deleteByIdIndividual(@Param("id") Long id);

    Optional<DocumentoAdjunto> findByNombreArchivoAndProyecto_IdProyecto(String nombreArchivo, Long idProyecto);
}
