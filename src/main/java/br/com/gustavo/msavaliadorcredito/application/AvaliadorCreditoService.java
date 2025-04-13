package br.com.gustavo.msavaliadorcredito.application;

import br.com.gustavo.msavaliadorcredito.domain.model.CartaoCliente;
import br.com.gustavo.msavaliadorcredito.domain.model.DadosCliente;
import br.com.gustavo.msavaliadorcredito.domain.model.SituacaoCliente;
import br.com.gustavo.msavaliadorcredito.infra.clients.CartoesResourceClient;
import br.com.gustavo.msavaliadorcredito.infra.clients.ClienteResourceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {

    private final ClienteResourceClient clientesClient;

    private final CartoesResourceClient cartoesClient;

    public SituacaoCliente obterSituacaoCliente(String cpf) {

        // obterDadosCliente - msclientes
        // obter cartoes do cliente - mscartoes

        ResponseEntity<DadosCliente> dadosClienteResponse = clientesClient.dadosCliente(cpf);
        ResponseEntity<List<CartaoCliente>> cartoesResponse = cartoesClient.getCartoesByCliente(cpf);
        return SituacaoCliente
                .builder()
                .cliente(dadosClienteResponse.getBody())
                .cartoes(cartoesResponse.getBody())
                .build();
    }
}