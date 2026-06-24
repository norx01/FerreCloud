package com.ferrecloud.ferrecloud.service;

import com.ferrecloud.ferrecloud.model.Venta;
import com.ferrecloud.ferrecloud.model.producto;
import com.ferrecloud.ferrecloud.repository.InventarioRepository;
import com.ferrecloud.ferrecloud.repository.VentaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReporteService {

    private final VentaRepository ventaRepository;
    private final InventarioRepository inventarioRepository;

    public ReporteService(VentaRepository ventaRepository,
                          InventarioRepository inventarioRepository) {
        this.ventaRepository = ventaRepository;
        this.inventarioRepository = inventarioRepository;
    }


    public List<Venta> ventasHoy() {
        LocalDateTime inicio = LocalDateTime.now().toLocalDate().atStartOfDay();
        LocalDateTime fin = inicio.plusDays(1);
        return ventaRepository.findByFechaBetween(inicio, fin);
    }


    public List<Venta> ventasSemana() {
        LocalDateTime fin = LocalDateTime.now();
        LocalDateTime inicio = fin.minusDays(7);
        return ventaRepository.findByFechaBetween(inicio, fin);
    }


    public List<Venta> ventasMes() {
        LocalDateTime fin = LocalDateTime.now();
        LocalDateTime inicio = fin.minusDays(30);
        return ventaRepository.findByFechaBetween(inicio, fin);
    }


    public List<producto> productosStockBajo() {
        return inventarioRepository.findAll().stream()
                .filter(p -> p.getStock() <= p.getStockMinimo())
                .collect(Collectors.toList());
    }
}
