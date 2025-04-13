package br.com.gustavo.msavaliadorcredito.application;

import br.com.gustavo.msavaliadorcredito.domain.model.DadosCliente;
import br.com.gustavo.msavaliadorcredito.domain.model.SituacaoCliente;
import br.com.gustavo.msavaliadorcredito.infra.clients.ClienteResourceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {

    private final ClienteResourceClient clientesClient;

    public SituacaoCliente obterSituacaoCliente(String cpf) {

        // obterDadosCliente - msclientes
        // obter cartoes do cliente - mscartoes

        ResponseEntity<DadosCliente> dadosClienteResponse = clientesClient.dadosCliente(cpf);
        return SituacaoCliente
                .builder()
                .cliente(dadosClienteResponse.getBody())
                .build();
    }
}