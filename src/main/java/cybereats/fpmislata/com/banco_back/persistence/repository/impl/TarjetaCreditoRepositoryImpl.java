package cybereats.fpmislata.com.banco_back.persistence.repository.impl;

import cybereats.fpmislata.com.banco_back.domain.dto.CuentaBancariaDto;
import cybereats.fpmislata.com.banco_back.domain.dto.TarjetaCreditoDto;
import cybereats.fpmislata.com.banco_back.domain.repository.TarjetaCreditoRepository;
import cybereats.fpmislata.com.banco_back.persistence.dao.jpa.TarjetaCreditoDaoJpa;
import cybereats.fpmislata.com.banco_back.persistence.dao.jpa.entity.TarjetaCreditoJpaEntity;
import cybereats.fpmislata.com.banco_back.persistence.repository.mapper.CuentaBancariaMapper;
import cybereats.fpmislata.com.banco_back.persistence.repository.mapper.TarjetaCreditoMapper;

import java.util.List;

public class TarjetaCreditoRepositoryImpl implements TarjetaCreditoRepository {

    private final TarjetaCreditoDaoJpa tarjetaCreditoDaoJpa;

    public TarjetaCreditoRepositoryImpl(TarjetaCreditoDaoJpa tarjetaCreditoDaoJpa) {
        this.tarjetaCreditoDaoJpa = tarjetaCreditoDaoJpa;
    }

    @Override
    public List<TarjetaCreditoDto> findByCuentaBancaria(CuentaBancariaDto cuentaBancariaDto) {
        List<TarjetaCreditoJpaEntity> list = tarjetaCreditoDaoJpa
                .findByCuentaBancaria(CuentaBancariaMapper.getInstance().toEntity(cuentaBancariaDto));
        return list.stream()
                .map(TarjetaCreditoMapper.getInstance()::toDto)
                .toList();
    }

    @Override
    public TarjetaCreditoDto findByNumeroTarjeta(String numeroTarjeta) {
        return TarjetaCreditoMapper.getInstance().toDto(tarjetaCreditoDaoJpa.findByNumeroTarjeta(numeroTarjeta));
    }

    @Override
    public TarjetaCreditoDto save(TarjetaCreditoDto tarjetaCreditoDto) {
        TarjetaCreditoJpaEntity entity = TarjetaCreditoMapper.getInstance().toEntity(tarjetaCreditoDto);
        if (entity.getId() == null) {
            return TarjetaCreditoMapper.getInstance().toDto(tarjetaCreditoDaoJpa.insert(entity));
        } else {
            return TarjetaCreditoMapper.getInstance().toDto(tarjetaCreditoDaoJpa.update(entity));
        }
    }

    @Override
    public TarjetaCreditoDto findById(Long id) {
        return TarjetaCreditoMapper.getInstance().toDto(tarjetaCreditoDaoJpa.findById(id));
    }

    @Override
    public List<TarjetaCreditoDto> findAll() {
        return tarjetaCreditoDaoJpa.findAll().stream()
                .map(TarjetaCreditoMapper.getInstance()::toDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        tarjetaCreditoDaoJpa.delete(id);
    }
}
