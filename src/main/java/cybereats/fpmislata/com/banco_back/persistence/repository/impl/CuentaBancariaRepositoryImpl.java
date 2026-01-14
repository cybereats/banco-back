package cybereats.fpmislata.com.banco_back.persistence.repository.impl;

import cybereats.fpmislata.com.banco_back.domain.dto.ClienteDto;
import cybereats.fpmislata.com.banco_back.domain.dto.CuentaBancariaDto;
import cybereats.fpmislata.com.banco_back.domain.dto.TarjetaCreditoDto;
import cybereats.fpmislata.com.banco_back.domain.model.OrigenMovimientoBancario;
import cybereats.fpmislata.com.banco_back.domain.model.Page;
import cybereats.fpmislata.com.banco_back.domain.model.TipoMovimientoBancario;
import cybereats.fpmislata.com.banco_back.domain.repository.CuentaBancariaRepository;
import cybereats.fpmislata.com.banco_back.persistence.dao.jpa.CuentaBancariaDaoJpa;
import cybereats.fpmislata.com.banco_back.persistence.dao.jpa.MovimientoBancarioDaoJpa;
import cybereats.fpmislata.com.banco_back.persistence.dao.jpa.entity.CuentaBancariaJpaEntity;
import cybereats.fpmislata.com.banco_back.persistence.dao.jpa.entity.MovimientoBancarioJpaEntity;
import cybereats.fpmislata.com.banco_back.persistence.repository.mapper.ClienteMapper;
import cybereats.fpmislata.com.banco_back.persistence.repository.mapper.CuentaBancariaMapper;
import cybereats.fpmislata.com.banco_back.persistence.repository.mapper.TarjetaCreditoMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class CuentaBancariaRepositoryImpl implements CuentaBancariaRepository {

    private final CuentaBancariaDaoJpa cuentaBancariaDaoJpa;
    private final MovimientoBancarioDaoJpa movimientoBancarioDaoJpa;

    public CuentaBancariaRepositoryImpl(CuentaBancariaDaoJpa cuentaBancariaDaoJpa,
            MovimientoBancarioDaoJpa movimientoBancarioDaoJpa) {
        this.cuentaBancariaDaoJpa = cuentaBancariaDaoJpa;
        this.movimientoBancarioDaoJpa = movimientoBancarioDaoJpa;
    }

    @Override
    public CuentaBancariaDto save(CuentaBancariaDto cuentaBancariaDto) {
        CuentaBancariaJpaEntity entity = CuentaBancariaMapper.getInstance().toEntity(cuentaBancariaDto);
        if (entity.getId() == null) {
            return CuentaBancariaMapper.getInstance().toDto(cuentaBancariaDaoJpa.insert(entity));
        } else {
            return CuentaBancariaMapper.getInstance().toDto(cuentaBancariaDaoJpa.update(entity));
        }
    }

    @Override
    public CuentaBancariaDto findById(Long id) {
        return CuentaBancariaMapper.getInstance().toDto(cuentaBancariaDaoJpa.findById(id));
    }

    @Override
    public CuentaBancariaDto findByIban(String iban) {
        return CuentaBancariaMapper.getInstance().toDto(cuentaBancariaDaoJpa.findByIban(iban));
    }

    @Override
    public Page<CuentaBancariaDto> findByClient(ClienteDto clienteDto) {
        List<CuentaBancariaJpaEntity> list = cuentaBancariaDaoJpa
                .findByCliente(ClienteMapper.getInstance().toEntity(clienteDto));
        List<CuentaBancariaDto> dtoList = list.stream()
                .map(CuentaBancariaMapper.getInstance()::toDto)
                .toList();
        return new Page<>(dtoList, 1, Math.max(1, dtoList.size()), dtoList.size());
    }

    @Override
    public CuentaBancariaDto findByTarjetaCredito(TarjetaCreditoDto tarjetaCreditoDto) {
        return CuentaBancariaMapper.getInstance().toDto(cuentaBancariaDaoJpa
                .findByTarjetaCredito(TarjetaCreditoMapper.getInstance().toEntity(tarjetaCreditoDto)));
    }

    @Override
    public CuentaBancariaDto ingresar(CuentaBancariaDto cuentaBancariaDto, BigDecimal importe, String concepto) {
        CuentaBancariaJpaEntity entity = CuentaBancariaMapper.getInstance().toEntity(cuentaBancariaDto);
        CuentaBancariaJpaEntity updatedEntity = cuentaBancariaDaoJpa.update(entity);

        MovimientoBancarioJpaEntity movimiento = new MovimientoBancarioJpaEntity();
        movimiento.setTipoMovimientoBancario(TipoMovimientoBancario.HABER);
        movimiento.setOrigenMovimientoBancario(OrigenMovimientoBancario.TARJETA_BANCARIA);
        movimiento.setImporte(importe);
        movimiento.setFecha(LocalDateTime.now());
        movimiento.setConcepto((concepto == null || concepto.trim().isEmpty()) ? "Ingreso" : concepto);
        movimiento.setCuentaBancaria(updatedEntity);

        movimientoBancarioDaoJpa.insert(movimiento);

        return CuentaBancariaMapper.getInstance().toDto(updatedEntity);
    }

    @Override
    public CuentaBancariaDto retirar(CuentaBancariaDto cuentaBancariaDto, BigDecimal importe, String concepto) {
        CuentaBancariaJpaEntity entity = CuentaBancariaMapper.getInstance().toEntity(cuentaBancariaDto);
        CuentaBancariaJpaEntity updatedEntity = cuentaBancariaDaoJpa.update(entity);

        MovimientoBancarioJpaEntity movimiento = new MovimientoBancarioJpaEntity();
        movimiento.setTipoMovimientoBancario(TipoMovimientoBancario.DEBE);
        movimiento.setOrigenMovimientoBancario(OrigenMovimientoBancario.TARJETA_BANCARIA);
        movimiento.setImporte(importe);
        movimiento.setFecha(LocalDateTime.now());
        movimiento.setConcepto((concepto == null || concepto.trim().isEmpty()) ? "Reintegro" : concepto);
        movimiento.setCuentaBancaria(updatedEntity);

        movimientoBancarioDaoJpa.insert(movimiento);

        return CuentaBancariaMapper.getInstance().toDto(updatedEntity);
    }

    @Override
    public void delete(Long id) {
        cuentaBancariaDaoJpa.delete(id);
    }
}
