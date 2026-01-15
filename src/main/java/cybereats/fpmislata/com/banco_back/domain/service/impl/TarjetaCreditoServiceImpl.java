package cybereats.fpmislata.com.banco_back.domain.service.impl;

import cybereats.fpmislata.com.banco_back.domain.dto.CuentaBancariaDto;
import cybereats.fpmislata.com.banco_back.domain.dto.TarjetaCreditoDto;
import cybereats.fpmislata.com.banco_back.domain.repository.TarjetaCreditoRepository;
import cybereats.fpmislata.com.banco_back.domain.service.TarjetaCreditoService;
import cybereats.fpmislata.com.banco_back.exception.BusinessException;
import cybereats.fpmislata.com.banco_back.domain.validation.DtoValidator;

import java.time.LocalDate;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class TarjetaCreditoServiceImpl implements TarjetaCreditoService {

    private final TarjetaCreditoRepository tarjetaCreditoRepository;

    public TarjetaCreditoServiceImpl(TarjetaCreditoRepository tarjetaCreditoRepository) {
        this.tarjetaCreditoRepository = tarjetaCreditoRepository;
    }

    @Override
    public List<TarjetaCreditoDto> findByCuentaBancaria(CuentaBancariaDto cuentaBancariaDto) {
        return tarjetaCreditoRepository.findByCuentaBancaria(cuentaBancariaDto);
    }

    @Override
    public void validate(TarjetaCreditoDto tarjetaCreditoDto) {
        DtoValidator.validate(tarjetaCreditoDto);

        TarjetaCreditoDto storedCard = tarjetaCreditoRepository.findByNumeroTarjeta(tarjetaCreditoDto.numeroTarjeta());

        if (storedCard == null) {
            throw new BusinessException("Tarjeta no encontrada: " + tarjetaCreditoDto.numeroTarjeta());
        }

        if (storedCard.fechaCaducidad().isBefore(LocalDate.now())) {
            throw new BusinessException("Fecha de caducidad incorrecta");
        }

        if (storedCard.cvc() != tarjetaCreditoDto.cvc()) {
            throw new BusinessException("CVC incorrecto");
        }

        if (!storedCard.nombreCompleto().equalsIgnoreCase(tarjetaCreditoDto.nombreCompleto())) {
            throw new BusinessException("Nombre del titular incorrecto");
        }
    }

    @Override
    public TarjetaCreditoDto create(TarjetaCreditoDto tarjetaCreditoDto) {
        DtoValidator.validate(tarjetaCreditoDto);
        return tarjetaCreditoRepository.save(tarjetaCreditoDto);
    }

    @Override
    public TarjetaCreditoDto update(TarjetaCreditoDto tarjetaCreditoDto) {
        DtoValidator.validate(tarjetaCreditoDto);
        return tarjetaCreditoRepository.save(tarjetaCreditoDto);
    }

    @Override
    public TarjetaCreditoDto findById(Long id) {
        return tarjetaCreditoRepository.findById(id);
    }

    @Override
    public List<TarjetaCreditoDto> findAll() {
        return tarjetaCreditoRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        tarjetaCreditoRepository.delete(id);
    }
}
