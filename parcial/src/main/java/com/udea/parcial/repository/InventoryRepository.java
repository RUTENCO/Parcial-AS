package com.udea.parcial.repository;

import com.udea.parcial.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Integer> {

    // IMPORTANTE: Asegúrate de que el nombre del atributo en la entidad Inventory sea 'almacen'
    // Spring Data es muy sensible: findBy + NombreAtributoRelacion + Id
    List<Inventory> findByAlmacenId(Integer almacenId);
}
