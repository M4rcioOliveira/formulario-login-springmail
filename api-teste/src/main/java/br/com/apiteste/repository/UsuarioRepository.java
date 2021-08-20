package br.com.apiteste.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.apiteste.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	@Query(value = "SELECT codigo from usuario where email = ?1", nativeQuery = true)

	Integer findByEmail(String email);
	
	@Query(value = "UPDATE usuario SET situacao = true WHERE email = ?1", nativeQuery = true )
	void updateByEmail(String email);


	Usuario findByCodigo(Integer codigo);

}
