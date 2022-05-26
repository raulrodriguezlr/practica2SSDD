package es.codeurjc.apirestreservabicicletas.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import es.codeurjc.apirestreservabicicletas.model.Estacion;





@Transactional
public interface RepoEstacion  extends JpaRepository<Estacion, Long>{
	 	@Modifying
	   @Query(value = "update estacion_bicicletas set coordenadas = :coordenadas where id = :id",nativeQuery = true)
	    void updateCoordenadasById(@Param("id") long id, @Param("coordenadas") String coordenadas);
	 	@Modifying
		@Query(value = "update estacion_bicicletas set estado = :estado where id = :id",nativeQuery = true)
	 	void updateEstadoById(@Param("id") long id, @Param("estado") String estado);
}

