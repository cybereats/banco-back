package cybereats.fpmislata.com.banco_back.domain.service.impl;

import cybereats.fpmislata.com.banco_back.domain.dto.ClienteDto;
import cybereats.fpmislata.com.banco_back.domain.dto.CuentaBancariaDto;
import cybereats.fpmislata.com.banco_back.domain.dto.TarjetaCreditoDto;
import cybereats.fpmislata.com.banco_back.domain.model.Page;
import cybereats.fpmislata.com.banco_back.domain.repository.CuentaBancariaRepository;
import cybereats.fpmislata.com.banco_back.domain.service.CuentaBancariaService;

import java.math.BigDecimal;

import cybereats.fpmislata.com.banco_back.exception.BusinessException;
import cybereats.fpmislata.com.banco_back.domain.mapper.CuentaBancariaMapper;
import cybereats.fpmislata.com.banco_back.domain.model.CuentaBancaria;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class CuentaBancariaServiceImpl implements CuentaBancariaService {

    private final CuentaBancariaRepository cuentaBancariaRepository;

    public CuentaBancariaServiceImpl(CuentaBancariaRepository cuentaBancariaRepository) {
        this.cuentaBancariaRepository = cuentaBancariaRepository;
    }

    @Override
    public CuentaBancariaDto create(CuentaBancariaDto cuentaBancariaDto) {
        return cuentaBancariaRepository.save(cuentaBancariaDto);
    }

    @Override
    public CuentaBancariaDto update(CuentaBancariaDto cuentaBancariaDto) {
        return cuentaBancariaRepository.save(cuentaBancariaDto);
    }

    @Override
    public CuentaBancariaDto findById(Long id) {
        return cuentaBancariaRepository.findById(id);
    }

    @Override
    public Page<CuentaBancariaDto> findByClient(ClienteDto clienteDto) {
        return cuentaBancariaRepository.findByClient(clienteDto);
    }

    @Override
    public CuentaBancariaDto findByIban(String iban) {
        return cuentaBancariaRepository.findByIban(iban);
    }

    @Override
    public void delete(Long id) {
        cuentaBancariaRepository.delete(id);
    }

    @Override
    public CuentaBancariaDto findByTarjetaCredito(TarjetaCreditoDto tarjetaCreditoDto) {
        return cuentaBancariaRepository.findByTarjetaCredito(tarjetaCreditoDto);
    }

    @Override
    public CuentaBancariaDto ingresar(CuentaBancariaDto cuentaBancariaDto, BigDecimal importe, String concepto) {
        validateConcepto(concepto);
        CuentaBancaria cuentaBancaria = CuentaBancariaMapper.getInstance().toModel(cuentaBancariaDto);
        cuentaBancaria.ingresar(importe);
        return cuentaBancariaRepository.ingresar(CuentaBancariaMapper.getInstance().toDto(cuentaBancaria), importe,
                concepto);
    }

    @Override
    public CuentaBancariaDto retirar(CuentaBancariaDto cuentaBancariaDto, BigDecimal importe, String concepto) {
        validateConcepto(concepto);
        CuentaBancaria cuentaBancaria = CuentaBancariaMapper.getInstance().toModel(cuentaBancariaDto);
        cuentaBancaria.retirar(importe);
        return cuentaBancariaRepository.retirar(CuentaBancariaMapper.getInstance().toDto(cuentaBancaria), importe,
                concepto);
    }

    private void validateConcepto(String concepto) {
        if (concepto != null && !concepto.trim().isEmpty() && concepto.trim().length() < 3) {
            throw new BusinessException("El concepto debe tener al menos 3 caracteres");
        }
    }
}
