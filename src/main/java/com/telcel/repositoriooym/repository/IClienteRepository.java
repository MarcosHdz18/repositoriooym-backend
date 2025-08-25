package com.telcel.repositoriooym.repository;

import com.telcel.repositoriooym.entity.Cliente;
import org.springframework.data.repository.CrudRepository;

/**
 * @author marcos.hernandez
 */

public interface IClienteRepository extends CrudRepository<Cliente, Long> {
}
