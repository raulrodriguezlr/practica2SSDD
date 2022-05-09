package es.codeurjc.apirestreservabicicletas.bicicletas;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import es.codeurjc.apirestreservabicicletas.bicicletas.Bicicleta;
import es.codeurjc.apirestreservabicicletas.estaciones.Estacion;

@Transactional
public interface RepoBicicletas  extends JpaRepository<Bicicleta, Long>{
	 	@Modifying
		@Query(value = "update bicicleta set Estado = :Estado where id_b = :id_b",nativeQuery = true)
	 	void updateEstadoById(@Param("id_b") long id, @Param("Estado") String Estado);
	 	@Modifying
		@Query(value = "update bicicleta set Estacion_id = :Estacion_id where id_b = :id_b",nativeQuery = true)
	 	void updateEstacionById(@Param("id_b") long id, @Param("Estacion_id") Estacion estacion);
}

